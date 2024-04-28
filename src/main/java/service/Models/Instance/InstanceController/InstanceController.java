package service.Models.Instance.InstanceController;

import at.jku.isse.designspace.core.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.Instance.InstanceController.Requests.AddRequest;
import service.Models.Instance.InstanceController.Requests.CreateInstanceRequest;
import service.Models.Instance.InstanceController.Requests.UpdateInstanceRequest;
import service.Models.Instance.InstanceService;
import service.SupportServices.ExceptionHandler.CustomStatus;

@RestController
@RequestMapping("/api")
public class InstanceController {

    private final InstanceService instanceService;

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/instance")
    public ResponseEntity<Instance> createInstance(@RequestBody CreateInstanceRequest request) {
        Instance result;

        try {
            result = instanceService.createInstance(request.name(), request.instanceTypeName());
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/instance/{id}")
    public ResponseEntity<Instance> updateInstance(@PathVariable Long id, @RequestBody UpdateInstanceRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
//        Instance result = instanceService.updateInstance(id, request.data);
//        if (result == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        //return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @DeleteMapping("/instance/{id}")
    public ResponseEntity<Instance> deleteInstance(@PathVariable Long id) {
        try {
            instanceService.deleteInstance(id);
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstance.getStatusCode()).build();
        }

        var instance = instanceService.getInstance(id);

        if (instance.isDeleted())
            return ResponseEntity.status(CustomStatus.Success.getStatusCode()).body(instance);
        else
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstance.getStatusCode()).build();
    }

    @GetMapping("/instance/{id}")
    public ResponseEntity<Instance> getInstance(@PathVariable Long id) {
        var instance = instanceService.getInstance(id);

        if (instance == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(instance);
    }

    @PostMapping("/instance/add/{id}")
    public ResponseEntity<Instance> add(@PathVariable Long id, @RequestBody AddRequest request) {
        Instance result = instanceService.add(id, request.getPropertyType(), request.getValue());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/instance/set/{id}")
    public ResponseEntity<Instance> set(@PathVariable Long id, @RequestBody AddRequest request) {
        Instance result = instanceService.set(id, request.getPropertyType(), request.getValue());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(result);
    }
}