# sample using Ollama

## what is used
- java21
- spring boot
- spring ai
- ollama(using aya for the model)
- neo4j 5.20

## how to use
### run application
```shell
$ mvn spring-boot:run -DLOAD_FILE_DIR_PATH=/path/to/doc/to/load
```

### load document to vector
```shell
$ curl "http://localhost:8080/ai/store"
```

### chat with llm without rag data
```shell
curl -H "Content-type: application/json" -X POST http://localhost:8080/ai/generate -d '{"message":"こんにちは！"}'
```

### chat with llm with rag data
```shell
curl -H "Content-type: application/json" -X POST http://localhost:8080/ai/rag/generate -d '{"message":"こんにちは！"}'
```
