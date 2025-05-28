package com.wl.myai.store;

import com.wl.myai.DO.StoreChatMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 根据 memoryId 获取消息
     * @param memoryId
     * @return
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        StoreChatMessage storeChatMessage = mongoTemplate.findOne(query, StoreChatMessage.class);
        if (storeChatMessage == null) {
            return new LinkedList<>();
        }
        return ChatMessageDeserializer.messagesFromJson(storeChatMessage.getContent());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {

        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        Update  update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(list));
        mongoTemplate.upsert(query, update, StoreChatMessage.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, StoreChatMessage.class);
    }

}
