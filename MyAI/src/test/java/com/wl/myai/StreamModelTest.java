package com.wl.myai;

import com.wl.myai.service.StreamAssistant;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class StreamModelTest {

    @Resource
    private StreamAssistant streamAssistant;

    @Test
    public void testStreamModel() throws InterruptedException {
        Flux<String> responseFlux = streamAssistant.chat("1+2等于几，322233222345的平方根是多少？");

        CountDownLatch latch = new CountDownLatch(1);

        responseFlux
                .doOnSubscribe(sub -> System.out.println("Subscribed to flux"))
                .subscribe(
                        chunk -> System.out.println("Received: " + chunk),
                        throwable -> {
                            System.err.println("Error occurred: " + throwable.getMessage());
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("Completed");
                            latch.countDown();
                        }
                );

        latch.await();
    }
}
