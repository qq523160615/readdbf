package com.flinkinfo.monitor_data.db;

import com.flinkinfo.monitor_data.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据操作管理类
 *
 * @author jimmy
 */
@Component
public class DbOperationManager
{
    @Autowired
    private DBHelper dbHelper;


    /**
     * 创建表
     *
     * @param table   表名
     * @param columns 列名列表
     */
    public void create(String table, List<String> columns)
    {
        dbHelper.init();
        String createSql = "create table if not exists " + table + "(";
        String colum = "";
        for (int i = 0; i < columns.size(); i++)
        {
            if (i != columns.size() - 1)
            {
                colum = colum + columns.get(i) + " varchar(50),";
            }
            else
            {
                colum = colum + columns.get(i) + " varchar(50))";
            }
        }
        try
        {
            createSql = createSql + colum;
            System.out.println(createSql);
            dbHelper.execute(createSql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            dbHelper.close();
        }
    }

    /**
     * 插入数据
     *
     * @param table     表名
     * @param rowValues 行数据
     */
    public void insert(String table, Object[] rowValues)
    {
        dbHelper.init();
        String insertSql = "insert into " + table + " values(";
        String value = "";
        for (int i = 0; i < rowValues.length; i++)
        {
            if (i != rowValues.length - 1)
            {
                value = value + rowValues[i] + ",";
            }
            else
            {
                value = value + rowValues[i];
            }
            value = StringUtil.cutSpace(value);
        }
        value = StringUtil.replaceSpace(value);
        value = StringUtil.addChar(value);
        insertSql = insertSql + value + ")";
        try
        {
            System.out.print(insertSql);
            dbHelper.execute(insertSql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            dbHelper.close();
        }
    }
}
