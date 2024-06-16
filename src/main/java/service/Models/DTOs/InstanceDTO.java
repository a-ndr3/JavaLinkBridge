package service.Models.DTOs;

import at.jku.isse.designspace.core.model.PropertyType;

import java.util.ArrayList;

public class InstanceDTO extends BaseDTO {
    private ArrayList<PropertyTypeDTO> propertyType;

    public InstanceDTO(Long id) {
        super(id);
    }

    public InstanceDTO(Long id, String name) {
        super(id,name);
    }

    public InstanceDTO(Long id, String name, ArrayList<PropertyTypeDTO> propertyType) {
        super(id,name);
        this.propertyType = propertyType;
    }

    public ArrayList<PropertyTypeDTO> getPropertyTypes() {
        return propertyType;
    }
}
