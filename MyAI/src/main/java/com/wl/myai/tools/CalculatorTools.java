package com.wl.myai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(name = "sum", value = "sum(a,b)计算a和b的和")
    double sum(
            @ToolMemoryId int memoryId,
            @P(value = "加数1",required = true) double a,
            @P(value = "加数2",required = true)double b) {
        System.out.println("调用加法运算,memoryId: " +   memoryId);
        return a + b;
    }

    @Tool(name = "squareRoot", value = "squareRoot(x)计算x的平方根")
    double squareRoot(
            @ToolMemoryId int memoryId,
            @P(value = "平方根数",required = true) double x) {
        System.out.println("调用平方根运算,memoryId: " +   memoryId);
        return Math.sqrt(x);
    }

}
