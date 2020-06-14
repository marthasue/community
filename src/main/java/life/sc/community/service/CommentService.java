package life.sc.community.service;

import life.sc.community.dto.CommentDTO;
import life.sc.community.mapper.CommentMapper;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.mapper.UserMapper;
import life.sc.community.model.Comment;
import life.sc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public  void updateLikeCount(Long id,Long likeCount){
        commentMapper.updateLikeCount(id,likeCount);
    }

    public void insert(Comment comment) {
        commentMapper.insert(comment);
        //questionMapper.increCommentCountById();
        //System.out.println("保存回复");
    }

    public List<CommentDTO> listByParentId(Long id, Integer type) {
        List<Comment> comments = commentMapper.getCommentsByEntityId(id,type);
        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            User user = userMapper.findById(comment.getUserId());
            commentDTO.setUser(user);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    //获取一个Question实体中的评论数量
    public int getCommentCount(Long id,Integer type){
        return commentMapper.getCommentCount(id,type);
    }

}
