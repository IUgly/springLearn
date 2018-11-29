package team.redrock.scheduled.vo;

/**
 * Created by huangds on 2017/10/28.
 */

public class User {

    private long id;

    private String userid;

    private String username;

    private String password;

    private String userps;

    private String college;

    @Override
    public String toString() {
        return "{"+"\"userid:\"" +"\""+ userid +"\""+","+
                "\"username:\"" +"\""+ username +"\""+","+
                "\"college:\"" +"\""+ college + "\"" +//","+
                '}';
    }


    public User(String userid, String username, String college) {
        this.userid = userid;
        this.username = username;
        this.college = college;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserps() {
        return userps;
    }

    public void setUserps(String userps) {
        this.userps = userps;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
