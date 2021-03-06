package life.sc.community.controller;

import io.micrometer.core.instrument.util.StringUtils;
import life.sc.community.async.EventModel;
import life.sc.community.async.EventProducer;
import life.sc.community.async.EventType;
import life.sc.community.dto.CommentDTO;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.dto.ResultDTO;
import life.sc.community.exception.CustomizeErrorCode;
import life.sc.community.model.Comment;
import life.sc.community.model.EntityType;
import life.sc.community.model.User;
import life.sc.community.service.CommentService;
import life.sc.community.service.HotQuestionService;
import life.sc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotQuestionService hotQuestionService;

    @Autowired
    private EventProducer eventProducer;

        @ResponseBody
        @RequestMapping(value = "/comment",method = RequestMethod.POST)
        public Object post(@RequestBody CommentDTO commentDTO,
                HttpServletRequest request){
            User user =(User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录");
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
       // System.out.println("插入评论");
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        hotQuestionService.increCommentCount(comment.getParentId());
        int count = commentService.getCommentCount(commentDTO.getParentId(),commentDTO.getType());
        if(commentDTO.getType() == EntityType.ENTITY_QUESTION){
            questionService.updateCommentCount(commentDTO.getParentId(),count);
        }

        //
        EventModel eventModel = new EventModel(EventType.COMMENT);
        //Comment comment = commentService.findCommentById(commentId);
        eventModel.setActorId(user.getId());
        eventModel.setEntityId(commentDTO.getParentId());
        eventModel.setEntityOwnerId(comment.getUserId());
        eventModel.setEntityType(EntityType.ENTITY_COMMENT);
        //eventModel.setExts("questionId",String.valueOf(comment.getParentId()));
        //实现异步化
        //当LikeController这个请求结束之后,但是EventConsumer启动之后,会执行InitlizingBean方法
        //会另外开启一个线程去执行这个事件
        eventProducer.fireEvent(eventModel);

        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Object comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, EntityType.ENTITY_COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

}
