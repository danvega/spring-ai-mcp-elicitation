# Spring AI MCP Elicitation Demo

A demonstration of **MCP (Model Context Protocol) elicitation** using Spring AI. Elicitation allows an MCP tool to interactively 
request additional information from the user at runtime when parameters are missing.

## What It Demonstrates

When an AI tool is invoked without all required information, elicitation enables the server to pause execution, request 
the missing data from the client, and resume once the user provides it.

## Project Structure

```
├── server/   # MCP server exposing the coffee ordering tool
└── client/   # MCP client with elicitation handler + chat endpoint
```

### Server
- Exposes an `order-coffee` MCP tool that requires size, type, and milk preference
- When parameters are missing, triggers elicitation with a `CoffeeOrder` schema
- Uses `@McpTool` annotations from Spring AI Community MCP

### Client
- Connects to the MCP server via Streamable HTTP
- Handles elicitation requests via console input using `@McpElicitation`
- Provides a `/` endpoint that simulates a customer ordering coffee

## Running

1. Set your OpenAI API key:
   ```bash
   export OPENAI_API_KEY=your-key
   ```

2. Start the server (port 8081):
   ```bash
   cd server && ../mvnw spring-boot:run
   ```

3. Start the client (port 8080):
   ```bash
   cd client && ../mvnw spring-boot:run
   ```

4. Visit `http://localhost:8080/` - the AI will invoke the coffee tool, triggering elicitation prompts in the client console.

## Requirements

- Java 25
- Spring Boot 3.5.9
- Spring AI 1.1.2