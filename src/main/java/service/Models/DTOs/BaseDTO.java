package service.Models.DTOs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import service.Models.General.ChangeTrackerManager;

import java.lang.reflect.Field;
import java.util.Arrays;

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
            Field field = Arrays.stream(getAllFields(this.getClass()))
                    .filter(f -> f.getName().equals(propertyName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException("Field not found"));
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set property", e);
        }
    }
}
