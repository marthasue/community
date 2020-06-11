package life.sc.community.service;

import life.sc.community.mapper.CommentMapper;
import life.sc.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public void insert(Comment comment) {
        commentMapper.insert(comment);
        //System.out.println("保存回复");
    }

    public List<Comment> listByQuestionId(Long questionId) {
        List<Comment> comments = commentMapper.getCommentsByQuestionId(questionId);
        return comments;
    }
}
