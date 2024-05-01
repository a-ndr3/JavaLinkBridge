package service.Models.Property.PropertyController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.DTOs.PropertyDTO;
import service.Models.Property.PropertyController.Requests.CreatePropertyRequest;
import service.Models.Property.PropertyController.Requests.SetPropertyRequest;
import service.Models.Property.PropertyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/property/getProperties/{instanceId}")
    public ResponseEntity<List<PropertyDTO>> getProperties(@PathVariable Long instanceId) {
        List<PropertyDTO> properties = new ArrayList<>();
        try {
            var props = propertyService.getProperties(instanceId);

            if (props == null) {
                return ResponseEntity.notFound().build();
            }

            for (var prop : props) {
                properties.add(new PropertyDTO(prop.getPropertyType().getId(), prop.getName()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok(properties);
    }

    @PostMapping("/property/{id}/set")
    public ResponseEntity<Void> setProperty(@PathVariable Long id, @RequestBody SetPropertyRequest request) {
        try {
            propertyService.setProperty(id, request.propertyId(), request.data());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }
}
