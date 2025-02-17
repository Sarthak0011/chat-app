package com.sarthak.chat_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class MessageEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversationEntity;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Column(nullable = false)
    private String content;
}
