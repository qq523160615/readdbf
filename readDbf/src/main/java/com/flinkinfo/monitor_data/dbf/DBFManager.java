package com.flinkinfo.monitor_data.dbf;

import com.flinkinfo.monitor_data.db.DbOperationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dbf文件管理类
 *
 * @author jimmy
 */
@Component
public class DBFManager
{
    @Autowired
    DbOperationManager dbOperationManager;

    @Autowired
    Rwdbf rwdbf;

    private String table;

//    public DBFManager(String ip, String user, String password)
//    {
//        dbOperationManager = new DbOperationManager(ip, user, password);
//    }

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
