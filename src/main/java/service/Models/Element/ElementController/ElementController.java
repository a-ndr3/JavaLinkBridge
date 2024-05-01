package service.Models.Element.ElementController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Models.DTOs.ElementDTO;
import service.Models.Element.ElementService;

@RestController
@RequestMapping("/api")
public class ElementController {
    private final ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    //todo implement
}
