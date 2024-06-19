package service.Models.DTOs;

import at.jku.isse.designspace.core.model.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import service.Models.General.ChangeTrackerManager;

import java.lang.reflect.Field;
import java.util.*;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFields;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InstanceDTO.class, name = "InstanceDTO"),
        @JsonSubTypes.Type(value = PropertyDTO.class, name = "PropertyDTO"),
        @JsonSubTypes.Type(value = PropertyTypeDTO.class, name = "PropertyTypeDTO"),
        @JsonSubTypes.Type(value = ElementDTO.class, name = "ElementDTO"),
        @JsonSubTypes.Type(value = InstanceTypeDTO.class, name = "InstanceTypeDTO"),
        @JsonSubTypes.Type(value = WorkspaceDTO.class, name = "WorkspaceDTO")
})
public class BaseDTO {
    private Long id;
    private Long oldId;
    private Map<String, Object> properties = new HashMap<>();
    private String name;

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void addProperty(PropertyTypeDTO propertyType, Object value) {
        properties.put(propertyType.getName(), value);
    }

    public BaseDTO(Long id) {
        this.id = id;
        name = null;
        ChangeTrackerManager.getInstance().addDto(id, this);
    }

    public BaseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        ChangeTrackerManager.getInstance().addDto(id, this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String propertyName, Object value) {
        try {
            Field[] fields = getAllFields(this.getClass());
            Field targetField = null;

            for (Field field : fields) {
                if (field.getName().equals(propertyName)) {
                    targetField = field;
                    break;
                }
            }

            if (targetField != null) {
                targetField.setAccessible(true);

                if (propertyName.equals("properties")) {
                    var valueCollection = (Collection<?>) value;
                    var targetCollection = targetField.get(this);
                    for (var item : valueCollection) {
                        if (item instanceof SingleProperty<?>) {
                            var singleProperty = (SingleProperty<?>) item;
                            var name = singleProperty.getName();
                            String val;
                            if (singleProperty.get() instanceof Instance)
                                val = ((Instance)singleProperty.get()).getName() + "(" + ((Instance)singleProperty.get()).getId() + ")";
                            else
                                val = singleProperty.get().toString();
                            if (((HashMap) targetCollection).containsKey(name))
                                ((HashMap) targetCollection).put(name, val);
                        } else if (item instanceof ListProperty<?>) {
                            var listProperty = (ListProperty<?>) item;
                            for (var listItem : listProperty.get()) {
                                var name = listProperty.getName();
                                var val = listItem;
                                if (((HashMap) targetCollection).containsKey(name))
                                    ((HashMap) targetCollection).put(name, val);
                            }
                        } else if (item instanceof MapProperty<?>) {
                            var mapProperty = (MapProperty<?>) item;
                            var iterator = mapProperty.get().entrySet().iterator();
                            while (iterator.hasNext()) {
                                var entry = (MapProperty) iterator.next();
                                var name = entry.getName();
                                var val = entry.get().toString();
                                if (((HashMap) targetCollection).containsKey(name))
                                    ((HashMap) targetCollection).put(name, val);
                            }
                        } else if (item instanceof SetProperty<?>) {
                            var setProperty = (SetProperty<?>) item;
                            for (var listItem : setProperty.get()) {
                                var name = setProperty.getName();
                                var val = listItem.toString();
                                if (((HashMap) targetCollection).containsKey(name))
                                    ((HashMap) targetCollection).put(name, val);
                            }
                        }
                    }
                } else {
                    targetField.set(this, value);
                }
            } else {

            }
        } catch (Exception e) {

        }
    }
}
