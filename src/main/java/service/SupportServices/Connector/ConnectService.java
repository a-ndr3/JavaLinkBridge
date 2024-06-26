package service.SupportServices.Connector;

import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.sdk.Connect;
import service.Settings;

public interface ConnectService {
    Connect connect(User user, Tool tool, Workspace parentWorkspace, Folder folder, boolean isAutoUpdate, boolean isAutoCommit, boolean continuePreviousWork);
    Connect getConnect();
    boolean isConnected();
    Connect connectTest(Settings settings);
    void conclude();
    boolean getInitialConnectUpdatesFromServer();
}
