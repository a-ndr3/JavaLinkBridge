package service.Models.ServerCommands.ServerCommandsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Models.Instance.InstanceService;
import service.Models.ServerCommands.ServerCommandsService;
import service.SupportServices.Connector.ConnectService;
import service.SupportServices.ExceptionHandler.CustomStatus;

@RestController
@RequestMapping("/api")
public class ServerCommandsController {

    private final ServerCommandsService serverCommandsService;

    @Autowired
    public ServerCommandsController(ServerCommandsService serverCommandsService) {
        this.serverCommandsService = serverCommandsService;
    }

    @PostMapping("/serverCommands")
    public ResponseEntity<Void> executeCommand(ServerCommands command) {
        var result = serverCommandsService.executeCommand(command); //todo if conclude => send updated data to client in response

        if (result == null || result.equals(CustomStatus.ErrorWhileExecutingServerCommand)) {
            return ResponseEntity.status(CustomStatus.ErrorWhileExecutingServerCommand.getStatusCode()).build();
        }

        return ResponseEntity.ok().build();
    }
}
