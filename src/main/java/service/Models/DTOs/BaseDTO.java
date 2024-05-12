package service.Models.DTOs;

public class BaseDTO {
    private Long id;

    private String name;

    public BaseDTO(Long id) {
        this.id = id;
        name = null;
    }

    public BaseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
