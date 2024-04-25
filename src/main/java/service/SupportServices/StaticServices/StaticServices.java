package service.SupportServices.StaticServices;

import at.jku.isse.designspace.core.model.Instance;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StaticServices {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String serialize(Object obj) {
        try{
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object deserialize(String json, Class<?> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static Instance instanceUpdater(Instance instance, String data){
        // update an instance with a given data
        return instance;
    }
}
