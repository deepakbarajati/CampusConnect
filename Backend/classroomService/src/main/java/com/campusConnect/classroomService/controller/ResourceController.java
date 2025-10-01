package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.ResourceDTO;
import com.campusConnect.classroomService.entity.enums.Type;
import com.campusConnect.classroomService.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ResourceController {

    private final ResourceService resourceService;

    // Create resource
    @PostMapping
    public ResponseEntity<ResourceDTO> createResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        log.info("Request to create resource: {} uploaded by user: {}",
                resourceDTO.getTitle(), resourceDTO.getUploadedBy());

        try {
            ResourceDTO createdResource = resourceService.createResource(resourceDTO);
            return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating resource: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all resources
    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAllResources() {
        log.info("Request to get all resources");

        try {
            List<ResourceDTO> resources = resourceService.getAllResources();
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all resources: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable Long id) {
        log.info("Request to get resource with ID: {}", id);

        try {
            ResourceDTO resource = resourceService.getResourceById(id);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resource with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update resource
    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> updateResource(@PathVariable Long id,
                                                      @Valid @RequestBody ResourceDTO resourceDTO) {
        log.info("Request to update resource with ID: {}", id);

        try {
            ResourceDTO updatedResource = resourceService.updateResource(id, resourceDTO);
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating resource with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.info("Request to delete resource with ID: {}", id);

        try {
            resourceService.deleteResource(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting resource with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get resources by uploaded by user
    @GetMapping("/uploaded-by/{uploadedBy}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUploadedBy(@PathVariable Long uploadedBy) {
        log.info("Request to get resources uploaded by user: {}", uploadedBy);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByUploadedBy(uploadedBy);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by uploaded by {}: {}", uploadedBy, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByType(@PathVariable Type type) {
        log.info("Request to get resources by type: {}", type);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByType(type);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by type {}: {}", type, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<ResourceDTO>> getResourcesBySubject(@PathVariable String subject) {
        log.info("Request to get resources for subject: {}", subject);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesBySubject(subject);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by subject {}: {}", subject, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by semester
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesBySemester(@PathVariable Integer semester) {
        log.info("Request to get resources for semester: {}", semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesBySemester(semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by semester {}: {}", semester, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by subject and semester
    @GetMapping("/subject/{subject}/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesBySubjectAndSemester(
            @PathVariable String subject,
            @PathVariable Integer semester) {
        log.info("Request to get resources for subject: {} and semester: {}", subject, semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesBySubjectAndSemester(subject, semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by subject and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by type and subject
    @GetMapping("/type/{type}/subject/{subject}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByTypeAndSubject(
            @PathVariable Type type,
            @PathVariable String subject) {
        log.info("Request to get resources with type: {} for subject: {}", type, subject);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByTypeAndSubject(type, subject);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by type and subject: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by type and semester
    @GetMapping("/type/{type}/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByTypeAndSemester(
            @PathVariable Type type,
            @PathVariable Integer semester) {
        log.info("Request to get resources with type: {} for semester: {}", type, semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByTypeAndSemester(type, semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by type and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by uploaded by and type
    @GetMapping("/uploaded-by/{uploadedBy}/type/{type}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUploadedByAndType(
            @PathVariable Long uploadedBy,
            @PathVariable Type type) {
        log.info("Request to get resources uploaded by user: {} with type: {}", uploadedBy, type);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByUploadedByAndType(uploadedBy, type);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by uploaded by and type: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by uploaded by and subject
    @GetMapping("/uploaded-by/{uploadedBy}/subject/{subject}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUploadedByAndSubject(
            @PathVariable Long uploadedBy,
            @PathVariable String subject) {
        log.info("Request to get resources uploaded by user: {} for subject: {}", uploadedBy, subject);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByUploadedByAndSubject(uploadedBy, subject);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by uploaded by and subject: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by uploaded by and semester
    @GetMapping("/uploaded-by/{uploadedBy}/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUploadedByAndSemester(
            @PathVariable Long uploadedBy,
            @PathVariable Integer semester) {
        log.info("Request to get resources uploaded by user: {} for semester: {}", uploadedBy, semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByUploadedByAndSemester(uploadedBy, semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by uploaded by and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search resources by title
    @GetMapping("/search/title")
    public ResponseEntity<List<ResourceDTO>> searchResourcesByTitle(@RequestParam String title) {
        log.info("Request to search resources by title: {}", title);

        try {
            List<ResourceDTO> resources = resourceService.searchResourcesByTitle(title);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching resources by title {}: {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search resources by subject
    @GetMapping("/search/subject")
    public ResponseEntity<List<ResourceDTO>> searchResourcesBySubject(@RequestParam String subject) {
        log.info("Request to search resources by subject: {}", subject);

        try {
            List<ResourceDTO> resources = resourceService.searchResourcesBySubject(subject);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching resources by subject {}: {}", subject, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by type, subject and semester
    @GetMapping("/type/{type}/subject/{subject}/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByTypeAndSubjectAndSemester(
            @PathVariable Type type,
            @PathVariable String subject,
            @PathVariable Integer semester) {
        log.info("Request to get resources with type: {} for subject: {} and semester: {}", type, subject, semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByTypeAndSubjectAndSemester(type, subject, semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by type, subject and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by uploaded by, subject and semester
    @GetMapping("/uploaded-by/{uploadedBy}/subject/{subject}/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUploadedByAndSubjectAndSemester(
            @PathVariable Long uploadedBy,
            @PathVariable String subject,
            @PathVariable Integer semester) {
        log.info("Request to get resources uploaded by user: {} for subject: {} and semester: {}", uploadedBy, subject, semester);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByUploadedByAndSubjectAndSemester(uploadedBy, subject, semester);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by uploaded by, subject and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by multiple types
    @GetMapping("/types")
    public ResponseEntity<List<ResourceDTO>> getResourcesByTypes(@RequestParam List<Type> types) {
        log.info("Request to get resources by multiple types: {}", types);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesByTypes(types);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by multiple types: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by multiple subjects
    @GetMapping("/subjects")
    public ResponseEntity<List<ResourceDTO>> getResourcesBySubjects(@RequestParam List<String> subjects) {
        log.info("Request to get resources by multiple subjects: {}", subjects);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesBySubjects(subjects);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by multiple subjects: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by multiple semesters
    @GetMapping("/semesters")
    public ResponseEntity<List<ResourceDTO>> getResourcesBySemesters(@RequestParam List<Integer> semesters) {
        log.info("Request to get resources by multiple semesters: {}", semesters);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesBySemesters(semesters);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources by multiple semesters: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search resources by title or subject
    @GetMapping("/search")
    public ResponseEntity<List<ResourceDTO>> searchResourcesByTitleOrSubject(@RequestParam String searchTerm) {
        log.info("Request to search resources by title or subject: {}", searchTerm);

        try {
            List<ResourceDTO> resources = resourceService.searchResourcesByTitleOrSubject(searchTerm);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching resources by title or subject {}: {}", searchTerm, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count resources by uploaded by user
    @GetMapping("/count/uploaded-by/{uploadedBy}")
    public ResponseEntity<Long> countResourcesByUploadedBy(@PathVariable Long uploadedBy) {
        log.info("Request to count resources uploaded by user: {}", uploadedBy);

        try {
            Long count = resourceService.countResourcesByUploadedBy(uploadedBy);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting resources by uploaded by: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count resources by type
    @GetMapping("/count/type/{type}")
    public ResponseEntity<Long> countResourcesByType(@PathVariable Type type) {
        log.info("Request to count resources by type: {}", type);

        try {
            Long count = resourceService.countResourcesByType(type);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting resources by type: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count resources by subject
    @GetMapping("/count/subject/{subject}")
    public ResponseEntity<Long> countResourcesBySubject(@PathVariable String subject) {
        log.info("Request to count resources by subject: {}", subject);

        try {
            Long count = resourceService.countResourcesBySubject(subject);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting resources by subject: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count resources by semester
    @GetMapping("/count/semester/{semester}")
    public ResponseEntity<Long> countResourcesBySemester(@PathVariable Integer semester) {
        log.info("Request to count resources by semester: {}", semester);

        try {
            Long count = resourceService.countResourcesBySemester(semester);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting resources by semester: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get distinct subjects
    @GetMapping("/distinct/subjects")
    public ResponseEntity<List<String>> getDistinctSubjects() {
        log.info("Request to get distinct subjects");

        try {
            List<String> subjects = resourceService.getDistinctSubjects();
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching distinct subjects: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get distinct semesters
    @GetMapping("/distinct/semesters")
    public ResponseEntity<List<Integer>> getDistinctSemesters() {
        log.info("Request to get distinct semesters");

        try {
            List<Integer> semesters = resourceService.getDistinctSemesters();
            return new ResponseEntity<>(semesters, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching distinct semesters: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources for student
    @GetMapping("/student/semester/{semester}")
    public ResponseEntity<List<ResourceDTO>> getResourcesForStudent(
            @PathVariable Integer semester,
            @RequestParam(required = false) List<String> subjects) {
        log.info("Request to get resources for student in semester: {} with subjects: {}", semester, subjects);

        try {
            List<ResourceDTO> resources = resourceService.getResourcesForStudent(semester, subjects);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching resources for student: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get recent resources by user
    @GetMapping("/uploaded-by/{uploadedBy}/recent")
    public ResponseEntity<List<ResourceDTO>> getRecentResourcesByUser(
            @PathVariable Long uploadedBy,
            @RequestParam(defaultValue = "10") int limit) {
        log.info("Request to get recent {} resources uploaded by user: {}", limit, uploadedBy);

        try {
            List<ResourceDTO> resources = resourceService.getRecentResourcesByUser(uploadedBy, limit);
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching recent resources by user: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
