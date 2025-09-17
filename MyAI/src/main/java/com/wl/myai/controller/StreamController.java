package com.wl.myai.controller;

import com.wl.myai.service.StreamAssistant;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
public class StreamController {

    @Resource
    private StreamAssistant streamAssistant;

    @Operation(summary = "对话流(原始模型片段)")
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam String chatMessage) {
        // 直接返回模型流式输出片段（千问已按 token/片段拆好）
        return streamAssistant.chat(chatMessage);
    }
}
