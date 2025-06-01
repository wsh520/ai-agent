package com.wl.myai.controller;

import com.wl.myai.service.StreamAssistant;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
public class StreamController {

    @Resource
    private StreamAssistant streamAssistant;

    @Operation(summary = "对话")
    @GetMapping(value = "/chat", produces = "text/stream;charset=utf-8") // 设置响应类型为流式文本，并指定字符集为UTF-8
    public Flux<String> chat() {
        return streamAssistant.chat("1+2等于几，322233222345的平方根是多少？");
    }
}
