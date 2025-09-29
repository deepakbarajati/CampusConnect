package com.campusConnect.chatService.util;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

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
     * Advanced map that resolves nested entity IDs.
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

                        // Load referenced entity using JPA EntityManager
                        Object refEntity = entityManager.find(entityField.getType(), idValue);
                        if (refEntity != null) {
                            entityField.set(target, refEntity);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return target;
    }
}
