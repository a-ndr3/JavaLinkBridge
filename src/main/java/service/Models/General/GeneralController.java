package service.Models.General;

import at.jku.isse.designspace.sdk.Connect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Models.DTOs.BaseDTO;
import service.Models.DTOs.InstanceDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GeneralController {
    private final GeneralService generalService;

    @Autowired
    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @PostMapping("/general/conclude")
    public ResponseEntity<List<BaseDTO>> conclude() {
        try {
            generalService.conclude();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        var results = ChangeTrackerManager.getInstance().getUpdatedDTOs();
        ChangeTrackerManager.getInstance().markAllAsFetched();
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/general/getInitialConnectUpdatesFromServer")
    public ResponseEntity<List<BaseDTO>> getInitialConnectUpdatesFromServer() {
        try {
            if (generalService.getInitialConnectUpdatesFromServer()) {
                var response = ChangeTrackerManager.getInstance()
                        .getTrackedDTOs().values()
                        .stream().filter(x->x.getClass() == InstanceDTO.class)
                        .collect(Collectors.toList()); //todo change to fetch all
                ChangeTrackerManager.getInstance().markAllAsFetched();
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/general/checkUpdates")
    public ResponseEntity<List<BaseDTO>> checkUpdates() {
        try {
            var response = ChangeTrackerManager.getInstance().getUpdatedDTOs();
            if (ChangeTrackerManager.getInstance().hasUpdates())
                ChangeTrackerManager.getInstance().markAllAsFetched();
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
