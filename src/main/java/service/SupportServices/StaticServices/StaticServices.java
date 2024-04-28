package service.SupportServices.StaticServices;

import at.jku.isse.designspace.core.model.Element;
import at.jku.isse.designspace.core.model.Instance;
import at.jku.isse.designspace.core.model.InstanceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StaticServices {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String serialize(Object obj) {
        try {
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

    public static Object parseJsonNode(JsonNode jsonNode, InstanceType referencedInstanceType) throws JsonProcessingException {
        if (jsonNode.isTextual()) {
            String value = jsonNode.asText();
            if (InstanceType.STRING.isKindOf(referencedInstanceType)) {
                return value;
            }
            if (InstanceType.DATE.isKindOf(referencedInstanceType)) {
                try {
                    LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("ddMMyyyy"));
                    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (jsonNode.isBoolean()) {
            Boolean value = jsonNode.asBoolean();
            if (InstanceType.BOOLEAN.isKindOf(referencedInstanceType)) {
                return value;
            }
        } else if (jsonNode.isInt() || jsonNode.isLong() || jsonNode.isNumber()) {
            Long value = jsonNode.asLong();
            if (InstanceType.INTEGER.isKindOf(referencedInstanceType)) {
                return value;
            }
            if (InstanceType.DATE.isKindOf(referencedInstanceType)) {
                try {
                    LocalDate date = LocalDate.parse(value.toString(), DateTimeFormatter.ofPattern("ddMMyyyy"));
                    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (jsonNode.isDouble()) {
            Double value = jsonNode.asDouble();
            if (InstanceType.REAL.isKindOf(referencedInstanceType)) {
                return value;
            }
        } else if (jsonNode.isArray()) {
            return new ObjectMapper().convertValue(jsonNode, List.class);
        } else if (jsonNode.isObject()) {
            Element value = new ObjectMapper().treeToValue(jsonNode, Element.class);
            if (value.getInstanceType().isKindOf(referencedInstanceType)) {
                return value;
            }
        } else if (jsonNode.isNull())
            return null;
        throw new IllegalArgumentException("JsonNode value is not assignable");
    }
}

