package service.Models.ServerCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.Models.ServerCommands.ServerCommandsController.ServerCommands;
import service.SupportServices.Connector.ConnectService;
import service.SupportServices.ExceptionHandler.CustomStatus;

@Service
public class ServerCommandsService {

    private final ConnectService connectService;

    @Autowired
    public ServerCommandsService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public CustomStatus executeCommand(ServerCommands command) {
        try {
            switch (command) {
                case Conclude:
                    connectService.getConnect().getToolWorkspace().concludeChange("");
                    break;
                case Commit:
                    connectService.getConnect().getToolWorkspace().commit("");
                    break;
                case Update:
                    connectService.getConnect().getToolWorkspace().update();
                    break;
            }
        } catch (Exception e) {
            return CustomStatus.ErrorWhileExecutingServerCommand;
        }
        return CustomStatus.Success;
    }
}
