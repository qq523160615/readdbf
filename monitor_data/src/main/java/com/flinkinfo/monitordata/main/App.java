package com.flinkinfo.monitordata.main;


import com.flinkinfo.monitordata.thread.MyThread;

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
        String paths = input.nextLine();

        String[] pathArray = paths.split(",");

        for (String path : pathArray)
        {
            new MyThread(ip, user, password, path).start();
        }
    }
}
