package com.hisoft;

import org.junit.Test;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-19 17:28:05
 **/
public class TestAccount {
    @Test
    public void Test(){
        Connection conn = JdbcUtil.getConnectino();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate("update account set money = money-100 where name = 'a'");
            int a = 1/0; //人工异常
            stmt.executeUpdate("update account set money = money+100 where name = 'b'");
            //没有异常，事务提交!
            conn.commit();
            System.out.println("操作成功！");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("操作失败！");
        }finally {
            JdbcUtil.closeAll(null,stmt,conn);
        }
    }
}
