package com.flinkinfo.monitor_data.dbf;

import com.flinkinfo.monitor_data.db.DbOperationManager;

import java.util.List;

/**
 * dbf文件管理类
 *
 * @author jimmy
 */
public class DBFManager
{
    DbOperationManager dbOperationManager;

    Rwdbf rwdbf;

    private String table;

    public DBFManager(DbOperationManager dbOperationManager, Rwdbf rwdbf)
    {
        this.dbOperationManager = dbOperationManager;
        this.rwdbf = rwdbf;
    }

    public void init(String path, String table)
    {
        rwdbf.init(path);
        this.table = table;
    }

    public void create()
    {
        dbOperationManager.create(table, rwdbf.getColumns());
    }

    public void insert()
    {
        List<Object[]> record = rwdbf.getRecords();
        for (Object[] rowVaues : record)
        {
            dbOperationManager.insert(table, rowVaues);
        }
    }
}
