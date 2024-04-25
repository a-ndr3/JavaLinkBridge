package service.Models.Instance.InstanceController;

import at.jku.isse.designspace.core.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.Instance.InstanceService;
import service.SupportServices.ExceptionHandler.CustomStatus;
import service.SupportServices.StaticServices.StaticServices;

@RestController
@RequestMapping("/api")
public class InstanceController {

    private final InstanceService instanceService;

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/instance")
    public ResponseEntity<String> createInstance(@RequestBody CreateInstanceRequest request) {
        Instance result = instanceService.createInstance(request.name(), request.instanceTypeName());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var parsed = StaticServices.serialize(result);

        if (parsed == null) {
            return ResponseEntity.status(CustomStatus.JsonMappingWritingException.getStatusCode())
                    .body("Error while parsing the result to JSON.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(parsed);
    }

    @PutMapping("/instance/{id}")
    public ResponseEntity<String> updateInstance(@PathVariable Long id, @RequestBody UpdateInstanceRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
//        Instance result = instanceService.updateInstance(id, request.data);
//        if (result == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        //return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/instance/{id}")
    public ResponseEntity<Void> deleteInstance(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
//        instanceService.deleteInstance(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}