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
import javafx.application.Platform;
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
                    ChangeTrackerManager.getInstance().populateTrackers(connect.getFolder().getInstances(connect.getToolWorkspace()));
                }

                if (workspaceOperation instanceof WorkspaceChangeOperation wc) {
                    for (var change : wc.getChanges()) {
                        for (var operation : change.getExternalOperations()) {
                            for (ElementOperation internalOperation : operation.getInternalOperations()) {
                                if (internalOperation instanceof CreateInstance ci) {
                                    var instance = connect.getToolWorkspace().getInstance(ci.elementId);
                                    if (ChangeTrackerManager.getInstance().getTrackers().entrySet().stream().noneMatch(entry -> entry.getKey() == ci.elementId))
                                        ChangeTrackerManager.getInstance().addToTrackerAfterFetch(instance);
                                } else if (internalOperation instanceof CreateProperty createProperty) {
                                    var instance = connect.getToolWorkspace().getInstance(createProperty.elementId);
                                    var inst = ChangeTrackerManager.getInstance().getTrackedDTOs().entrySet().
                                            stream().filter(entry -> entry.getKey() == instance.getId())
                                            .findFirst();
                                    if (inst.isPresent()) {
                                        ChangeTrackerManager.getInstance().getTrackers().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                        ChangeTrackerManager.getInstance().addToTracker(instance);
                                    }
                                } else if (internalOperation instanceof DeleteInstance di) {
                                    var instance = connect.getToolWorkspace().getInstance(di.elementId);
                                    ChangeTrackerManager.getInstance().getTrackedDTOs().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                } else if (internalOperation instanceof PropertyValueOperation pvo) {
                                    var instance = connect.getToolWorkspace().getInstance(pvo.elementId);
                                    var inst = ChangeTrackerManager.getInstance().getTrackedDTOs().entrySet().
                                            stream().filter(entry -> entry.getKey() == instance.getId())
                                            .findFirst();
                                    if (inst.isPresent()) {
                                        ChangeTrackerManager.getInstance().getTrackers().entrySet().removeIf(entry -> entry.getKey() == instance.getId());
                                        ChangeTrackerManager.getInstance().addToTracker(instance);
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

    private void subscribeToWorkspaceEvents() {
        connect.getToolWorkspace().addListener(new WorkspaceListener() {
            @Override
            public void notifyWorkspaceOperation(Workspace workspace, WorkspaceOperation workspaceOperation) {
                System.out.println("Workspace operation: " + workspaceOperation);

                if (workspaceOperation instanceof InformWorkspaceChanges iwc) {
                    ((ToolWorkspace) workspace).acceptChanges(iwc.changesPayload);
                    ChangeTrackerManager.getInstance().populateTrackers(connect.getFolder().getInstances(connect.getToolWorkspace().getWorkspace()));
                }
            }

            //                if (workspaceOperation instanceof WorkspaceChangeOperation wc) {
//                    for (var change : wc.getChanges()) {
//                        for (var operation : change.getExternalOperations()) {
//                            for (ElementOperation internalOperation : operation.getInternalOperations()) {
//                                if (internalOperation instanceof CreateInstance ci) {
//
//                                } else if (internalOperation instanceof DeleteInstance di) {
//
//                                } else if (internalOperation instanceof PropertyValueOperation pvo) {
//                                    var instance = connect.getToolWorkspace().getWorkspace().getInstance(pvo.elementId);
//                                    if (instance.getInstanceType().getId() == ??) {
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
            public void workspaceChanged(WorkspaceOperation operation) {
                System.out.println("Workspace changed: " + operation);
            }
        });
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
                if (tracker.compareExistingId(operation.elementId, id)) {
                    tracker.trackField(operation.elementId, "id", id);
                    tracker.updateTrackingKey(operation.elementId, id);
                    tracker.trackField(id, "name", element.getName());
                } else {
                    tracker.trackField(operation.elementId, "name", element.getName());
                }
            });
        });
    }
}