package com.flinkinfo.monitor_data.db;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 数据库帮助类
 *
 * @author jimmy
 */
@Component
public class DBHelper
{
    //连接url
    @Value("${jdbc.url}")
    private String url;

    //驱动程序
    @Value("${jdbc.driverClassName}")
    private String name;

    //用户名
    @Value("${jdbc.username}")
    private String user;

    //用户密码
    @Value("${jdbc.password}")
    private String password;

    private Connection conn;

    private PreparedStatement pst;

//    public DBHelper(String ip, String user, String password)
//    {
//        this.url = "jdbc:mysql://" + ip + ":3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
//        this.user = user;
//        this.password = password;
//        init();
//    }

    /**
     * 初始化
     */
    public void init()
    {
        System.out.println(name);
        try
        {
            //指定连接类型
            Class.forName(name);
            //获取连接
            conn = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 执行sql语句
     *
     * @param sql
     * @throws SQLException
     */
    public void execute(String sql) throws SQLException
    {
        pst = conn.prepareCall(sql);
        pst.execute();
    }

    /**
     * 关闭连接
     */
    public void close()
    {
        try
        {
            this.conn.close();
            this.pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
