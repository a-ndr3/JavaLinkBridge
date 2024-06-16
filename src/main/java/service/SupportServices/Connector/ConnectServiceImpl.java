package service.SupportServices.Connector;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.ElementOperation;
import at.jku.isse.designspace.core.operations.PropertyValueOperation;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.core.operations.element.CreateInstance;
import at.jku.isse.designspace.core.operations.element.DeleteInstance;
import at.jku.isse.designspace.core.operations.property.CreateProperty;
import at.jku.isse.designspace.core.operations.workspace.InformWorkspaceChanges;
import at.jku.isse.designspace.core.operations.workspace.WorkspaceChangeOperation;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.stereotype.Service;
import service.Models.General.ChangeTrackerManager;
import service.Settings;

@Service
public class ConnectServiceImpl implements ConnectService {
    private Connect connect;
    private LanguageWorkspace languageWorkspace;

    @Override
    public Connect connect(User user, Tool tool, Workspace parentWorkspace, Folder folder, boolean isAutoUpdate, boolean isAutoCommit, boolean continuePreviousWork) {
        this.connect = new Connect(user, tool, parentWorkspace, folder, isAutoUpdate, isAutoCommit, continuePreviousWork);
        return connect;
    }

    @Override
    public Connect getConnect() {
        return connect;
    }

    @Override
    public boolean isConnected() {
        return connect != null;
    }

    @Override
    public Connect connectTest(Settings settings) {

        var changeTrackerManager = ChangeTrackerManager.getInstance();

        Connect.init(settings.getName());
        connect = Connect.forProjectTesting(
                settings.getName(),
                settings.getTool(),
                ProjectWorkspace.ALL().stream().filter(workspace -> workspace.getName().equals(settings.getWorkspace()))
                        .findFirst().get(),
                Folder.ALL().stream().filter(x -> x.getName().equals(settings.getFolder())).findFirst().get(),
                settings.getUpdate(),
                settings.getCommit(),
                settings.getPrevWork()
        );

        connect.subscribeChanges(new WorkspaceListener() {
            @Override
            public void notifyWorkspaceOperation(Workspace workspace, WorkspaceOperation workspaceOperation) {
                if (workspaceOperation instanceof InformWorkspaceChanges iwc) {
                    ((ToolWorkspace) workspace).acceptChanges(iwc.changesPayload);
                    changeTrackerManager.populateTrackers(connect.getFolder().getInstances(connect.getToolWorkspace()));
                }

                if (workspaceOperation instanceof WorkspaceChangeOperation wc) {
                    for (var change : wc.getChanges()) {
                        for (var operation : change.getExternalOperations()) {
                            for (ElementOperation internalOperation : operation.getInternalOperations()) {
                                if (internalOperation instanceof CreateInstance ci) {
                                    var instance = connect.getToolWorkspace().getInstance(ci.elementId);
                                    var inst = changeTrackerManager.getTrackers().getOrDefault(ci.elementId, null);
                                    if (inst == null)
                                        changeTrackerManager.addToTrackerAfterFetch(instance);
                                } else if (internalOperation instanceof CreateProperty createProperty) {
                                    var instance = connect.getToolWorkspace().getInstance(createProperty.elementId);
                                    var inst = changeTrackerManager.getTrackedDTOs().getOrDefault(instance.getId(), null);
                                    if (inst != null) {
                                        changeTrackerManager.getTrackers().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                        changeTrackerManager.addToTracker(instance);
                                    }
                                } else if (internalOperation instanceof DeleteInstance di) {
                                    var instance = connect.getToolWorkspace().getInstance(di.elementId);
                                    changeTrackerManager.getTrackedDTOs().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                } else if (internalOperation instanceof PropertyValueOperation pvo) {
                                    var instance = connect.getToolWorkspace().getInstance(pvo.elementId);
                                    var inst = changeTrackerManager.getTrackedDTOs().getOrDefault(instance.getId(), null);
                                    if (inst != null) {
                                        changeTrackerManager.getTrackers().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                        changeTrackerManager.addToTracker(instance);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        this.languageWorkspace = (LanguageWorkspace) LanguageWorkspace.ROOT.getChildWorkspace("STA v1");
        return connect;
    }

    @Override
    public boolean getInitialConnectUpdatesFromServer() {
        var trackedDTOs = ChangeTrackerManager.getInstance().getTrackedDTOs();
        if (!trackedDTOs.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void conclude() {
        var tracker = ChangeTrackerManager.getInstance();
        try {
            connect.getToolWorkspace().concludeChange("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var changes = connect.getToolWorkspace().getAllChanges();

        changes.forEach(change -> {
            var op = change.getExternalOperations();
            op.forEach(operation -> {
                var element = connect.getToolWorkspace().getElement(operation.elementId);
                var id = element.getId();
                long generalId = 0;
                if (tracker.compareExistingId(operation.elementId, id))
                {
                    tracker.trackField(operation.elementId, "id", id);
                    tracker.updateTrackingKey(operation.elementId, id);
                    generalId = id;
                } else {
                    generalId = operation.elementId;
                }
                tracker.trackField(generalId, "name", element.getName());
                tracker.trackField(generalId, "properties", element.getAccessedProperties());
                tracker.trackField(generalId, "instanceType", element.getInstanceType());
                tracker.trackField(generalId, "element", element);
                tracker.trackField(generalId, "propertyTypes", element.getInstanceType().getPropertyTypes());
            });
        });
    }
}