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
    private final ToolCallbackProvider tools;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider tools) {
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool Callback found: {}", t.getToolDefinition());
        });

        this.tools = tools;

        this.chatClient = builder
                .build();
    }


    @GetMapping("/")
    public String chat() {
        return chatClient.prompt()
                .system("You are a barista at Dan's Coffee Shop. Use the order_coffee tool to process all customer orders.")
                .toolCallbacks(tools.getToolCallbacks())
                .user("I would like to order a coffee")
                .call()
                .content();
    }

}
