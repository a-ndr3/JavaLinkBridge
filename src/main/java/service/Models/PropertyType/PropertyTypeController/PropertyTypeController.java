package service.Models.PropertyType.PropertyTypeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.DTOs.PropertyTypeDTO;
import service.Models.PropertyType.PropertyTypeService;
import service.SupportServices.ExceptionHandler.CustomStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PropertyTypeController {

    private final PropertyTypeService propertyTypeService;

    @Autowired
    public PropertyTypeController(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @GetMapping("/propertyType/getTypes/{instanceTypeId}")
    public ResponseEntity<List<PropertyTypeDTO>> getPropertyTypes(@PathVariable Long instanceTypeId) {
        var result = new ArrayList<PropertyTypeDTO>();
        try {
            var propertyTypes = propertyTypeService.getPropertyTypes(instanceTypeId);

            for (var propertyType : propertyTypes) {
                result.add(new PropertyTypeDTO(propertyType.getId(), propertyType.getName()));
            }

            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileGettingData.getStatusCode()).build();
        }

        return ResponseEntity.ok(result);
    }
}
