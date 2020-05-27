package life.sc.community.controller;

import life.sc.community.dto.CommentDTO;
import life.sc.community.dto.CommentPreDTO;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.dto.ResultDTO;
import life.sc.community.mapper.CommentMapper;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.model.Comment;
import life.sc.community.model.Question;
import life.sc.community.model.User;
import life.sc.community.service.CommentService;
import life.sc.community.service.HotQuestionService;
import life.sc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotQuestionService hotQuestionService;

        @ResponseBody
        @RequestMapping(value = "/comment",method = RequestMethod.POST)
        public Object post(@RequestBody CommentPreDTO commentPreDTO,
                HttpServletRequest request){
            User user =(User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录");
        }
        //System.out.println(commentPreDTO);
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setQuestionId(commentPreDTO.getQuestionId());
        comment.setContent(commentPreDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        commentService.insert(comment);
        QuestionDTO questionDTO = questionService.getById(comment.getQuestionId());
        questionService.increCommentCountById(comment.getQuestionId());
        hotQuestionService.increCommentCount(comment.getQuestionId());
        return ResultDTO.okOf();
    }
}
