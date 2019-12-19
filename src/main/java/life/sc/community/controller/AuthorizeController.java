package life.sc.community.controller;

import life.sc.community.dto.AccessTokenDTO;
import life.sc.community.dto.GithubUser;
import life.sc.community.mapper.UserMapper;
import life.sc.community.model.User;
import life.sc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

//import life.sc.community.mapper.UserMapper;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    //@Autowired(required = false)
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    @ResponseBody
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
        //System.out.println("code");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accesstoken = githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getuser(accesstoken);
        if(githubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
           // System.out.println(System.currentTimeMillis());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
            //登录成功，写cookie和session
            request.getSession().setAttribute("githubUser", githubUser);
            return "redirect:/";
        }
        else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }

}
