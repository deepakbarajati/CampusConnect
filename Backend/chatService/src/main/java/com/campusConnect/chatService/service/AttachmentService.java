package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.AttachmentDTO;

import java.util.List;

public interface AttachmentService {

    AttachmentDTO createAttachment(AttachmentDTO attachmentDTO);

    AttachmentDTO getAttachmentById(Long id);

    List<AttachmentDTO> getAllAttachments();

    AttachmentDTO updateAttachment(Long id, AttachmentDTO attachmentDTO);

    void deleteAttachment(Long id);
}
