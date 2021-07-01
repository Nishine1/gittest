package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @program: project01
 * @description: 工具类
 * @author: nyg
 * @create: 2021-05-18 14:31:32
 **/
public class JdbcUtil {
    public static String  DRIVER;
    public static String URL;
    public static String USER;
    public static String PWD;
    static{
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("resources/db.properties");
            prop.load(fis);
            DRIVER = prop.getProperty("driver");
            URL = prop.getProperty("url");
            USER = prop.getProperty("user");
            PWD = prop.getProperty("pwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //加载驱动
    static{
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //创建连接
    public static Connection getConnectino(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER,PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 所有的增删改操作
     * @param sql 增删改操作语句，带占位符的语句
     * @param parms 数量不定的具体参数
     * @return
     */
    public static int executeUpdate(String sql,Object... parms){
        Connection conn = getConnectino();
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i <parms.length; i++) {
                pstmt.setObject(i+1,parms[i]);
            }
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(null,pstmt,conn);
        }
        return count;
    }
    //关闭连接
    public static void closeAll(ResultSet rs, Statement stmt,Connection conn){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
