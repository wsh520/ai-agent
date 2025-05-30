package com.wl.myai;

import com.wl.myai.service.Assistant;
import com.wl.myai.service.SeparateChatAssistant;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

    // 如果要显示今天的日期，我们需要在提示词中添加当前日期的占位符{{current_date}}

    @Resource
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testSystemMessage() {
        String answer1 = separateChatAssistant.chat(6, "你好呀，我是wl,请夸夸我");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(6, "今天是几号");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(6, "我是谁呀？");
        System.out.println(answer3);
    }

    @Test
    public void testSystemMessageDate() {
        String answer1 = separateChatAssistant.chat(4, "今天是几号");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(4, "我是谁呀？");
        System.out.println(answer2);
    }

    @Resource
    private Assistant assistant;

    @Test
    public void testUserMessage() {
        String answer1 = assistant.chat("今天是几号");
        System.out.println(answer1);
    }

    @Test
    public void testMutiV() {
        String answer1 = separateChatAssistant.chat2(7, "今天是几号");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat2(7, "我是谁呀？");
        System.out.println(answer2);
    }

    @Test
    public void testMutiV2() {
        String answer = separateChatAssistant.chat3(10, "我是谁，我多大了", "wl", 18);
        System.out.println(answer);
    }
}