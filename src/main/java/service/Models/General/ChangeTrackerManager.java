package service.Models.General;

import at.jku.isse.designspace.core.model.Instance;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import service.Models.DTOs.BaseDTO;
import service.Models.DTOs.InstanceDTO;
import service.Models.DTOs.PropertyTypeDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeTrackerManager {
    private static ChangeTrackerManager instance;
    private static Map<Long, ChangeTracker<Object>> trackers = new ConcurrentHashMap<>();
    private static Map<Long, BaseDTO> trackedDTOs = new ConcurrentHashMap<>();
    private static Map<Long, Boolean> hasUpdatedSinceLastFetch = new ConcurrentHashMap<>();

    private ChangeTrackerManager() {
    }

    public static synchronized ChangeTrackerManager getInstance() {
        if (instance == null) {
            instance = new ChangeTrackerManager();
        }
        return instance;
    }

    public Map<Long, ChangeTracker<Object>> getTrackers() {
        return trackers;
    }

    public Map<Long, BaseDTO> getTrackedDTOs() {
        return trackedDTOs;
    }

    public boolean exists(Long id) {
        return trackers.containsKey(id);
    }

    public boolean compareExistingId(Long oldId, Long possibleNewId) {
        return trackers.containsKey(oldId) && !trackers.containsKey(possibleNewId);
    }

    public void track(Long id, Object object) {
        trackers.put(id, new ChangeTracker<>(object));
    }

    public void trackAfterFetch(Long id, Object object) {
        trackers.put(id, new ChangeTracker<>(object, true));
    }

    public void addDto(Long id, BaseDTO dto) {
        trackedDTOs.put(id, dto);
        hasUpdatedSinceLastFetch.put(id, true);
    }

    public void trackField(Long id, String fieldName, Object newValue) {
        ChangeTracker<Object> tracker = trackers.get(id);
        if (tracker != null) {
            tracker.track(fieldName, newValue);
        }
    }

    public void updateTrackingKey(Long oldId, Long newId) {
        ChangeTracker<Object> tracker = trackers.remove(oldId);
        BaseDTO dto = trackedDTOs.remove(oldId);
        hasUpdatedSinceLastFetch.put(newId, hasUpdatedSinceLastFetch.remove(oldId));
        if (tracker != null) {
            trackers.put(newId, tracker);
        }
        if (dto != null) {
            dto.setId(newId);
            dto.setOldId(oldId);
            trackedDTOs.put(newId, dto);
        }
    }

    public static List<BaseDTO> getUpdatedDTOs() {
        List<BaseDTO> updates = new ArrayList<>();
        trackers.forEach((id, tracker) -> {
            BaseDTO dto = trackedDTOs.get(id);
            if (hasUpdatedSinceLastFetch.get(id)) {
                if (tracker.hasChanges()) {
                    Map<String, Object> changes = tracker.getChanges();
                    applyChangesToDTO(dto, changes);
                    updates.add(dto);
                } else if (tracker.isFetchedForTheFirstTime()) {
                    updates.add(dto);
                }
            }
            hasUpdatedSinceLastFetch.put(id, false);
        });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String json = objectMapper.writeValueAsString(updates);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updates;
    }

    public Map<String, Object> getTrackedFields(Long id) {
        return trackers.get(id).getCurrentValues();
    }

    public void markAllAsFetched() {
        hasUpdatedSinceLastFetch.keySet().forEach(key -> hasUpdatedSinceLastFetch.put(key, false));
        trackers.values().stream().filter(ChangeTracker::isFetchedForTheFirstTime)
                .forEach(tracker -> tracker.setFetchedForTheFirstTime(false));
    }

    public boolean hasUpdates() {
        return (hasUpdatedSinceLastFetch.values().stream().anyMatch(Boolean::booleanValue)
                || trackers.values().stream().anyMatch(ChangeTracker::isFetchedForTheFirstTime));
    }

    private static void applyChangesToDTO(BaseDTO dto, Map<String, Object> changes) {
        changes.forEach(dto::setProperty);
    }


    public void clearTracker(Long id) {
        trackers.remove(id);
    }

    public void clear() {
        trackers.clear();
    }

    public void addToTracker(Instance instance) {
        if (!exists(instance.getId())) {
            track(instance.getId(), instance);
            addDto(instance.getId(), createInstanceDTO(instance));
        }
    }

    public void addToTrackerAfterFetch(Instance instance) {
        if (!exists(instance.getId())) {
            trackAfterFetch(instance.getId(), instance);
            addDto(instance.getId(), createInstanceDTO(instance));
        }
    }

    private InstanceDTO createInstanceDTO(Instance instance) {
        var propTypes = instance.getInstanceType().getPropertyTypes();
        var dtos = new ArrayList<PropertyTypeDTO>();
        for (var prop : propTypes) {
            var dto = new PropertyTypeDTO(prop);
            dtos.add(dto);
        }
        return new InstanceDTO(instance.getId(), instance.getName(), dtos);
    }

    public void populateTrackers(Set<Instance> instances) {
        //populate both trackers with existing instances
        //create DTO and track it as well
        instances.forEach(instance -> {
            if (!exists(instance.getId())) {
                track(instance.getId(), instance);
                addDto(instance.getId(), new InstanceDTO(instance.getId(), instance.getName()));
            }
        });
    }
}
