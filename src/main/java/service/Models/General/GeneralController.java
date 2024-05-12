package service.Models.General;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeneralController {
    private final GeneralService generalService;

    @Autowired
    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @PostMapping("/general/conclude")
    public ResponseEntity<?> conclude() {
        try {
            var responses = generalService.conclude();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }
}
