package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.ResourceDTO;
import com.campusConnect.classroomService.entity.Resource;
import com.campusConnect.classroomService.entity.enums.Type;
import com.campusConnect.classroomService.repository.ResourceRepository;
import com.campusConnect.classroomService.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        log.info("Creating new resource: {} uploaded by user: {}",
                resourceDTO.getTitle(), resourceDTO.getUploadedBy());

        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        Resource savedResource = resourceRepository.save(resource);

        log.info("Resource created successfully with ID: {}", savedResource.getId());
        return modelMapper.map(savedResource, ResourceDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceDTO getResourceById(Long id) {
        log.info("Fetching resource with ID: {}", id);

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with ID: " + id));

        return modelMapper.map(resource, ResourceDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getAllResources() {
        log.info("Fetching all resources");

        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        log.info("Updating resource with ID: {}", id);

        Resource existingResource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with ID: " + id));

        // Update fields
        existingResource.setUploadedBy(resourceDTO.getUploadedBy());
        existingResource.setType(resourceDTO.getType());
        existingResource.setTitle(resourceDTO.getTitle());
        existingResource.setFileUrl(resourceDTO.getFileUrl());
        existingResource.setSubject(resourceDTO.getSubject());
        existingResource.setSemester(resourceDTO.getSemester());

        Resource updatedResource = resourceRepository.save(existingResource);
        log.info("Resource updated successfully with ID: {}", updatedResource.getId());

        return modelMapper.map(updatedResource, ResourceDTO.class);
    }

    @Override
    public void deleteResource(Long id) {
        log.info("Deleting resource with ID: {}", id);

        if (!resourceRepository.existsById(id)) {
            throw new RuntimeException("Resource not found with ID: " + id);
        }

        resourceRepository.deleteById(id);
        log.info("Resource deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByUploadedBy(Long uploadedBy) {
        log.info("Fetching resources uploaded by user: {}", uploadedBy);

        List<Resource> resources = resourceRepository.findByUploadedBy(uploadedBy);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByType(Type type) {
        log.info("Fetching resources by type: {}", type);

        List<Resource> resources = resourceRepository.findByType(type);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesBySubject(String subject) {
        log.info("Fetching resources for subject: {}", subject);

        List<Resource> resources = resourceRepository.findBySubject(subject);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesBySemester(Integer semester) {
        log.info("Fetching resources for semester: {}", semester);

        List<Resource> resources = resourceRepository.findBySemester(semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesBySubjectAndSemester(String subject, Integer semester) {
        log.info("Fetching resources for subject: {} and semester: {}", subject, semester);

        List<Resource> resources = resourceRepository.findBySubjectAndSemester(subject, semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByTypeAndSubject(Type type, String subject) {
        log.info("Fetching resources with type: {} for subject: {}", type, subject);

        List<Resource> resources = resourceRepository.findByTypeAndSubject(type, subject);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByTypeAndSemester(Type type, Integer semester) {
        log.info("Fetching resources with type: {} for semester: {}", type, semester);

        List<Resource> resources = resourceRepository.findByTypeAndSemester(type, semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByUploadedByAndType(Long uploadedBy, Type type) {
        log.info("Fetching resources uploaded by user: {} with type: {}", uploadedBy, type);

        List<Resource> resources = resourceRepository.findByUploadedByAndType(uploadedBy, type);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByUploadedByAndSubject(Long uploadedBy, String subject) {
        log.info("Fetching resources uploaded by user: {} for subject: {}", uploadedBy, subject);

        List<Resource> resources = resourceRepository.findByUploadedByAndSubject(uploadedBy, subject);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByUploadedByAndSemester(Long uploadedBy, Integer semester) {
        log.info("Fetching resources uploaded by user: {} for semester: {}", uploadedBy, semester);

        List<Resource> resources = resourceRepository.findByUploadedByAndSemester(uploadedBy, semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> searchResourcesByTitle(String title) {
        log.info("Searching resources by title: {}", title);

        List<Resource> resources = resourceRepository.findByTitleContainingIgnoreCase(title);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> searchResourcesBySubject(String subject) {
        log.info("Searching resources by subject: {}", subject);

        List<Resource> resources = resourceRepository.findBySubjectContainingIgnoreCase(subject);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByTypeAndSubjectAndSemester(Type type, String subject, Integer semester) {
        log.info("Fetching resources with type: {} for subject: {} and semester: {}", type, subject, semester);

        List<Resource> resources = resourceRepository.findByTypeAndSubjectAndSemester(type, subject, semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByUploadedByAndSubjectAndSemester(Long uploadedBy, String subject, Integer semester) {
        log.info("Fetching resources uploaded by user: {} for subject: {} and semester: {}", uploadedBy, subject, semester);

        List<Resource> resources = resourceRepository.findByUploadedByAndSubjectAndSemester(uploadedBy, subject, semester);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByTypes(List<Type> types) {
        log.info("Fetching resources by multiple types: {}", types);

        List<Resource> resources = resourceRepository.findResourcesByTypes(types);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesBySubjects(List<String> subjects) {
        log.info("Fetching resources by multiple subjects: {}", subjects);

        List<Resource> resources = resourceRepository.findResourcesBySubjects(subjects);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesBySemesters(List<Integer> semesters) {
        log.info("Fetching resources by multiple semesters: {}", semesters);

        List<Resource> resources = resourceRepository.findResourcesBySemesters(semesters);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> searchResourcesByTitleOrSubject(String searchTerm) {
        log.info("Searching resources by title or subject: {}", searchTerm);

        List<Resource> resources = resourceRepository.searchResourcesByTitleOrSubject(searchTerm);
        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countResourcesByUploadedBy(Long uploadedBy) {
        return resourceRepository.countResourcesByUploadedBy(uploadedBy);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countResourcesByType(Type type) {
        return resourceRepository.countResourcesByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countResourcesBySubject(String subject) {
        return resourceRepository.countResourcesBySubject(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countResourcesBySemester(Integer semester) {
        return resourceRepository.countResourcesBySemester(semester);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctSubjects() {
        return resourceRepository.findDistinctSubjects();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getDistinctSemesters() {
        return resourceRepository.findDistinctSemesters();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesForStudent(Integer semester, List<String> subjects) {
        log.info("Fetching resources for student in semester: {} with subjects: {}", semester, subjects);

        List<Resource> resources;
        if (subjects != null && !subjects.isEmpty()) {
            resources = resourceRepository.findResourcesBySubjects(subjects).stream()
                    .filter(resource -> resource.getSemester().equals(semester))
                    .collect(Collectors.toList());
        } else {
            resources = resourceRepository.findBySemester(semester);
        }

        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDTO> getRecentResourcesByUser(Long uploadedBy, int limit) {
        log.info("Fetching recent {} resources uploaded by user: {}", limit, uploadedBy);

        // Note: This assumes resources are ordered by ID (which usually corresponds to creation time)
        // In a real application, you'd have a createdDate field
        List<Resource> resources = resourceRepository.findByUploadedBy(uploadedBy).stream()
                .sorted((r1, r2) -> r2.getId().compareTo(r1.getId()))
                .limit(limit)
                .collect(Collectors.toList());

        return resources.stream()
                .map(resource -> modelMapper.map(resource, ResourceDTO.class))
                .collect(Collectors.toList());
    }
}
