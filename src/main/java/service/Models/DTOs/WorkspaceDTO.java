package service.Models.DTOs;

public class WorkspaceDTO extends BaseDTO {

    public WorkspaceDTO(Long id) {
        super(id);
    }

    public WorkspaceDTO(Long id, String name) {
        super(id, name);
    }
}
