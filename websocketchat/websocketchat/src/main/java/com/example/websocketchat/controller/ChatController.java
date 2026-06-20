package com.example.websocketchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.websocketchat.dto.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/messages")
	public ChatMessage sendMessage(
	        @Payload ChatMessage message) {


	    System.out.println(
	        "Broadcasting message: "
	        + message.getContent()
	    );


	    return message;

	}
}
