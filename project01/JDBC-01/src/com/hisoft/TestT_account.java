package com.hisoft;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import util.AccUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-20 16:51:45
 **/
public class TestT_account {
    public static Scanner sc =new Scanner(System.in);
    public static JdbcTemplate tp = new JdbcTemplate(AccUtil.ds);
    //开户
    public static void insert(){
        System.out.println("---------开户---------");
        System.out.println("请输入要注册的卡号：");
        String str = sc.next();
        System.out.println("请输入密码：");
        String str1 = sc.next();
        System.out.println("请输入姓名：");
        String str2 = sc.next();
        System.out.println("请输入手机号：");
        String str3 = sc.next();
        try {
            tp.update("insert into t_account(cardid,password,username,phone) values(?,?,?,?)",str,str1,str2,str3);
        } catch (DataAccessException e) {
            System.out.println("开户成功");
        }
    }
    //存款
    public static void deposit(){
        System.out.println("---------存款---------");
        System.out.println("请输入卡号：");
        String str = sc.next();
        System.out.println("请输入密码：");
        String str1 = sc.next();
        System.out.println("请输入存款金额：");
        Double money = sc.nextDouble();
        tp.update("update t_account set balance = balance + ? where cardid = ? and password = ?",money,str,str1);
        System.out.println("存款成功");
    }
    //取款
    public static void withdraw(){
        System.out.println("---------取款---------");
        System.out.println("请输入卡号：");
        String str = sc.next();
        System.out.println("请输入密码：");
        String str1 = sc.next();
        System.out.println("请输入取款金额：");
        Double money = sc.nextDouble();
        Double d = tp.queryForObject("select balance from t_account where cardid = ?", Double.class,str);
        Double balance = d;
        if(balance > money){
            tp.update("update t_account set balance = balance - ? where cardid = ? and password = ?",money,str,str1);
            System.out.println("取款成功");
        }else{
            System.out.println("取款失败");
        }
    }
    //转帐
    public static void transfer(){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = AccUtil.getConnectino();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            System.out.println("---------转账---------");
            System.out.println("请输入卡号：");
            String str = sc.next();
            System.out.println("请输入密码：");
            String str1 = sc.next();
            System.out.println("请输入转账金额：");
            Double money = sc.nextDouble();
            System.out.println("请输入对方卡号：");
            String str2 = sc.next();
            int count;
            count = stmt.executeUpdate("update t_account set balance = balance - '" + money + "' where cardid = '" + str + "' and password = '" + str1 + "'");
//            int count = AccUtil.executeUpdate("update t_account set balance = balance - ? where cardid = ? and password = ?",money,str,str1);

//            int count = tp.update( "update t_account set balance = balance - ? where cardid = ? and password = ?",money,str,str1);
            if (count != 0) {
                stmt.executeUpdate("update t_account set balance = balance + '"+money+"' where cardid = '"+str2+"'");
                AccUtil.executeUpdate("update t_account set balance = balance + ? where cardid = ?", money, str2);
//                tp.update("update t_account set balance = balance + ? where cardid = ?",money,str2);
                conn.commit();
                System.out.println("转账成功");
            } else {
                int b = 1 / 0;
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("转账失败");
        }finally {
            AccUtil.closeAll(null,stmt,conn);
        }
    }
    //修改密码
    public static void update(){
        System.out.println("---------修改密码---------");
        System.out.println("请输入卡号：");
        String str = sc.next();
        System.out.println("请输入密码：");
        String str1 = sc.next();
        System.out.println("请输入新密码：");
        String str2 = sc.next();
        tp.update("update t_account set password = ? where cardid = ? and password = ?",str2,str,str1);
        System.out.println("修改成功");
    }
    //注销账户
    public static void delete(){
        System.out.println("---------注销账户---------");
        System.out.println("请输入卡号：");
        String str = sc.next();
        System.out.println("请输入密码：");
        String str1 = sc.next();
        tp.update("delete from t_account where cardid = ? and password = ?",str,str1);
        System.out.println("注销成功");
    }
    public static void main(String[] args) {
        System.out.println("-------------欢迎进入银行系统-------------");
        Boolean flag = true;
        while(flag){
            System.out.println("功能菜单：");
            System.out.println("1.开户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.注销账户");
            System.out.println("7.退出");
            System.out.println("请选择需要进行操作的功能：");
            int a = sc.nextInt();
            switch (a) {
                case 1:
                    insert();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    update();
                    break;
                case 6:
                    delete();
                    break;
                case 7:
                    System.out.println("---------退出系统---------");
                    flag = false;
                    break;
            }
        }
    }
}
