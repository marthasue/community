package life.sc.community.service;

import life.sc.community.mapper.QuestionMapper;
import life.sc.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HotQuestionService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private QuestionMapper questionMapper;

    public static final String HOT_QUESTION_SCORE = "hot_question_score:";

    public void increCommentCount(Long questionId){
        String squestionId = ""+questionId;
        redisTemplate.opsForZSet().incrementScore(HOT_QUESTION_SCORE,squestionId,1);
    }

    public Set<String> getHotQuestionRank(){
        if(redisTemplate.hasKey(HOT_QUESTION_SCORE)){
            Set<String> set = redisTemplate.opsForZSet().reverseRange(HOT_QUESTION_SCORE,0,-1);
            //System.out.println(1);
            return set;
        }
        else{
            return new HashSet<>();
        }
    }

    public Double getHotQuestionScore(String obj){
        return redisTemplate.opsForZSet().score(HOT_QUESTION_SCORE,obj);
    }
    public List<Question> hotQuesion(){
        //List<Question> lastWeekQuestion = questionMapper.findLastWeekQuestion();
        Set<String> rankSet = getHotQuestionRank();
        //System.out.println(rankSet);
        List<Question> hotQuestions = new ArrayList<>();
        for(String obj:rankSet){
            Long questionId = Long.valueOf(obj);
            Question question = questionMapper.getById(questionId);
            hotQuestions.add(question);
        }
        return hotQuestions;
    }
}
