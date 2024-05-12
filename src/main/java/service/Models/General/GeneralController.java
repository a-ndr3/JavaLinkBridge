package service.Models.General;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Models.DTOs.BaseDTO;

import java.util.List;

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
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/general/getInitialConnectUpdatesFromServer")
    public ResponseEntity<List<BaseDTO>> getInitialConnectUpdatesFromServer() {
        try {
            if (generalService.getInitialConnectUpdatesFromServer()) {
                return ResponseEntity.ok().body(ChangeTrackerManager.getInstance().getUpdatedDTOs());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().body(null);
    }
}
