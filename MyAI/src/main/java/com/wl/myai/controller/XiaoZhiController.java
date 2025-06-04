package com.wl.myai.controller;

import com.wl.myai.param.ChatForm;
import com.wl.myai.service.XiaozhiAgent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaoZhiController {

    @Resource
    private XiaozhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {

        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}