package service.SupportServices.TestService;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.Folder;
import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.ProjectWorkspace;
import at.jku.isse.designspace.core.model.Workspace;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

@Service
public class TestService {
    private final ConnectService connectService;

    @Autowired
    public TestService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public void DEBUG_createInstanceAsBob() {
        Connect conn2 = Connect.forProjectTesting(
                "Bob",
                "STA Tool v1",
                ProjectWorkspace.ROOT,
                Folder.PROJECTS,
                true,
                true,
                true
        );

        conn2.subscribeChanges(new WorkspaceListener() {
            @Override
            public void notifyWorkspaceOperation(Workspace workspace, WorkspaceOperation workspaceOperation) {

            }
        });

        var instanceType = connectService.getConnect().getLanguageWorkspace().getInstanceType(220L);

        if (instanceType == null)
            return;

        var inst = Instance.create(conn2.getToolWorkspace(), instanceType, "DEBUG_BOB", conn2.getFolder());
        conn2.getToolWorkspace().concludeChange("DEBUG_BOB");
    }
}
