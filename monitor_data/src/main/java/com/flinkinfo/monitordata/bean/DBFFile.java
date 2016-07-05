package com.flinkinfo.monitordata.bean;

import java.util.List;

/**
 * dbf文件
 *
 * @author jimmy
 */
public class DBFFile
{
    //文件名
    private String name;

    //字段数
    private int filedCount;

    //列名
    private List<String> columns;

    //行数据
    private List<Object[]> records;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getFiledCount()
    {
        return filedCount;
    }

    public void setFiledCount(int filedCount)
    {
        this.filedCount = filedCount;
    }

    public List<String> getColumns()
    {
        return columns;
    }

    public void setColumns(List<String> columns)
    {
        this.columns = columns;
    }

    public List<Object[]> getRecords()
    {
        return records;
    }

    public void setRecords(List<Object[]> records)
    {
        this.records = records;
    }
}
