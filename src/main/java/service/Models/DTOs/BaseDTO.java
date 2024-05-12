package service.Models.DTOs;

import service.Models.General.ChangeTrackerManager;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFields;

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
            //Arrays.stream(getAllFields(object.getClass())).forEach(field -> {
            //            field.setAccessible(true);
            Field field = Arrays.stream(getAllFields(this.getClass()))
                    .filter(f -> f.getName().equals(propertyName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException("Field not found"));
           // Field field = this.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set property", e);
        }
    }
}
