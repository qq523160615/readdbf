package com.flinkinfo.monitor_data;


import com.flinkinfo.monitor_data.db.DBHelper;
import com.flinkinfo.monitor_data.db.DbOperationManager;
import com.flinkinfo.monitor_data.dbf.DBFManager;
import com.flinkinfo.monitor_data.dbf.Rwdbf;
import com.flinkinfo.monitor_data.monitor.MonitorListen;
import com.flinkinfo.monitor_data.monitor.MonitorManger;

/**
 * 文件监视类
 *
 * @author chen 635722150@qq.com
 */
public class MonitoringManager
{

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        // 监控目录
        String rootDir = "/Users/jimmy/jpa/";
        DBHelper dbHelper = new DBHelper("localhost", "root", "13788798");
        DbOperationManager dbOperationManager = new DbOperationManager(dbHelper);
        Rwdbf rwdbf = new Rwdbf();
        DBFManager dbfManager = new DBFManager(dbOperationManager, rwdbf);
        //监控管理类
        MonitorManger monitorManger = new MonitorManger(rootDir, 5l);
        //设置监控监听器
        MonitorListen monitorListen = new MonitorListen(dbfManager);
        monitorManger.setListener(monitorListen);
        //开启监控
        monitorManger.start();
    }
}
