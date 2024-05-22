package service;

import at.jku.isse.designspace.core.model.*;
import at.jku.isse.designspace.domains.STA;
import at.jku.isse.designspace.sdk.Connect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import service.SupportServices.Connector.ConnectService;

@SpringBootApplication
public class JLBApp {

    public static Settings settings;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar **.jar <ConnectionX> where X is the connection id in the config file <ConfigFilePath>");
            return;
        }

        try {
            settings = ConfigLoader.Instance.load(args[0],args[1]);
            if (settings == null) {
                System.out.println("Error loading settings");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error loading settings: " + e.getMessage());
            return;
        }

        try{
        SpringApplication app = new SpringApplication(JLBApp.class);
        app.addListeners(new ApplicationStartup());
        app.run(args);}
        catch (Exception e) {
            throw new RuntimeException("Error starting application", e);
        }
    }

    private static class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            try {
                ApplicationContext context = event.getApplicationContext();
                ConnectService connectService = context.getBean(ConnectService.class);

                connectService.connectTest(settings);

            } catch (Exception e) {
                throw new RuntimeException("Error connecting to workspace", e);
            }
        }
    }
}
