package service.Models.DTOs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import service.Models.General.ChangeTrackerManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

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

    private String name;

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
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
                targetField.set(this, value);
            } else {

            }
        } catch (Exception e) {

        }
    }
}
