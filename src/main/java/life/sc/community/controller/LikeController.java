package life.sc.community.controller;


import life.sc.community.async.EventModel;
import life.sc.community.async.EventProducer;
import life.sc.community.async.EventType;
import life.sc.community.dto.ResultDTO;
import life.sc.community.exception.CustomizeErrorCode;
import life.sc.community.model.Comment;
import life.sc.community.model.EntityType;
import life.sc.community.model.HostHolder;
import life.sc.community.model.User;
import life.sc.community.service.CommentService;
import life.sc.community.service.LikeService;
import life.sc.community.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    EventProducer eventProducer;


    //对某个评论的点赞功能
    @RequestMapping(path = {"/likeComment"},method = {RequestMethod.GET})
    @ResponseBody
    public Object like(@RequestParam("id") Long commentId, HttpServletRequest request,
                       Model model){
//        if(hostHolder.getUser() == null){
////            return WendaUtil.getJSONString(999);//用户未登录
////        }
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return new ResultDTO().errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        //实现异步化
        //当LikeController这个请求结束之后,但是EventConsumer启动之后,会执行InitlizingBean方法
        //会另外开启一个线程去执行这个事件
        eventProducer.fireEvent(new EventModel(EventType.LIKE));

        long likeCount = likeService.like(user.getId(),EntityType.ENTITY_COMMENT,commentId);
        System.out.println(likeCount);
        commentService.updateLikeCount(commentId,likeCount);
        return ResultDTO.okOf(String.valueOf(likeCount));
    }

//
//    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
//    @ResponseBody
//    public String dislike(@RequestParam("commentId") int commentId){
//        if(hostHolder.getUser() == null){
//            return WendaUtil.getJSONString(999);//用户未登录
//        }
//
//        long dislikeCount = likeService.disLike(hostHolder.getUser().getId(),
//                EntityType.ENTITY_COMMENT,commentId);
//        return WendaUtil.getJSONString(0, String.valueOf(dislikeCount));
//    }

}
