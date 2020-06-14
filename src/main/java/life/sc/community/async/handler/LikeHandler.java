package life.sc.community.async.handler;

import life.sc.community.async.EventHandler;
import life.sc.community.async.EventModel;
import life.sc.community.async.EventType;
import life.sc.community.model.Message;
import life.sc.community.model.User;
import life.sc.community.service.MessageService;
import life.sc.community.service.UserService;
import life.sc.community.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 当收到点赞的时候,向对方发出一个私信
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    //向用户发送一条Message消息
    @Override
    public void doHandle(EventModel model) {
        System.out.println(model);
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);//系统向用户发送的消息
        message.setToId(model.getEntityOwnerId());//向谁发送
        message.setGmtCreate(System.currentTimeMillis());
        User user = userService.findById(model.getActorId());//获取触发点赞事件的用户
        message.setContent("用户" + user.getName() +"赞了你的评论");
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);//只关注LIKE的事件(只关心点赞的事件)
    }
}
