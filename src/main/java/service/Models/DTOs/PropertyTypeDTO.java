package service.Models.DTOs;

public class PropertyTypeDTO {
    Long id;
    String name;

    public PropertyTypeDTO(Long id) {
        this.id = id;
    }

    public PropertyTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
