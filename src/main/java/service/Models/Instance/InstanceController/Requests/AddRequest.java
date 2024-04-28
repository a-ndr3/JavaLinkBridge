package service.Models.Instance.InstanceController.Requests;

import com.fasterxml.jackson.databind.JsonNode;

public class AddRequest {

    private String propertyType;
    private JsonNode value;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public JsonNode getValue() {
        return value;
    }

    public void setValue(JsonNode value) {
        this.value = value;
    }
}
