package com.wl.myai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "qwenStreamingChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProviderXiaozhi",
        tools = "appointmentTools",
//        contentRetriever = "contentRetriever" ,// 使用内存存储向量数据
        contentRetriever = "contentRetrieverXiaozhiPinecone" // 使用pinecone存储向量数据
) // 添加内容检索工具
public interface XiaozhiAgent {

    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);

}
