package team.redrock.scheduled.vo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashMap;
import java.util.Map;


@Data
public class IndividualRankZSet implements ZSetOperations.TypedTuple<String> {
    private String stuId;
    private double distance;
    private String college;

    public IndividualRankZSet(String stuId, double distance, String college) {
        this.stuId = stuId;
        this.distance = distance;
        this.college = college;
    }

    @Override
    public String getValue() {
        User user = new User(this.stuId,"匡俊霖", this.college);
        System.out.println(user.toString());
        return user.toString();
    }

    @Override
    public Double getScore() {
        return this.distance;
    }

    @Override
    public int compareTo(ZSetOperations.TypedTuple<String> o) {
        return (int) (this.distance-o.getScore());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
