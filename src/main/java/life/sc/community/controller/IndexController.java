package life.sc.community.controller;

import life.sc.community.dto.PaginationDTO;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.mapper.UserMapper;
import life.sc.community.model.Question;
import life.sc.community.model.User;
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

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0)
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    //System.out.println(user.getName());
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }


        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
