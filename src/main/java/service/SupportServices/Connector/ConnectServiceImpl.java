package service.SupportServices.Connector;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.stereotype.Service;
import service.Models.General.ChangeTrackerManager;

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
    public Connect connectTest(String lwsName) {
        Connect.init("Alice");
        connect = Connect.forProjectTesting(
                "Alice",
                "STA Tool v1",
                ProjectWorkspace.ROOT,
                Folder.PROJECTS,
                true,
                true,
                true
        );
        connect.subscribeChanges();
        this.languageWorkspace = (LanguageWorkspace) LanguageWorkspace.ROOT.getChildWorkspace("STA v1");
        return connect;
    }

    private void subscribeToWorkspaceEvents() {
        connect.getToolWorkspace().addListener(new WorkspaceListener() {
            @Override
            public void notifyWorkspaceOperation(Workspace workspace, WorkspaceOperation workspaceOperation) {
                System.out.println("Workspace operation: " + workspaceOperation);
            }

            public void workspaceChanged(WorkspaceOperation operation) {
                System.out.println("Workspace changed: " + operation);
            }
        });
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
                }
            });
        });
    }
}