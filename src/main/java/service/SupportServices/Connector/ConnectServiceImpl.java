package service.SupportServices.Connector;

import at.jku.isse.designspace.core.model.Folder;
import at.jku.isse.designspace.core.model.Tool;
import at.jku.isse.designspace.core.model.User;
import at.jku.isse.designspace.core.model.Workspace;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.stereotype.Service;

@Service
public class ConnectServiceImpl implements ConnectService {
    private Connect connect;

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
}