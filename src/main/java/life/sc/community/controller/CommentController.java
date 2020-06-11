package life.sc.community.controller;

import life.sc.community.dto.CommentDTO;
import life.sc.community.dto.ResultDTO;
import life.sc.community.model.Comment;
import life.sc.community.model.User;
import life.sc.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

        @ResponseBody
        @RequestMapping(value = "/comment",method = RequestMethod.POST)
        public Object post(@RequestBody CommentDTO commentDTO,
                HttpServletRequest request){
            User user =(User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录");
        }
        System.out.println("commentcontroller");
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setQuestionId(commentDTO.getQuestionId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

}
