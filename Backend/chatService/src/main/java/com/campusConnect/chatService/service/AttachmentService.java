package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.AttachmentDTO;

import java.util.List;

public interface AttachmentService {

    AttachmentDTO createAttachment(AttachmentDTO attachmentDTO);

    AttachmentDTO getAttachmentById(String id);

    List<AttachmentDTO> getAllAttachments();

    AttachmentDTO updateAttachment(String id, AttachmentDTO attachmentDTO);

    void deleteAttachment(String id);
}
