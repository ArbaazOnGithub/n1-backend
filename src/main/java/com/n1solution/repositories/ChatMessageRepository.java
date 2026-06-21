package com.n1solution.repositories;

import com.n1solution.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE (m.senderEmail = :email1 AND m.receiverEmail = :email2) OR (m.senderEmail = :email2 AND m.receiverEmail = :email1) ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(@Param("email1") String email1, @Param("email2") String email2);

    @Query("SELECT DISTINCT CASE WHEN m.senderEmail = :adminEmail THEN m.receiverEmail ELSE m.senderEmail END FROM ChatMessage m WHERE m.senderEmail = :adminEmail OR m.receiverEmail = :adminEmail")
    List<String> findActiveChatPartners(@Param("adminEmail") String adminEmail);
}
