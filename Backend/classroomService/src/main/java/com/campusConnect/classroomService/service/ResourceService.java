package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.ResourceDTO;
import com.campusConnect.classroomService.entity.enums.Type;

import java.util.List;

public interface ResourceService {

    // CRUD Operations
    ResourceDTO createResource(ResourceDTO resourceDTO);
    ResourceDTO getResourceById(Long id);
    List<ResourceDTO> getAllResources();
    ResourceDTO updateResource(Long id, ResourceDTO resourceDTO);
    void deleteResource(Long id);

    // Query Operations
    List<ResourceDTO> getResourcesByUploadedBy(Long uploadedBy);
    List<ResourceDTO> getResourcesByType(Type type);
    List<ResourceDTO> getResourcesBySubject(String subject);
    List<ResourceDTO> getResourcesBySemester(Integer semester);
    List<ResourceDTO> getResourcesBySubjectAndSemester(String subject, Integer semester);
    List<ResourceDTO> getResourcesByTypeAndSubject(Type type, String subject);
    List<ResourceDTO> getResourcesByTypeAndSemester(Type type, Integer semester);
    List<ResourceDTO> getResourcesByUploadedByAndType(Long uploadedBy, Type type);
    List<ResourceDTO> getResourcesByUploadedByAndSubject(Long uploadedBy, String subject);
    List<ResourceDTO> getResourcesByUploadedByAndSemester(Long uploadedBy, Integer semester);
    List<ResourceDTO> searchResourcesByTitle(String title);
    List<ResourceDTO> searchResourcesBySubject(String subject);
    List<ResourceDTO> getResourcesByTypeAndSubjectAndSemester(Type type, String subject, Integer semester);
    List<ResourceDTO> getResourcesByUploadedByAndSubjectAndSemester(Long uploadedBy, String subject, Integer semester);
    List<ResourceDTO> getResourcesByTypes(List<Type> types);
    List<ResourceDTO> getResourcesBySubjects(List<String> subjects);
    List<ResourceDTO> getResourcesBySemesters(List<Integer> semesters);
    List<ResourceDTO> searchResourcesByTitleOrSubject(String searchTerm);

    // Statistics and Analytics
    Long countResourcesByUploadedBy(Long uploadedBy);
    Long countResourcesByType(Type type);
    Long countResourcesBySubject(String subject);
    Long countResourcesBySemester(Integer semester);
    List<String> getDistinctSubjects();
    List<Integer> getDistinctSemesters();

    // Utility Methods
    List<ResourceDTO> getResourcesForStudent(Integer semester, List<String> subjects);
    List<ResourceDTO> getRecentResourcesByUser(Long uploadedBy, int limit);
}
