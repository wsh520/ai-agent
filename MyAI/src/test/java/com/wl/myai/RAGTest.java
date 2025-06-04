package com.wl.myai;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

@SpringBootTest
public class RAGTest {

    @Test
    public void testFileSystemDocumentLoader() {
        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        // 并使用默认的文档解析器TextDocumentParser对文档进行解析

        // 确保文件路径正确，资源位于classpath:knowledge/人工智能.md
        Document document = FileSystemDocumentLoader.loadDocument("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge\\人工智能.md");
        // 输出文档内容
        System.out.println(document.text());

        // 加载单个文档
        Document documentOne = FileSystemDocumentLoader.loadDocument("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge\\科室信息.txt", new TextDocumentParser());
        // 从一个目录中加载所有文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge", new TextDocumentParser());
        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documentsTxtAll = FileSystemDocumentLoader.loadDocuments("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge", pathMatcher, new TextDocumentParser());
        // 从一个目录及其子目录中加载所有文档
        List<Document> documentsAll = FileSystemDocumentLoader.loadDocumentsRecursively("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge", new TextDocumentParser());
    }

    @Test
    public void testClassPathDocumentLoader() {
        // 并使用默认的文档解析器TextDocumentParser对文档进行解析
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md");
        System.out.println(document.text());
        // 加载单个文档
        Document documentOne = ClassPathDocumentLoader.loadDocument("knowledge/科室信息.txt", new TextDocumentParser());
        // 从一个目录中加载所有文档
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("knowledge", new TextDocumentParser());
        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documentsTxtAll = ClassPathDocumentLoader.loadDocuments("knowledge", pathMatcher, new TextDocumentParser());
        // 从一个目录及其子目录中加载所有文档
        List<Document> documentsAll = ClassPathDocumentLoader.loadDocumentsRecursively("knowledge", new TextDocumentParser());

    }

    @Test
    public void testUrlDocumentLoader() {
        Document load = UrlDocumentLoader.load("https://www.baidu.com", new TextDocumentParser());
        System.out.println(load.text());
    }

    @Test
    public void testPDFParser() {
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/医院信息.pdf", new ApachePdfBoxDocumentParser());
        System.out.println(document);
    }

    @Test
    public void testPOIParser() throws FileNotFoundException {
        ApachePoiDocumentParser parser = new ApachePoiDocumentParser();
        Document parse = parser.parse(new FileInputStream("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge\\S4任务奖励大全(含剧情任务列表)2023.02.19.xlsx"));
        System.out.println(parse.text());
    }

    @Test
    public void testApacheTikaDocumentParser() throws IOException, TikaException, SAXException {
        ApacheTikaDocumentParser parser1 = new ApacheTikaDocumentParser();
        Document parse1 = parser1.parse(new FileInputStream("D:\\AI_Item\\ai-agent\\MyAI\\src\\main\\resources\\knowledge\\S4任务奖励大全(含剧情任务列表)2023.02.19.xlsx"));
        System.out.println(parse1.text());
    }

    /**
     * 加载文档并存入向量数据库
     */
    @Test
    public void testReadDocumentAndStore() {
        //使用ClassPathDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //ingest
        //1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300个token，并且有 0 个token的重叠部分保证连贯性
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        //3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        IngestionResult ingest = EmbeddingStoreIngestor.ingest(document, embeddingStore);
        //查看向量数据库内容
        System.out.println(embeddingStore.serializeToJson());
        System.out.println(ingest.tokenUsage());

    }

    /**
     * 文档分割
     */
    @Test
    public void testDocumentSplitter() {
        //使用ClassPathDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //自定义文档分割器
        //按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
                300,
                30,
                //token分词器：按token计算
                new HuggingFaceTokenizer());
        //按字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);

        IngestionResult ingest = EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);
        //查看向量数据库内容
        System.out.println(embeddingStore.serializeToJson());
        System.out.println(ingest.tokenUsage());
    }


    @Value("${ali.bailian}")
    private String token;
    @Test
    public void testTokenCount() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
        //计算 token 长度
        QwenTokenizer tokenizer = new QwenTokenizer(token,"qwen-max");
//        HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();
        int count = tokenizer.estimateTokenCountInMessage(userMessage);
        System.out.println("token长度：" + count);
    }

}
