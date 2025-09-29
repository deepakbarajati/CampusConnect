package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.dto.AttachmentDTO;
import com.campusConnect.chatService.document.Attachment;
import com.campusConnect.chatService.document.Message;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.AttachmentRepository;
import com.campusConnect.chatService.repository.MessageRepository;
import com.campusConnect.chatService.service.AttachmentService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final MessageRepository messageRepository;
    private final MapperUtils mapperUtils;

    @Override
    public AttachmentDTO createAttachment(AttachmentDTO attachmentDTO) {
        Message message = messageRepository.findById(attachmentDTO.getMessageId())
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + attachmentDTO.getMessageId()));

        Attachment attachment = mapperUtils.map(attachmentDTO, Attachment.class);

        Attachment savedAttachment = attachmentRepository.save(attachment);
        return mapperUtils.map(savedAttachment, AttachmentDTO.class);
    }

    @Override
    public AttachmentDTO getAttachmentById(String id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id: " + id));

        return mapperUtils.map(attachment, AttachmentDTO.class);
    }

    @Override
    public List<AttachmentDTO> getAllAttachments() {
        List<Attachment> attachments = attachmentRepository.findAll();
        return mapperUtils.mapList(attachments, AttachmentDTO.class);
    }

    @Override
    public AttachmentDTO updateAttachment(String id, AttachmentDTO attachmentDTO) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id: " + id));

        // Update only fields present in DTO
        if (attachmentDTO.getFileUrl() != null) attachment.setFileUrl(attachmentDTO.getFileUrl());
        if (attachmentDTO.getType() != null) attachment.setType(attachmentDTO.getType());

        Attachment updated = attachmentRepository.save(attachment);
        return mapperUtils.map(updated, AttachmentDTO.class);
    }

    @Override
    public void deleteAttachment(String id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id: " + id));
        attachmentRepository.delete(attachment);
    }
}
