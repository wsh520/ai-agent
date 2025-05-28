package com.wl.myai;

import com.wl.myai.service.Assistant;
import com.wl.myai.service.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ChatMemoryTest {

    @Resource
    private Assistant assistant;

    @Test
    public void testChatMemory() {
        String result1 = assistant.chat("你好,我是wl");
        System.out.println(result1);
        String result2 = assistant.chat("请问我是谁?");
        System.out.println(result2);
    }


    @Resource
    private QwenChatModel qwenChatModel;

    /**
     * 手动传入聊天记录，查看模型记忆
     */
    @Test
    public void testChatMemory2() {
        //第一轮对话
        UserMessage userMessage1 = UserMessage.userMessage("我是wl");
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage1.text());
        //第二轮对话
        UserMessage userMessage2 = UserMessage.userMessage("你知道我是谁吗");
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1,
                aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage2.text());
    }

    /**
     * 使用chatMemory2
     */
    @Test
    public void testChatMemory3() {
        // 记录十次会话的ChatMemory
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.withMaxMessages(10);
        Assistant memoryAssistant =
                AiServices.builder(Assistant.class)
                        .chatLanguageModel(qwenChatModel)
                        .chatMemory(messageWindowChatMemory)
                        .build();
        String result1 = memoryAssistant.chat("你好,我是wl");
        System.out.println(result1);
        String result2 = memoryAssistant.chat("你知道我是谁吗");
        System.out.println(result2);
    }


    /**
     * 使用@AiService 注解配置实现记忆
     */
    @Test
    public void testChatMemory4() {

        String result1 = assistant.chat("你好,我是wl");
        System.out.println(result1);
        String result2 = assistant.chat("你知道我是谁吗");
        System.out.println(result2);
    }

    @Resource
    private SeparateChatAssistant separateChatAssistant;

    /**
     * 测试消息隔离
     */
    @Test
    public void testChatMemoryProvider() {
        String result1 = separateChatAssistant.chat(1, "你好,我是wl");
        System.out.println(result1);
        String result2 = separateChatAssistant.chat(1, "我是谁？");
        System.out.println(result2);
        String result3 = separateChatAssistant.chat(2, "我是谁？");
        System.out.println(result3);

    }


}
