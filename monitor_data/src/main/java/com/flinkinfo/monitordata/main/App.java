package com.flinkinfo.monitordata.main;

import com.flinkinfo.monitordata.thread.MyThread;

/**
 * 主程序
 *
 * @author jimmy
 */
public class App
{
    public static void main(String[] args)
    {
        new MyThread().start();
//        String data = "Sat Apr 16 13:17:29 CST 2006";
//        Date date = new Date(data);
//        System.out.println(date.getTime());
    }
}
