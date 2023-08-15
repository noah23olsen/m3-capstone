package com.techelevator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.techelevator.model.ChatbotResponse;
import com.techelevator.dao.ChatbotResponseDao;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/")
@CrossOrigin
public class ChatbotResponseController {

    private ChatbotResponseDao chatbotResponseDao;

    public ChatbotResponseController(ChatbotResponseDao chatbotResponseDao) {
        this.chatbotResponseDao = chatbotResponseDao;
    }

    @GetMapping(path = "{userInput}/{subjectContext}/{topicContext}")
    public String[] getChatbotResponse(
            @PathVariable String userInput,
            @PathVariable String subjectContext,
            @PathVariable String topicContext
    ) {
        ChatbotResponse chatbotResponse = new ChatbotResponse();

        chatbotResponse.setUserInput(userInput);
        chatbotResponse.setSubjectContext(subjectContext);
        chatbotResponse.setTopicContext(topicContext);

        // Retrieve the response directly as a string
        String[] chatbotReply = chatbotResponseDao.getResponseFromInput(chatbotResponse);

        // Return an array containing the response string
        return chatbotReply;
    }

    @GetMapping("/{contextString}")
    public ResponseEntity<String> getChatbotSuggestions(@PathVariable String contextString) {
        try {
            ChatbotResponse chatbotResponse = new ChatbotResponse();
            chatbotResponse.setVariableContext(contextString);

            String chatbotReply = chatbotResponseDao.getSuggestions(chatbotResponse);
            return ResponseEntity.ok(chatbotReply);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

}
