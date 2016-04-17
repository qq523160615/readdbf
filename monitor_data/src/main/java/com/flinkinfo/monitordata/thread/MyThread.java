package com.flinkinfo.monitordata.thread;

import com.flinkinfo.monitordata.dao.DBHelper;
import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.monitor.MonitorListen;
import com.flinkinfo.monitordata.monitor.MonitorManger;

/**
 * 线程
 */
public class MyThread extends Thread
{
    private String ip;

    private String user;

    private String password;

    private String path;

    public MyThread(String ip, String user, String password, String path)
    {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.path = path;
        System.out.println("开始监控文件夹" + path + ":");
    }

    @Override
    public void run()
    {
        super.run();
        try
        {
            //DBF文件管理类
            DBFFileManager dbfFileManager = new DBFFileManager();

            //设置连接数据库地址,账号和密码
            DBHelper dbHelper = new DBHelper(ip, user, password);

            //设置监控目录
            MonitorManger monitorManger = new MonitorManger(path);

            //监控监听器
            MonitorListen monitorListen = new MonitorListen(dbfFileManager, dbHelper);

            //设置监控监听器
            monitorManger.setMonitorListen(monitorListen);

            //开启监控
            monitorManger.start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
