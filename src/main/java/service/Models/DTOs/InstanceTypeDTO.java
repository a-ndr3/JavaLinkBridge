package service.Models.DTOs;

public class InstanceTypeDTO {
    private Long id;
    private String name;


    public InstanceTypeDTO(Long id) {
        this.id = id;
    }

    public InstanceTypeDTO(Long id, String name) {
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
