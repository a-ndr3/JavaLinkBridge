package service.Models.DTOs;

import java.util.Map;

public class DTOFactory {
    public static BaseDTO createDTO(String className, Map<String, Object> changes) {
        switch (className) {
            case "Instance":
                return new InstanceDTO((Long) changes.get("id"), (String) changes.get("name"));
            case "Property":
                return new PropertyDTO((Long) changes.get("id"), (String) changes.get("name"));
            default:
                throw new IllegalArgumentException("Unknown class for DTO creation");
        }
    }
}
