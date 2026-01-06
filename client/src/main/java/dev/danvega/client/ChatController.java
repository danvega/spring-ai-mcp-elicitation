package dev.danvega.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider tools) {

        // list what tools are available: just using this for debugging
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool Callback found: {}", t.getToolDefinition());
        });

        this.chatClient = builder
                .defaultToolCallbacks(tools)
                .build();
    }


    @GetMapping("/")
    public String chat() {
        return chatClient.prompt()
                .system("If a customer asks me to order coffee use the order_coffee tool that is available to you.")
                .user("I would like to order a coffee")
                .call()
                .content();
    }

    @GetMapping("/dvaas")
    public String dvaas() {
        return chatClient.prompt()
                .user("What are Dan's latest 3 videos")
                .call()
                .content();
    }

}
