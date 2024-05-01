package service.Models.Instance.InstanceController;

import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.InstanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.DTOs.InstanceTypeDTO;
import service.Models.Instance.InstanceController.Requests.CreateInstanceRequest;
import service.Models.Instance.InstanceService;
import service.SupportServices.ExceptionHandler.CustomStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InstanceController {

    private final InstanceService instanceService;

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/instance/create")
    public ResponseEntity<InstanceTypeDTO> createInstance(@RequestBody CreateInstanceRequest request) {
        Instance result;

        try {
            result = instanceService.createInstance(request.id(), request.name());
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var instanceTypeDTO = new InstanceTypeDTO(result.getId(), result.getName());

        return ResponseEntity.ok(instanceTypeDTO);
    }

    @DeleteMapping("/instance/delete/{id}")
    public ResponseEntity<Void> deleteInstance(@PathVariable Long id) {
        try {
            instanceService.deleteInstance(id);
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstance.getStatusCode()).build();
        }

        var instance = instanceService.getInstance(id);

        if (instance.isDeleted())
            return ResponseEntity.status(CustomStatus.Success.getStatusCode()).build();
        else
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstance.getStatusCode()).build();
    }

    @GetMapping("/property/getInstances/{typeId}")
    public ResponseEntity<List<InstanceTypeDTO>> getInstances(@PathVariable Long typeId) {
        var result = new ArrayList<InstanceTypeDTO>();
        try {
            var instances = instanceService.getInstances(typeId);

            if (instances == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            for (var instance : instances) {
                result.add(new InstanceTypeDTO(instance.getId(), instance.getName()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileGettingData.getStatusCode()).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/instance/getAll")
    public ResponseEntity<List<InstanceTypeDTO>> getAllInstances() {
        var result = new ArrayList<InstanceTypeDTO>();
        try {
            var instances = instanceService.getInstances();

            if (instances == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            for (var instance : instances) {
                result.add(new InstanceTypeDTO(instance.getId(), instance.getName()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileGettingData.getStatusCode()).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/instance/get/{id}")
    public ResponseEntity<InstanceTypeDTO> getInstance(@PathVariable Long id) {
        var instance = instanceService.getInstance(id);

        if (instance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var instanceTypeDTO = new InstanceTypeDTO(instance.getId(), instance.getName());

        return ResponseEntity.ok(instanceTypeDTO);
    }
}