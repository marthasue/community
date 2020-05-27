package life.sc.community.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import life.sc.community.dto.QuestionDTO;
import life.sc.community.mapper.QuestionMapper;
import life.sc.community.model.Question;
import life.sc.community.util.RedisUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CacheService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    QuestionMapper questionMapper;

    /**
     * 本周热议初始化
     */
//    @Override
    public void initWeekRank() {

        // 获取7天的发表的文章
        List<Question> questions = questionMapper.findLastWeekQuestion();

        // 初始化文章的总评论量
        for (Question question : questions) {
            Date date = question.getGmtCreate();
            String key = "day:rank:" + DateUtil.format(date, DatePattern.PURE_DATE_FORMAT);
            redisUtil.zSet(key, question.getId(),question.getCommentCount());

            // 7天后自动过期(15号发表，7-（18-15）=4)
            long between = DateUtil.between(new Date(), date, DateUnit.DAY);
            long expireTime = (7 - between) * 24 * 60 * 60; // 有效时间

            redisUtil.expire(key, expireTime);

            // 缓存文章的一些基本信息（id，标题，评论数量，作者）
            hashCacheQuestionIdAndTitle(question, expireTime);
        }

        //做并集
        zunionAndStoreLast7DayForWeekRank();

    }

    /**
     * 本周合并每日评论数量操作
     */
    private void zunionAndStoreLast7DayForWeekRank() {
        String  currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);

        String destKey = "week:rank";
        List<String> otherKeys = new ArrayList<>();
        for(int i=-6; i < 0; i++) {
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);

            otherKeys.add(temp);
        }

        redisUtil.zUnionAndStore(currentKey, otherKeys, destKey);
    }

    /**
     * 缓存文章的基本信息
     * @param Question
     * @param expireTime
     */
    private void hashCacheQuestionIdAndTitle(Question Question, long expireTime) {
        String key = "rank:Question:" + Question.getId();
        boolean hasKey = redisUtil.hasKey(key);
        if(!hasKey) {

            redisUtil.hset(key, "Question:id", Question.getId(), expireTime);
            redisUtil.hset(key, "Question:title", Question.getTitle(), expireTime);
            redisUtil.hset(key, "Question:commentCount", Question.getCommentCount(), expireTime);
            redisUtil.hset(key, "Question:viewCount", Question.getViewCount(), expireTime);
        }

    }

    //@Override
    public void incrCommentCountAndUnionForWeekRank(Long QuestionId, boolean isIncr) {
        String  currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        redisUtil.zIncrementScore(currentKey, QuestionId, isIncr? 1: -1);

        Question question = questionMapper.getById(QuestionId);
        // 7天后自动过期(15号发表，7-（18-15）=4)
        Date gmtcreate = question.getGmtCreate();
        long between = DateUtil.between(new Date(),gmtcreate, DateUnit.DAY);
        long expireTime = (7 - between) * 24 * 60 * 60; // 有效时间

        // 缓存这篇文章的基本信息
        hashCacheQuestionIdAndTitle(question, expireTime);

        // 重新做并集
        zunionAndStoreLast7DayForWeekRank();
    }

    //@Override
    public void putViewCount(QuestionDTO questionDTO) {
        String key = "rank:Question:" + questionDTO.getId();


        // 1、从缓存中获取viewcount
        Integer viewCount = (Integer) redisUtil.hget(key, "Question:viewCount");

        // 2、如果没有，就先从实体里面获取，再加一
        if(viewCount != null) {
            questionDTO.setViewCount(viewCount + 1);
        } else {
            questionDTO.setViewCount(questionDTO.getViewCount() + 1);
        }

        // 3、同步到缓存里面
        redisUtil.hset(key, "Question:viewCount", questionDTO.getViewCount());

    }

}
