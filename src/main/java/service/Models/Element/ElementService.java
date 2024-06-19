package service.Models.Element;

import at.jku.isse.designspace.core.model.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SupportServices.Connector.ConnectService;

@Service
public class ElementService {
    private final ConnectService connectService;

    @Autowired
    public ElementService(ConnectService connectService) {
        this.connectService = connectService;
    }

    public void deleteElement(Long id) {
        connectService.getConnect().getToolWorkspace().getElement(id).delete();
    }

    public Element getElement(Long id) {
        return connectService.getConnect().getToolWorkspace().getElement(id);
    }
}
