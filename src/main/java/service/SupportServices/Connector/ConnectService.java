package service.SupportServices.Connector;

import at.jku.isse.designspace.core.model.Folder;
import at.jku.isse.designspace.core.model.Tool;
import at.jku.isse.designspace.core.model.User;
import at.jku.isse.designspace.core.model.Workspace;
import at.jku.isse.designspace.sdk.Connect;

public interface ConnectService {
    Connect connect(User user, Tool tool, Workspace parentWorkspace, Folder folder, boolean isAutoUpdate, boolean isAutoCommit, boolean continuePreviousWork);
    Connect getConnect();
    boolean isConnected();
}
