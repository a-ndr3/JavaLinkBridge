package service;

import at.jku.isse.designspace.sdk.Connect;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public enum ConfigLoader {
    Instance;

    public Settings load(String connectionId, String path){
        try {
            Properties properties = new Properties();

            try (FileReader reader = new FileReader(path)) {
                properties.load(reader);

                String name = properties.getProperty("name");
                String tool = properties.getProperty("tool");
                String workspace = properties.getProperty("workspace");
                String folder = properties.getProperty("folder");
                boolean flag1 = Boolean.parseBoolean(properties.getProperty("update"));
                boolean flag2 = Boolean.parseBoolean(properties.getProperty("commit"));
                boolean flag3 = Boolean.parseBoolean(properties.getProperty("previousWork"));

                return new Settings(name, tool, workspace, folder, flag1, flag2, flag3);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
