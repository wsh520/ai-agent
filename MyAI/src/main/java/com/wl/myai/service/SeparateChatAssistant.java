package com.wl.myai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider")
public interface SeparateChatAssistant {

    // 只有一个参数的时候，@UserMessage注解可以省略
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
