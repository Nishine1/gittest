package com.hisoft;

import entity.User;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.DruidUtil;

import java.util.List;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-20 15:25:50
 **/
public class TestJdbcTemplate {
    JdbcTemplate tp = new JdbcTemplate(DruidUtil.ds);
    @Test
    public void query(){
        // queryForMap()查询结果将结果集封装为map集合
//        Map<String, Object> st = tp.queryForMap("select * from user where id = ?", 1);
////        st.forEach((a,d)-> System.out.println(a+" : "+d));
//        Set<Map.Entry<String, Object>> entry = st.entrySet();
//        for (Map.Entry<String, Object> en : entry) {
//            System.out.println(en.getKey()+" : "+en.getValue());
//        }
        //queryForList()查询结果将结果集封装为list集合
//        List<Map<String, Object>> maps = tp.queryForList("select * from user where brithday = ?", "2021-5-18");
//        for (Map<String, Object> map : maps) {
//            map.forEach((a,b)-> System.out.println(a+" : "+b));
//        }
        // query()查询结果，将结果封装为JavaBean对象
        List<User> query = tp.query("select * from user where id = ?", new BeanPropertyRowMapper<User>(User.class), 1);
        for (User user : query) {
            System.out.println("user = " + user);
        }
    }
    @Test
    public void update(){
        //改
        int count= tp.update("update user set brithday = ? where id = ?", "1978-8-25", 1);
        System.out.println("count = " + count);
    }
    @Test
    public void insert(){
        int count = tp.update("insert into user values (?,?,?,?,?,?)",null,"小龙女","159","1989-2-12",5000.00,0);
        System.out.println("count = " + count);
    }
    @Test
    public void delete(){
        int count = tp.update("delete from user where usename = ?","小龙女");
        System.out.println("count = " + count);
    }
    //查询条数
    @Test
    public void testForObject(){
        Long i = tp.queryForObject("select count(*) from user", Long.class);
        System.out.println("i = " + i);
    }
}
