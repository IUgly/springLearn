package team.redrock.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import team.redrock.scheduled.StartSpringBootMain;
import team.redrock.scheduled.vo.IndividualRankZSet;
import team.redrock.scheduled.vo.User;

import javax.annotation.Resource;
import java.util.*;

import static net.minidev.json.JSONValue.toJSONString;

@SpringBootTest(classes = StartSpringBootMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDeptService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String SCORE_RANK = "tedsswqt1wq1";

    @Test
    public void test() throws Exception {

        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(""+(char)Math.random()*26+'A'+i, Math.random()*10000+i);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = this.redisTemplate.opsForZSet().add(SCORE_RANK, tuples);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);
    }
    @Test
    public void sort() {
        long start = System.currentTimeMillis();
        Set<ZSetOperations.TypedTuple<String>> individualRankZSetList = new HashSet<>();
        for (int i=0; i< 10000; i++){
            IndividualRankZSet typedTuple = new IndividualRankZSet("201721"+i,2000+i, "计算机");
            individualRankZSetList.add(typedTuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = this.redisTemplate.opsForZSet().add(SCORE_RANK, individualRankZSetList);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);
    }

    /**
     * 获取排行列表
     */
    @Test
    public void list() {

        Set<String> range = redisTemplate.opsForZSet().reverseRange(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行列表:" + toJSONString(range));
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        Iterator<ZSetOperations.TypedTuple<String>> it = rangeWithScores.iterator();
        int start = 0;
        JSONArray jsonArray = new JSONArray();
        while (it.hasNext()) {
            ZSetOperations.TypedTuple str = it.next();
            System.out.println(str.getValue().toString());
            com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(str.getValue().toString());
            json.put("rank", start+1);
            jsonArray.add(json);
            start++;
        }
        System.out.println("获取到的排行和分数列表:" + jsonArray.toJSONString());

    }

    /**
     * 单个新增
     */
    @Test
    public void add() {
        User user = new User("2011212","牛七", "tongxin");

        redisTemplate.opsForZSet().add(SCORE_RANK, user.toString(), 3986);
    }
    /**
     * 获取单个的排行
     */
    @Test
    public void find(){
        User user = new User("2011212","牛七", "tongxin");
        Long rankNum = redisTemplate.opsForZSet().reverseRank(SCORE_RANK, user.toString());
        System.out.println("牛七的个人排名：" + rankNum);


        Double score = redisTemplate.opsForZSet().score(SCORE_RANK, user.toString());
        System.out.println("牛七的分数:" + score);


        Set<ZSetOperations.TypedTuple<String>> range = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, rankNum-1, rankNum-1);
        Iterator<ZSetOperations.TypedTuple<String>> it = range.iterator();
        while (it.hasNext()) {
            ZSetOperations.TypedTuple str = it.next();
            System.out.println("牛七的前一名分数："+str.getScore());
        }
    }
    /**
     * 统计两个分数之间的人数
     */
    @Test
    public void count(){
        Long count = redisTemplate.opsForZSet().count(SCORE_RANK, 8001, 9000);
        System.out.println("统计8001-9000之间的人数:" + count);
    }

    /**
     * 使用加法操作分数
     */
    @Test
    public void incrementScore(){
        Double score = redisTemplate.opsForZSet().incrementScore(SCORE_RANK, "牛七", 10000);
        System.out.println("牛7分数+1000后：" + score);
    }

}
