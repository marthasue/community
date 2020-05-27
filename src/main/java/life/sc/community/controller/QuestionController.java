package life.sc.community.controller;

import life.sc.community.dto.CommentDTO;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.model.Comment;
import life.sc.community.service.CommentService;
import life.sc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")  Long id,
                           Model model){
        questionService.increViewCount(id);
        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> commentList = commentService.listByQuestionId(id);

        model.addAttribute("question",questionDTO);
        //System.out.println(questionDTO.getId());
        model.addAttribute("comments",commentList);
        return "question";
    }
}
