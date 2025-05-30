package com.wl.myai.service;


import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory")
public interface Assistant {

//    @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。 {{it}}") //{{it}}表示这里唯一的参数的占位符
//    @UserMessage(fromResource = "userMessage.txt")
//    String chat(@V("userMessage") String userMessage);

    @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。 {{userMessage}}") //当我们指定了 @V的value值时，占位符就不能随便写了，需要和指定的值一致
//    @UserMessage(fromResource = "userMessage.txt")
    String chat(@V("userMessage") String userMessage);

}
