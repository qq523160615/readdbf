package com.flinkinfo.monitordata.dao;


import com.flinkinfo.monitordata.util.DateUtil;
import com.flinkinfo.monitordata.util.LoggerUtil;
import com.flinkinfo.monitordata.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private String date;

    /**
     * 创建表
     *
     * @param table   表名
     * @param columns 列名列表
     */
    public void create(String table, List<String> columns,Date time) throws SQLException
    {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = tempDate.format(new Date());
        String createSql = "create table if not exists " + table + DateUtil.changeToYYYYMMDDHHMMSS(time) + "(id int(5) NOT NULL auto_increment,";
        String colum = "";
        for (int i = 0; i < columns.size(); i++)
        {
            if (i != columns.size() - 1)
            {
                colum = colum + columns.get(i) + " varchar(50),";
            } else
            {
                colum = colum + columns.get(i) + " varchar(50),update_ime datetime,PRIMARY KEY  (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
            }
        }

        createSql = createSql + colum;
        System.out.println(createSql);
        LoggerUtil.info(createSql);
        dbHelper.execute(createSql);
    }

    /**
     * 插入数据
     *
     * @param table     表名
     * @param rowValues 行数据
     */
    public void insert(String table, Object[] rowValues, Date time) throws SQLException
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
        value = StringUtil.replaceSpace(value, rowValues.length);
        value = StringUtil.addChar(value);
        insertSql = insertSql + value + ",'" + DateUtil.changToYYYY_MM_DD_HH_MM_SS(time) + "')";
//        System.out.println(insertSql);
//        LoggerUtil.info(insertSql);
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

    /**
     * 清空表
     *
     * @param table
     * @throws SQLException
     */
    public void truncate(String table) throws SQLException
    {
        String truncateSql = "truncate table if exists " + table;
        System.out.println(truncateSql);
        dbHelper.execute(truncateSql);
    }
}
