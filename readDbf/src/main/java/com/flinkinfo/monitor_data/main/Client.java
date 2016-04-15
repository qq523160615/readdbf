package com.flinkinfo.monitor_data.main;

import com.flinkinfo.monitor_data.dbf.DBFManager;

/**
 * 入口程序
 *
 * @author jimmy
 */
public class Client
{

    public static void main(String[] args) throws Exception
    {
        DBFManager dbfManager = new DBFManager();
        dbfManager.init("/Users/jimmy/Downloads/xxx.DBF", "ccc");
        dbfManager.create();
        dbfManager.insert();
    }
}
