package com.campusConnect.chatService.controller;

import com.campusConnect.chatService.dto.AttachmentDTO;
import com.campusConnect.chatService.service.AttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<AttachmentDTO> createAttachment(@Valid @RequestBody AttachmentDTO attachmentDTO) {
        AttachmentDTO createdAttachment = attachmentService.createAttachment(attachmentDTO);
        return new ResponseEntity<>(createdAttachment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDTO> getAttachmentById(@PathVariable Long id) {
        return ResponseEntity.ok(attachmentService.getAttachmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments() {
        return ResponseEntity.ok(attachmentService.getAllAttachments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttachmentDTO> updateAttachment(@PathVariable Long id,
                                                          @Valid @RequestBody AttachmentDTO attachmentDTO) {
        return ResponseEntity.ok(attachmentService.updateAttachment(id, attachmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}
