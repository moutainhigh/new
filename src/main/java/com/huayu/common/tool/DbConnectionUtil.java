package com.huayu.common.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by DengPeng on 2016/12/2.
 */
public class DbConnectionUtil {

    public static Properties properties = new Properties();

    /**
     * 获取单例模式下的数据库连接对象
     */
    private static Connection conn = null;

    public static void register(String path){
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("获取数据库配置信息出错！ :\n" + e);
        }
    }

    /**
     * 清除从数据库配置中读取到的数据信息
     */
    public static void unRegister() {
        properties.clear();
    }

    /**
     * 单例模式下的数据库连接对象获取方式
     *
     * @return 返回单例模式下的数据库连接对象
     */
    public static Connection getConn() {
        try {
            if (conn != null) {
                return conn;
            }
            if (properties.isEmpty()){
                throw new RuntimeException("数据库配置信息未注册！" );
            }
            String DRIVER = properties.getProperty("connection.driverClass");
            String URL = properties.getProperty("connection.url");
            /*String USER = properties.getProperty("connection.username");
            String PASSWORD = properties.getProperty("connection.password");*/
            Class.forName(DRIVER);
            //conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn = DriverManager.getConnection(URL,properties);
            if (conn != null) {
                return conn;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取数据库连接时出错！ :\n" + e);
        }
        return null;
    }

    /**
     * 释放数据库连接对象
     *
     * @param conn
     *            给定的数据库连接对象
     */
    public static void release(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
            throw new RuntimeException("释放数据库连接对象时出错！ :\n" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
