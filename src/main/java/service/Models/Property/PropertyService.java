package service.Models.Property;

import at.jku.isse.designspace.core.model.Property;
import at.jku.isse.designspace.core.model.PropertyType;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

import java.util.List;

@Service
public class PropertyService {
    private final ConnectService connectService;

    public PropertyService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public List<Property> getProperties(Long instanceId) {
        var instance = connectService.getConnect().getToolWorkspace().getInstance(instanceId);

        if (instance == null)
            return null;

        return instance.getAccessedProperties().stream().toList();
    }

    public void setProperty(Long instanceId, Long propertyId, Object value) {
        var instance = connectService.getConnect().getToolWorkspace().getInstance(instanceId);

        if (instance == null)
            return;

        setProperty(propertyId, value);
    }

    private void setProperty(Long propertyId, Object value) {
        var property = connectService.getConnect().getToolWorkspace().getAccessedProperties().stream()
                .filter(p -> p.getElement().getId() == propertyId)
                .findFirst()
                .orElse(null);

        if (property == null)
            return;

        setProperty(property, value);
    }

    private boolean setProperty(Property property, Object value) {
        return property.getElement().set(property.getPropertyType(), value);
    }

    public void setProperty(Long instanceId, String propertyName, Object value) {
        var instance = connectService.getConnect().getToolWorkspace().getInstance(instanceId);

        if (instance == null)
            return;

        var property = instance.getAccessedProperties().stream()
                .filter(p -> p.getName().equals(propertyName))
                .findFirst()
                .orElse(null);

        if (property == null)
            return;

        setProperty(property, value);
    }
}

