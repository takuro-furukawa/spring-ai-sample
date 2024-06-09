package com.example.springaisample;

import com.example.springaisample.domain.ChatInput;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/")
public class OllamaChatController {

    @Value("classpath:/template.txt")
    private Resource template;
    @Value("${load.file.dir.path}")
    private String loadFileDirectoryPath;

    private final ChatClient chatClient;
    private final FileLoadService fileLoadService;
    private final VectorStore vectorStore;

    public OllamaChatController(ChatModel chatModel, FileLoadService fileLoadService, VectorStore vectorStore) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.fileLoadService = fileLoadService;
        this.vectorStore = vectorStore;
    }

    /**
     * chat with LLM without RAG data
     * @param input question
     * @return answer
     */
    @PostMapping("/ai/generate")
    public Flux<String> generateStream(@RequestBody ChatInput input) {
        return chatClient.prompt().user(input.getMessage()).stream().content();
    }

    /**
     * chat with LLM with RAG data
     * @param input question
     * @return answer
     */
    @PostMapping("/ai/rag/generate")
    public Flux<String> generateRagStream(@RequestBody ChatInput input) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(input.getMessage()).withTopK(3));
        List<String> contentList = documents.stream().map(Document::getContent).toList();
        return chatClient.prompt()
                .user(
                        userSpec -> userSpec
                                .text(template)
                                .param("input", input.getMessage())
                                .param("documents", String.join("¥n", contentList))
                ).stream().content();
    }

    /**
     * load pdf to vector database
     * @throws IOException
     */
    @GetMapping("/ai/store")
    public void store() throws IOException {
        Path path = Paths.get(loadFileDirectoryPath);
        for (File file : path.toFile().listFiles()) {
            if (file.getName().endsWith(".pdf")) {
                fileLoadService.storeFiles(file);
            }
        }
    }
}
