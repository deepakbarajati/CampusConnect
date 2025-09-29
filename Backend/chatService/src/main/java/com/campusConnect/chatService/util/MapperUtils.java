package com.campusConnect.chatService.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    /**
     * Basic map (no nested handling)
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Advanced map that resolves nested entity IDs using MongoDB.
     * Example:
     *  - DTO has messageId
     *  - Entity has Message message
     */
    public <S, T> T mapWithRelations(S source, Class<T> targetClass) {
        T target = modelMapper.map(source, targetClass);

        // Reflectively set nested entities if dto has {entityName}Id field
        for (var field : source.getClass().getDeclaredFields()) {
            if (field.getName().endsWith("Id")) {
                field.setAccessible(true);
                try {
                    Object idValue = field.get(source);
                    if (idValue != null) {
                        String entityName = field.getName().replace("Id", "");
                        var entityField = targetClass.getDeclaredField(entityName);
                        entityField.setAccessible(true);

                        // Load referenced entity using MongoDB
                        Object refEntity = findEntityById(entityField.getType(), idValue);
                        if (refEntity != null) {
                            entityField.set(target, refEntity);
                        }
                    }
                } catch (Exception ignored) {
                    // Handle exceptions silently as in original code
                }
            }
        }
        return target;
    }

    /**
     * Helper method to find entity by ID in MongoDB
     */
    private Object findEntityById(Class<?> entityType, Object id) {
        try {
            // Convert ID to String if needed (MongoDB typically uses String IDs)
            String stringId = id.toString();

            // Use MongoTemplate to find by ID
            return mongoTemplate.findById(stringId, entityType);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Alternative method if you prefer using specific field name for ID lookup
     */
    private Object findEntityByField(Class<?> entityType, String fieldName, Object value) {
        try {
            Query query = new Query(Criteria.where(fieldName).is(value));
            return mongoTemplate.findOne(query, entityType);
        } catch (Exception e) {
            return null;
        }
    }
}
