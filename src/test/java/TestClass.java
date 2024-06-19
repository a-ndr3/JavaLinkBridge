import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.sdk.Connect;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

public class TestClass {
    @Test @Disabled
    public void test() {
        Connect.init("Bob");
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

        var instanceType = conn2.getLanguageWorkspace().getInstanceType(220L);

        if (instanceType == null)
            return;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 1000; j++) {
                Instance.create(conn2.getToolWorkspace(), instanceType, "DEBUG_BOB"+i+j, conn2.getFolder());
            }
            conn2.getToolWorkspace().concludeChange("DEBUG_BOB"+i);
        }
        stopWatch.stop();
        System.out.println("Time taken: " + stopWatch.getTotalTimeMillis() / 1000);

        conn2.getToolWorkspace().concludeChange("DEBUG_BOB");
        conn2.disconnect();
    }
}
