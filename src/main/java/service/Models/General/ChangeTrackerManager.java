package service.Models.General;

import at.jku.isse.designspace.core.model.Instance;
import service.Models.DTOs.BaseDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeTrackerManager {
    private static ChangeTrackerManager instance;
    private static Map<Long, ChangeTracker<Object>> trackers = new ConcurrentHashMap<>();
    private static Map<Long, BaseDTO> trackedDTOs = new ConcurrentHashMap<>();

    private ChangeTrackerManager() {}

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

    public void addDto(Long id, BaseDTO dto) {
        trackedDTOs.put(id, dto);
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
            if (tracker.hasChanges()) {
                Map<String, Object> changes = tracker.getChanges();
                applyChangesToDTO(dto, changes);
                updates.add(dto);
            }
        });
        return updates;
    }

    private static void applyChangesToDTO(BaseDTO dto, Map<String, Object> changes) {
        changes.forEach(dto::setProperty);
    }

    public Map<Long, Map<String, Object>> collectChanges() {
        Map<Long, Map<String, Object>> allChanges = new HashMap<>();
        trackers.forEach((id, tracker) -> {
            Map<String, Object> changes = tracker.getChanges();
            if (!changes.isEmpty()) {
                allChanges.put(id, changes);
            }
        });
        return allChanges;
    }

    public void clearTracker(Long id) {
        trackers.remove(id);
    }

    public void clear() {
        trackers.clear();
    }

    public void populateTrackers(Set<Instance> instances) {
        //populate both trackers with existing instances
        //create DTO and track it as welld
        instances.forEach(instance -> {
            if (!exists(instance.getId())) {
                track(instance.getId(), instance);
                addDto(instance.getId(), new BaseDTO(instance.getId(), instance.getName()));
            }
        });
    }
}
