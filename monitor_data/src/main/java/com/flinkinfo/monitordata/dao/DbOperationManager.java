package com.flinkinfo.monitordata.dao;


import com.flinkinfo.monitordata.util.StringUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据操作管理类
 *
 * @author jimmy
 */
public class DbOperationManager
{

    private DBHelper dbHelper;

    public DbOperationManager(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    /**
     * 创建表
     *
     * @param table   表名
     * @param columns 列名列表
     */
    public void create(String table, List<String> columns) throws SQLException
    {
        String createSql = "create table if not exists " + table + "(id int(5) NOT NULL auto_increment,";
        String colum = "";
        for (int i = 0; i < columns.size(); i++)
        {
            if (i != columns.size() - 1)
            {
                colum = colum + columns.get(i) + " varchar(50),";
            } else
            {
                colum = colum + columns.get(i) + " varchar(50),PRIMARY KEY  (`id`))";
            }
        }

        createSql = createSql + colum;
        System.out.println(createSql);
        dbHelper.execute(createSql);
    }

    /**
     * 插入数据
     *
     * @param table     表名
     * @param rowValues 行数据
     */
    public void insert(String table, Object[] rowValues) throws SQLException
    {
        String insertSql = "insert into " + table + " values(null,";
        String value = "";
        for (int i = 0; i < rowValues.length; i++)
        {
            if (i != rowValues.length - 1)
            {
                value = value + rowValues[i] + ",";
            } else
            {
                value = value + rowValues[i];
            }
            value = StringUtil.cutSpace(value);
        }
        value = StringUtil.replaceSpace(value);
        value = StringUtil.addChar(value);
        insertSql = insertSql + value + ")";

        System.out.println(insertSql);
        dbHelper.execute(insertSql);
    }

    /**
     * 删除表
     *
     * @param table 表名称
     * @throws SQLException
     */
    public void delete(String table) throws SQLException
    {
        String deleteSql = "drop table if exists " + table;
        System.out.println(deleteSql);
        dbHelper.execute(deleteSql);
    }
}
