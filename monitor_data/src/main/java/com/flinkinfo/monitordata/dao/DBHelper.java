package com.flinkinfo.monitordata.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 数据库帮助类
 *
 * @author jimmy
 */
public class DBHelper
{
    //驱动程序
    private String name = "com.mysql.jdbc.Driver";

    private Connection conn;

    private PreparedStatement pst;

    public DBHelper(String ip,String user,String password)
    {
        init(ip,user,password);
    }

    /**
     * 初始化
     */
    private void init(String ip,String user,String password)
    {
        String url = "jdbc:mysql://" + ip  + ":3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
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
