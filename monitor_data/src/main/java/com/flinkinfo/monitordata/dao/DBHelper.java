package com.flinkinfo.monitordata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
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
    private PreparedStatement pst;   //驱动程序

    @Autowired
    private DataSource dataSource;

    private static Connection connection;

    /**
     * 执行sql语句
     *
     * @param sql
     * @throws SQLException
     */
    public void execute(String sql) throws SQLException
    {
        if(connection == null)
        {
            connection = dataSource.getConnection();
        }

        if(connection.isClosed())
        {

        }

        pst = connection.prepareCall(sql);
        pst.execute();
    }

    /**
     * 关闭连接
     */
    public void close()
    {
        try
        {
            dataSource.getConnection().close();
            this.pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
