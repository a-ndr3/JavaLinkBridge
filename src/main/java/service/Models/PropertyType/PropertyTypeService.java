package service.Models.PropertyType;

import at.jku.isse.designspace.core.model.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

import java.util.List;

@Service
public class PropertyTypeService {

    private final ConnectService connectService;

    @Autowired
    public PropertyTypeService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public List<PropertyType> getPropertyTypes(Long instanceTypeId) {
        return connectService.getConnect().getToolWorkspace()
                .getInstanceType(instanceTypeId).getPropertyTypes().stream().toList();
    }
}
