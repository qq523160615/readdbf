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
    }
}
