package com.wl.myai;

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
        String answer1 = separateChatAssistant.chat(5, "你好呀，我是wl,请夸夸我");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(5, "今天是几号");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(5, "我是谁呀？");
        System.out.println(answer3);
    }

    @Test
    public void testSystemMessageDate() {
        String answer1 = separateChatAssistant.chat(4, "今天是几号");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(4, "我是谁呀？");
        System.out.println(answer2);
    }
}