package com.flinkinfo.monitordata.main;

import com.flinkinfo.monitordata.dao.DBHelper;
import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.monitor.MonitorListen;
import com.flinkinfo.monitordata.monitor.MonitorManger;

import java.util.Scanner;

/**
 * 主程序
 *
 * @author jimmy
 */
public class App
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入一个数据库ip：");
        String ip = input.nextLine();
        System.out.println("请输入一个数据库用户名：");
        String user = input.nextLine();
        System.out.println("请输入一个数据库密码：");
        String password = input.nextLine();
        System.out.println("请输入一个监控目录：");
        String path = input.nextLine();

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
