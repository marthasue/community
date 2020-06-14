package life.sc.community.service;

import life.sc.community.mapper.MessageMapper;
import life.sc.community.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public void addMessage(Message message) {
        messageMapper.addMessage(message);
    }
}
