package service.Models.Property;

import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.Property;
import at.jku.isse.designspace.core.model.PropertyType;
import org.springframework.stereotype.Service;
import service.Models.General.ChangeTrackerManager;
import service.SupportServices.Connector.ConnectService;

import java.util.List;

import static at.jku.isse.designspace.core.model.InstanceType.*;

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

    private boolean setProperty(Instance instance, PropertyType pt, Object value) {
        var type = pt.getReferencedInstanceType();
        if (type == STRING)
            return instance.set(pt, String.valueOf(value));
        else if (type == BOOLEAN)
            return instance.set(pt, Boolean.parseBoolean(value.toString()));
        else if (type == INTEGER)
            return instance.set(pt, Long.parseLong(value.toString()));
        else if (type == REAL)
            return instance.set(pt, Double.parseDouble(value.toString()));
        else if (type == DATE)
            return instance.set(pt, value);
        return false;
    }

    private boolean setProperty(Property property, Object value) {
        return property.getElement().set(property.getPropertyType(), value);
    }

    public void setProperty(Long instanceId, String propertyName, Object value) {
        Instance instance = connectService.getConnect().getToolWorkspace().getInstance(instanceId);

        if (instance == null)
            return;

        var property = instance.getAccessedProperties().stream()
                .filter(p -> p.getName().equals(propertyName))
                .findFirst()
                .orElse(null);

        if (property == null) {
            var propertyType = instance.getPropertyType(propertyName);
            if (propertyType == null)
                return;

            if (!propertyType.isPrimitive()) {
                var refInstance = connectService.getConnect().getToolWorkspace().getInstance(Long.parseLong(value.toString()));
                instance.set(propertyType, refInstance);
            } else {
                setProperty(instance, propertyType, value);
            }
        } else {
            setProperty(property, value);
        }
    }

    public void addProperty(Long instanceId, String propertyName, Object value) {
        Instance instance = connectService.getConnect().getToolWorkspace().getInstance(instanceId);

        if (instance == null)
            return; //todo return Errors when they happen in all null checks

        var property = instance.getAccessedProperties().stream()
                .filter(p -> p.getName().equals(propertyName))
                .findFirst()
                .orElse(null);

        if (property == null) {
            var propertyType = instance.getPropertyType(propertyName);
            if (propertyType == null)
                return;

            if (!propertyType.isPrimitive()) {
                var refInstance = connectService.getConnect().getToolWorkspace().getInstance(Long.parseLong(value.toString()));
                instance.add(propertyType, refInstance);
            } else {
                instance.add(propertyType, value);
            }
        } else {
            var propertyType = instance.getPropertyType(propertyName);
            if (propertyType == null)
                return;
            if (!propertyType.isPrimitive()) {
                var refInstance = connectService.getConnect().getToolWorkspace().getInstance(Long.parseLong(value.toString()));
                instance.add(propertyType, refInstance);
            }
        }
    }
}

