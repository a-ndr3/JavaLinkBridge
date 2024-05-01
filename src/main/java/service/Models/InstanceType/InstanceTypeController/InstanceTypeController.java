package service.Models.InstanceType.InstanceTypeController;

import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.InstanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.DTOs.InstanceDTO;
import service.Models.DTOs.InstanceTypeDTO;
import service.Models.Instance.InstanceController.Requests.CreateInstanceRequest;
import service.Models.InstanceType.InstanceTypeController.Requests.CreateInstanceTypeRequest;
import service.Models.InstanceType.InstanceTypeService;
import service.SupportServices.ExceptionHandler.CustomStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InstanceTypeController {

    private final InstanceTypeService instanceService;

    @Autowired
    public InstanceTypeController(InstanceTypeService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping("/instanceType/getTypes")
    public ResponseEntity<List<InstanceTypeDTO>> getInstanceTypes() {
        List<InstanceTypeDTO> result = new ArrayList<>();
        try {
            var instanceTypes = instanceService.getInstanceTypes();

            for (var instanceType : instanceTypes) {
                result.add(new InstanceTypeDTO(instanceType.getId(), instanceType.getName()));
            }

            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileGettingData.getStatusCode()).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/instanceType/create")
    public ResponseEntity<InstanceTypeDTO> createInstanceType(@RequestBody CreateInstanceTypeRequest request) {
        InstanceType result;

        try {
            result = instanceService.createInstanceType(request.parentInstanceId(), request.name());
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var instanceDTO = new InstanceTypeDTO(result.getId(), result.getName());

        return ResponseEntity.ok(instanceDTO);
    }

    @DeleteMapping("/instanceType/delete/{id}")
    public ResponseEntity<Void> deleteInstanceType(@PathVariable Long id) {
        try {
            instanceService.deleteInstanceType(id);
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstance.getStatusCode()).build();
        }

        var instanceType = instanceService.getInstanceType(id);

        if (instanceType.isDeleted())
            return ResponseEntity.status(CustomStatus.Success.getStatusCode()).build();
        else
            return ResponseEntity.status(CustomStatus.ErrorWhileDeletingInstanceType.getStatusCode()).build();
    }
}