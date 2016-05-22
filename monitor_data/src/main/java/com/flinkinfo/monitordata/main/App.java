package com.flinkinfo.monitordata.main;


import com.flinkinfo.monitordata.thread.MyThread;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MyThread myThread = context.getBean(MyThread.class);
        myThread.start();
    }
}
