package entity;

import java.util.Date;

/**
 * @program: project01
 * @description:用户实体类
 * @author: nyg
 * @create: 2021-05-18 16:00:40
 **/
public class User {
    private int id;
    private String usename;
    private String password;
    private Date brithday;

    public User(int id, String usename, String password, Date brithday) {
        this.id = id;
        this.usename = usename;
        this.password = password;
        this.brithday = brithday;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usename='" + usename + '\'' +
                ", password='" + password + '\'' +
                ", brithday=" + brithday +
                '}';
    }
}
