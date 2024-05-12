package service.Models.DTOs;

public class ElementDTO extends BaseDTO {

    public ElementDTO(Long id) {
        super(id);
    }

    public ElementDTO(Long id, String name) {
        super(id, name);
    }
}
