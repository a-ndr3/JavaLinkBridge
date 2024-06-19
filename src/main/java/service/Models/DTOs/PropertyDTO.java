package service.Models.DTOs;

public class PropertyDTO extends BaseDTO {

    public PropertyDTO(Long id) {
        super(id);
    }

    public PropertyDTO(Long id, String name) {
        super(id, name);
    }
}
