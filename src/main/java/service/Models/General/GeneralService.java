package service.Models.General;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

import java.util.List;

@Service
public class GeneralService {
    private final ConnectService connectService;

    @Autowired
    public GeneralService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public void conclude() {
        connectService.conclude();
    }

    public boolean getInitialConnectUpdatesFromServer() {
        return connectService.getInitialConnectUpdatesFromServer();
    }
}
