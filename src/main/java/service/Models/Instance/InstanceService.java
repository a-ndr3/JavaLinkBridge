package service.Models.Instance;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.Models.General.ChangeTrackerManager;
import service.SupportServices.Connector.ConnectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InstanceService{

    private final ConnectService connectService;

    @Autowired
    public InstanceService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public Instance createInstance(Long id, String name) {
        var connect = connectService.getConnect();

        var instanceType = getInstanceType(id);

        if (instanceType == null)
            return null;

        var instance = Instance.create(connect.getToolWorkspace(), instanceType, name, connect.getFolder());

        ChangeTrackerManager.getInstance().track(instance.getId(), instance);

        return instance;
    }
    
    public void deleteInstance(Long id) {
        var instance = getInstance(id);

        if (instance != null)
            instance.delete();
    }

    public Instance getInstance(Long id) {
        return connectService.getConnect().getToolWorkspace().getInstance(id);
    }

    public List<Instance> getInstances(Long instanceTypeId) {
        var connect = connectService.getConnect();

        var instanceType = getInstanceType(instanceTypeId);

        if (instanceType == null)
            return new ArrayList<>();

        return connect.getFolder().getInstances(connect.getToolWorkspace()).stream().filter(
                x -> x.getInstanceType().equals(instanceType)).toList();
    }

    public Set<Instance> getInstances() {
        return connectService.getConnect().getFolder().getInstances(connectService.getConnect().getToolWorkspace());
    }

    public InstanceType getInstanceType(Long id){
        return connectService.getConnect().getLanguageWorkspace().getInstanceType(id);
    }
}
