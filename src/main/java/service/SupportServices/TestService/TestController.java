package service.SupportServices.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Models.General.ChangeTrackerManager;
import service.SupportServices.ExceptionHandler.CustomStatus;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;
    private final ApplicationContext applicationContext; //to call beans -> InstanceService instanceService = applicationContext.getBean(InstanceService.class);
    private final ChangeTrackerManager changeTrackerManager = ChangeTrackerManager.getInstance();

    @Autowired
    public TestController(TestService testService, ApplicationContext applicationContext) {
        this.testService = testService;
        this.applicationContext = applicationContext;
    }

    @PostMapping("/test/create_instance_as_bob")
    public ResponseEntity<Void> DEBUG_createInstanceAsBob() {
        try {
            testService.DEBUG_createInstanceAsBob();
        } catch (Exception e) {
            return ResponseEntity.status(CustomStatus.ErrorWhileCreatingInstance.getStatusCode()).build();
        }

        return ResponseEntity.status(CustomStatus.Success.getStatusCode()).build();
    }
}

