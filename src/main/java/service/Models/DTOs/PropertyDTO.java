package service.Models.DTOs;

public class PropertyDTO {
    Long id;
    String name;

    public PropertyDTO(Long id) {
        this.id = id;
    }

    public PropertyDTO(Long id, String name) {
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
