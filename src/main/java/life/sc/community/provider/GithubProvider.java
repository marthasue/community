package life.sc.community.provider;

import com.alibaba.fastjson.JSON;
import life.sc.community.dto.AccessTokenDTO;
import life.sc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import static com.alibaba.fastjson.JSON.parseObject;

@Component
@org.springframework.web.bind.annotation.ResponseBody
public class GithubProvider {
    public String getAccesstoken(AccessTokenDTO accessTokenDTO){
        System.out.println("yes");
        System.out.println(accessTokenDTO.getCode());
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String s_new = s.split("&")[0].split("=")[1];
            return s_new;
        }catch (Exception e){
            System.out.println("error");
        }
        return null;
    }

    public GithubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = parseObject(string, GithubUser.class);
            return githubUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
