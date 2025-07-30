package com.klack.klack.chats.repository;

import com.klack.klack.chats.entity.ChatMessage;
import com.klack.klack.chats.enums.MessageType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByCompanyIdAndChannelId(String companyId, String channelId);
    List<ChatMessage> findByCompanyIdAndReceiverId(String companyId, String receiverId);
    List<ChatMessage> findByCompanyIdAndType(String companyId, MessageType type);
}
