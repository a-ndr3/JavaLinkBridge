package service;

import at.jku.isse.designspace.core.foundation.WorkspaceListener;
import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.core.operations.WorkspaceOperation;
import at.jku.isse.designspace.core.operations.workspace.InformWorkspaceChanges;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import service.SupportServices.Connector.ConnectService;

@SpringBootApplication
public class JLBApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JLBApp.class);
        app.addListeners(new ApplicationStartup());
        app.run(args);
    }

    private static class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            try {
                ApplicationContext context = event.getApplicationContext();
                ConnectService connectService = context.getBean(ConnectService.class);

                //TODO 30.04: add a list of parameters that we get from c# to init the connect

                Connect.init("Alice");
                Workspace workspace = LanguageWorkspace.ROOT.getChildWorkspace("STA v1");

                connectService.connect(User.GET("Alice"), Tool.GET("STA Tool v1"),
                        workspace, Folder.PROJECTS, true, true, true);

            } catch (Exception e) {
                throw new RuntimeException("Error connecting to workspace", e);
            }
        }
    }
}
