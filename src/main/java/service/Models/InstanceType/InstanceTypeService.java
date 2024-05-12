package service.Models.InstanceType;

import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.InstanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstanceTypeService {

    private final ConnectService connectService;

    @Autowired
    public InstanceTypeService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public Set<InstanceType> getInstanceTypes() {
        return connectService.getConnect().getLanguageWorkspace().getInstanceTypes()
                .stream().filter(i -> !i.isDeleted()).collect(Collectors.toSet());
    }

    public InstanceType createInstanceType(Long id, String name) {
        var connect = connectService.getConnect();

        var instanceType = getInstanceType(id);

        if (instanceType == null)
            return null;

        return InstanceType.create(connect.getToolWorkspace(), name, instanceType, connect.getFolder());
    }

    public void deleteInstanceType(Long id) {
        var instanceType = getInstanceType(id);

        if (instanceType == null)
            return;

        instanceType.delete();
    }

    public InstanceType getInstanceType(Long id) {
        return connectService.getConnect().getLanguageWorkspace().getInstanceType(id);
    }
}
