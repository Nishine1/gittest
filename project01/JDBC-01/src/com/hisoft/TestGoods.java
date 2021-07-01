package com.hisoft;

import entity.Goods;
import org.junit.Test;
import util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-18 18:40:33
 **/
public class TestGoods {
    @Test
    public void select(){
        List<Goods> goods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtil.getConnectino();
            System.out.println("数据连接成功");
            pstmt = conn.prepareStatement("select * from Goods");
            rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Date launch_date = rs.getDate("launch_date");
                Goods good = new Goods(id,name,price,launch_date);
                goods.add(good);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,pstmt,conn);
        }
        for (Goods good : goods) {
            System.out.println("good = " + good);
        }
    }
    @Test
    public void insert(){
        String sql = "insert into goods(name,price,Launch_date)values(?,?,?)";
        int count = JdbcUtil.executeUpdate(sql,"卤蛋",1.50,"2021-5-18");
        System.out.println("count = " + count);
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = JdbcUtil.getConnectino();
//            pstmt = conn.prepareStatement("insert into goods(name,price,Launch_date)values(?,?,?) ");
//            pstmt.setString(1,"卤蛋");
//            pstmt.setDouble(2,1.50);
//            pstmt.setString(3,"2021-5-18");
//            int count = pstmt.executeUpdate();
//            System.out.println("count = " + count);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.closeAll(null,pstmt,conn);
//        }
    }
    @Test
    public void update(){
        String sql = "update goods set price = ? where name = ?";
        int count = JdbcUtil.executeUpdate(sql,2.00,"卤蛋");
        System.out.println("count = " + count);
    }
    @Test
    public void delete(){
        String sql = "delete from goods where name = ?";
        int count = JdbcUtil.executeUpdate(sql,"卤蛋");
        System.out.println("count = " + count);
    }

    /**
     * 商品销售
     * 积分比例1：1
     * 一次只能购买一种商品
     * 订单：订单id 用户Id 商品id 商品数量
     */
    @Test
    public void saleGoogs(){
        Connection conn = JdbcUtil.getConnectino();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String usename = "杨过";
            String name = "卤蛋";
            //更新商品数量
            stmt.executeUpdate("update goods set count = count -150 where name = '"+name+"'");
            //更新用余额及积分
            stmt.executeUpdate("update user set money = money-150*(select price from goods where name = '"+name+"'),integral = integral+150 *(select price from goods where name = '"+name+"') where usename = '"+usename+"'");
            //生成订单
            stmt.executeUpdate("insert into orders(uid,gid,count)values((select id from user where usename = '"+usename+"'),(select id from goods where name = '"+name+"'),'"+150+"')");
            conn.commit();
            System.out.println("操作成功");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("操作失败");
        }finally {
        JdbcUtil.closeAll(null,stmt,conn);
        }
    }
}
