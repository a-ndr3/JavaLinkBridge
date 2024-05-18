package service.Models.General;

import java.util.*;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFields;

public class ChangeTracker<T> {
    private T original;
    private Map<String, Object> originalValues = new HashMap<>();
    private Map<String, Object> currentValues = new HashMap<>();
    private boolean isFetchedForTheFristTime = false;

    public ChangeTracker(T original) {
        this.original = original;
        initializeOriginalValues(original);
    }

    public ChangeTracker(T original, boolean isFetchedForTheFristTime) {
        this.original = original;
        this.isFetchedForTheFristTime = isFetchedForTheFristTime;
        initializeOriginalValues(original);
    }

    public boolean isFetchedForTheFristTime() {
        return isFetchedForTheFristTime;
    }

    public void setFetchedForTheFristTime(boolean fetchedForTheFristTime) {
        isFetchedForTheFristTime = fetchedForTheFristTime;
    }

    private void initializeOriginalValues(T object) {
        Arrays.stream(getAllFields(object.getClass())).forEach(field -> {
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
}
