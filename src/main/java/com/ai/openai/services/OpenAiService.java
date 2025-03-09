package com.ai.openai.services;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    private final OpenAiChatModel openAiChatModel;

    public OpenAiService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        this.openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder().apiKey(apiKey).build())
                .build();
    }

    public String generateResponse(String prompt) {
        return openAiChatModel.call(prompt);
    }
}
