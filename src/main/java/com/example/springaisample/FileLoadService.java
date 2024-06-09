package com.example.springaisample;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileLoadService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public FileLoadService(EmbeddingModel embeddingModel, VectorStore vectorStore) {
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
    }

    public void storeFiles(File file) throws IOException {
        // define pdf file
        var pdfResource = new ByteArrayResource(new FileInputStream(file).readAllBytes());
        // define splitter
        var textSplitter = new TokenTextSplitter() {
            @Override
            protected List<String> splitText(String text) {
                return doSplit(text, embeddingModel.dimensions());
            }
        };
        // get document
        TikaDocumentReader documentReader = new TikaDocumentReader(pdfResource);
        List<Document> documents = documentReader.get();
        // split document
        List<Document> splitDocuments = textSplitter.apply(documents);
        // embed and save
        vectorStore.accept(splitDocuments);
    }
}
