package life.sc.community.controller;

import life.sc.community.dto.PaginationDTO;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.mapper.UserMapper;
import life.sc.community.model.Question;
import life.sc.community.model.User;
import life.sc.community.service.CacheService;
import life.sc.community.service.HotQuestionService;
import life.sc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    /*@GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("name",name);
        return "hello";
    }*/
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotQuestionService hotQuestionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        List<Question> hotQuesions = hotQuestionService.hotQuesion();
        //System.out.println(hotQuesions);
        model.addAttribute("hotQuestions",hotQuesions);
        return "index";
    }
}
