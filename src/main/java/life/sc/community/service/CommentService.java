package life.sc.community.service;

import life.sc.community.dto.CommentDTO;
import life.sc.community.mapper.CommentMapper;
import life.sc.community.mapper.UserMapper;
import life.sc.community.model.Comment;
import life.sc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        commentMapper.insert(comment);
        //System.out.println("保存回复");
    }

    public List<CommentDTO> listByQuestionId(Long questionId) {
        List<CommentDTO> comments = commentMapper.getCommentsByQuestionId(questionId);
        for (CommentDTO commentDTO:comments){
            User user = userMapper.findById(commentDTO.getUserId());
            commentDTO.setUser(user);
        }
        //System.out.println(comments);
        return comments;
    }
}
