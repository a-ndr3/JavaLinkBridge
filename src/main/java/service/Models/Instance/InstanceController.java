package service.Models.Instance;

import at.jku.isse.designspace.core.model.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Models.DTOs.InstanceDTO;
import service.Models.General.ChangeTrackerManager;
import service.Models.Instance.Requests.CreateInstanceRequest;
import service.SupportServices.ExceptionHandler.CustomStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InstanceController {

    private final InstanceService instanceService;

    private final ChangeTrackerManager changeTrackerManager = ChangeTrackerManager.getInstance();

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/instance/create")
    public ResponseEntity<InstanceDTO> createInstance(@RequestBody CreateInstanceRequest request) {
        Instance result;

        try {
            result = instanceService.createInstance(request.id(), request.name());
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var inst = (InstanceDTO) changeTrackerManager.getTrackedDTOs().get(result.getId());

        return ResponseEntity.ok(inst);
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

    @GetMapping("/instance/getInstances/{typeId}")
    public ResponseEntity<List<InstanceDTO>> getInstances(@PathVariable(required = false) Long typeId) {
        var result = new ArrayList<InstanceDTO>();
        try {
            if (typeId != null) {
                var instances = instanceService.getInstances(typeId);

                if (instances == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                for (var instance : instances) {
                    if (changeTrackerManager.exists(instance.getId())) {
                        result.add((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
                    } else {
                        changeTrackerManager.addToTracker(instance);
                        result.add((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
                    }
                }
            } else {
                var instances = instanceService.getInstances();

                if (instances == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                for (var instance : instances) {
                    if (changeTrackerManager.exists(instance.getId())) {
                        result.add((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
                    } else {
                        changeTrackerManager.addToTracker(instance);
                        result.add((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
                    }
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileGettingData.getStatusCode()).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/instance/get/{id}")
    public ResponseEntity<InstanceDTO> getInstance(@PathVariable Long id) {
        var instance = instanceService.getInstance(id);

        if (instance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (changeTrackerManager.exists(instance.getId())) {
            return ResponseEntity.ok((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
        } else {
            changeTrackerManager.addToTracker(instance);
            return ResponseEntity.ok((InstanceDTO) changeTrackerManager.getTrackedDTOs().get(instance.getId()));
        }
    }

    @PostMapping("/instance/DEBUG_BOB")
    public ResponseEntity<Void> DEBUG_createInstanceAsBob() {
        try {
            instanceService.DEBUG_createInstanceAsBob();
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        return ResponseEntity.status(CustomStatus.Success.getStatusCode()).build();
    }
}