package service.Models.Instance;

import at.jku.isse.designspace.core.model.Instance;
import com.fasterxml.jackson.databind.JsonNode;
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
        var instance = getInstance(id);

        if (instance == null)
            return null;

        throw new UnsupportedOperationException("Not implemented");
    }

    public void deleteInstance(Long id) {
        var instance = getInstance(id);

        if (instance != null)
            instance.delete();
    }

    public Instance getInstance(Long id) {
        return connectService.getConnect().getToolWorkspace().getInstance(id);
    }

    public Instance add(Long id, String propertyType, JsonNode value) {
        try {
            var instance = getInstance(id);

            if (instance == null)
                return null;

            var type = instance.getInstanceType().getPropertyTypes().stream()
                    .filter(it -> it.getName().equals(propertyType))
                    .findFirst().orElse(null);

            if (type == null)
                return null;

            instance.add(type, value);

            return instance;

        } catch (Exception e) {
            return null;
        }
    }

    public Instance set(Long id, String propertyType, JsonNode value) {
        try {
            var instance = getInstance(id);

            if (instance == null)
                return null;

            var type = instance.getInstanceType().getPropertyTypes().stream()
                    .filter(it -> it.getName().equals(propertyType))
                    .findFirst().orElse(null);

            if (type == null)
                return null;

            var result = instance.set(type, StaticServices.parseJsonNode(value, type.getReferencedInstanceType()));

            if (result)
                return instance;

            return null;

        } catch (Exception e) {
            return null;
        }
    }
}
