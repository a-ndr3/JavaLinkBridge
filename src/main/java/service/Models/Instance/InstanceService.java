package service.Models.Instance;

import at.jku.isse.designspace.core.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;
import service.SupportServices.StaticServices.StaticServices;

@Service
public class InstanceService {

    private final ConnectService connectService;

    @Autowired
    public InstanceService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public Instance createInstance(String name, String instanceTypeName) {
        var connect = connectService.getConnect();

        var instanceType = connect.getToolWorkspace().getParentWorkspace().getInstanceTypes().stream()
                .filter(it -> it.getName().equals(instanceTypeName))
                .findFirst().orElse(null);

        if (instanceType == null)
            return null;

        return Instance.create(connect.getToolWorkspace(), instanceType, name, connect.getFolder());
    }

    public Instance updateInstance(Long id, String data) {
        var instance = connectService.getConnect().getToolWorkspace().getParentWorkspace().getInstance(id);

        if (instance == null)
            return null;

        return StaticServices.instanceUpdater(instance, data);
    }

    public void deleteInstance(Long id) {
        var instance = connectService.getConnect().getToolWorkspace().getParentWorkspace().getInstance(id);

        if (instance != null)
            instance.delete();
    }
}
