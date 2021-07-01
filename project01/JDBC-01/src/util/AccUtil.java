package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * @program: project01
 * @description: 德鲁伊数据源工具类
 * @author: nyg
 * @create: 2021-05-20 11:01:02
 **/
public class AccUtil {
    public static DataSource ds;
    static{
        Properties pro = new Properties();
        try {
            pro.load(AccUtil.class.getClassLoader().getResourceAsStream("ab.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    static{
//        Properties pro = new Properties();
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("resources/dd.properties");
//            pro.load(fis);
//            ds = DruidDataSourceFactory.createDataSource(pro);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    //创建连接
    public static Connection getConnectino(){
    Connection conn = null;
        try {
           conn = ds.getConnection();
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
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();//归还连接
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
