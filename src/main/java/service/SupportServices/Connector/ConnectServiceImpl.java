package service.SupportServices.Connector;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.stereotype.Service;
import service.Models.DTOs.BaseDTO;
import service.Models.DTOs.DTOFactory;
import service.Models.General.ChangeTracker;

import java.util.ArrayList;
import java.util.List;

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

    private List<BaseDTO> generateChangeDTOs(List<ChangeTracker<?>> trackers) {
        List<BaseDTO> changedDTOs = new ArrayList<>();
        for (ChangeTracker<?> tracker : trackers) {
            if (tracker.hasChanges()) {
                BaseDTO dto = DTOFactory.createDTO(tracker.getClass().getSimpleName(), tracker.getChanges());
                changedDTOs.add(dto);
            }
        }
        return changedDTOs;
    }

    @Override
    public List<?> conclude() {
//        List<ChangeTracker<?>> trackers = new ArrayList<>();
//
//        List<BaseDTO> changes = generateChangeDTOs(trackers);
//
//        if (!changes.isEmpty()) {
//
//        } else {
//
//        }
//
//        var originalInstance = connect.getToolWorkspace().getUnconcludedExternalElementOperations().get(0);
//        var instance = connect.getToolWorkspace().getInstance(originalInstance.elementId);
//        var instanceDTO = new InstanceDTO(instance.getId(), instance.getName());
//
//        try {
//            connect.getToolWorkspace().concludeChange("");
//        } catch (Exception e) {
//            return response;
//        }
//
//        var changes = connect.getToolWorkspace().getAllChanges();
//
//        for (var change : changes){
//            var newObj = connect.getToolWorkspace().getInstance(change.externalOperations.get(0).elementId);
//            var newConv = genConv.getChanges(newObj);
//            System.out.println(newConv);
//        }
//        return response;
//    }
        return null;
    }
}