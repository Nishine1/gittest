package com.hisoft;

import entity.User;
import org.junit.Test;
import util.DruidUtil;
import util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-18 10:14:28
 **/
public class TestJdbc {
    @Test
    public void select(){
        List<User> list = new ArrayList<>();
        //2.创建连接
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnectino();
            System.out.println("获取连接成功");
            //3.创建statement对象，对象可以发送SQL
            stmt = conn.createStatement();
            //4.发送SQL
            String sql = "select * from user";
            rs = stmt.executeQuery(sql);
            //5.处理结果集
            while(rs.next()){
                //通过列名取值
                int id = rs.getInt("id");
                String username = rs.getString("usename");
                String password = rs.getString("password");
                Date brithday = rs.getDate("brithday");
                User user = new User(id,username,password,brithday);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭连接，后打开的先关闭
            JdbcUtil.closeAll(rs,stmt,conn);
        }
        //遍历list集合
        for (User user : list) {
            System.out.println("user = " + user);
        }

    }
    @Test
    public void findUser(){
        //2.创建连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = JdbcUtil.getConnectino();
            //3.创建statement对象，对象可以发送SQL
            pstmt = conn.prepareStatement("select * from user where usename=? and password=?");
            pstmt.setString(1,"郭靖");
            pstmt.setString(2,"123");
            rs = pstmt.executeQuery();
            //5.处理结果集
            while(rs.next()){
                //通过列名取值
                int id = rs.getInt("id");
                String username = rs.getString("usename");
                String password = rs.getString("password");
                Date brithday = rs.getDate("brithday");
                user = new User(id,username,password,brithday);
                System.out.println("登陆成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭连接，后打开的先关闭
            JdbcUtil.closeAll(rs,pstmt,conn);
        }
            System.out.println("user = " + user);
    }
    @Test
    public void insert(){
        String sql = "insert into user(usename,password,brithday)values(?,?,?)";
        int count = DruidUtil.executeUpdate(sql,"小龙女","456","2015-5-18");
        System.out.println("count = " + count);
    }
    @Test
    public void update() {
        String sql = "update user set password = ?,brithday = ? where usename = ?";
        int count = JdbcUtil.executeUpdate(sql,133,"2020-5-18","小龙女");
        System.out.println("count = " + count);
    }
    @Test
    public void delete() {
        String sql = "delete from user where usename = ?";
        int count = JdbcUtil.executeUpdate(sql,"小龙女");
        System.out.println("count = " + count);
    }
}
