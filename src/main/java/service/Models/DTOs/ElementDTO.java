package service.Models.DTOs;

public class ElementDTO {
    Long id;

    String name;

    public ElementDTO(Long id) {
        this.id = id;
    }

    public ElementDTO(Long id, String name) {
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
