package service.Models.General;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChangeTracker<T> {
    private T original;
    private Map<String, Object> originalValues = new HashMap<>();
    private Map<String, Object> currentValues = new HashMap<>();
    private boolean isNew = false;

    public ChangeTracker(T original, boolean isNew) {
        this.original = original;
        this.isNew = isNew;
        if (isNew) {
            registerNewObject(original);
        } else {
            initializeOriginalValues(original);
        }
    }

    private void registerNewObject(T object) {
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                originalValues.put(field.getName(), value);
                currentValues.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeOriginalValues(T object) {
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                originalValues.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void track(String fieldName, Object currentValue) {
        currentValues.put(fieldName, currentValue);
    }

    public Map<String, Object> getChanges() {
        if (isNew) {
            return new HashMap<>(currentValues);
        }
        Map<String, Object> changes = new HashMap<>();
        currentValues.forEach((key, value) -> {
            if (!Objects.equals(value, originalValues.get(key))) {
                changes.put(key, value);
            }
        });
        return changes;
    }

    public boolean hasChanges() {
        return !getChanges().isEmpty();
    }

    public boolean isNew() {
        return isNew;
    }
}
