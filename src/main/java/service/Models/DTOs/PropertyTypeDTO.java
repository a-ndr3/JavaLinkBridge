package service.Models.DTOs;

import at.jku.isse.designspace.core.model.PropertyType;

import java.util.List;

public class PropertyTypeDTO extends BaseDTO{

    public PropertyTypeDTO(Long id) {
        super(id);
    }

    public PropertyTypeDTO(Long id, String name) {
        super(id, name);
    }

    public PropertyTypeDTO(PropertyType propertyType){
        super(propertyType.getId(), propertyType.getName());
    }
}
