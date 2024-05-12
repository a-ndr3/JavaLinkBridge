package service.Models.DTOs;

public class PropertyTypeDTO extends BaseDTO{

    public PropertyTypeDTO(Long id) {
        super(id);
    }

    public PropertyTypeDTO(Long id, String name) {
        super(id, name);
    }
}
