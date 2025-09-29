package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.document.Attachment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends MongoRepository<Attachment, String> {
}
