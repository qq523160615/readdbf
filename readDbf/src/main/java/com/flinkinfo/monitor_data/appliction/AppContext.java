package com.flinkinfo.monitor_data.appliction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用上下文
 */
public class AppContext
{
    private static ApplicationContext context = null;

    private AppContext()
    {
    }


    public static ApplicationContext getInstance()
    {
        if (context == null)
        {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            return context;
        }
        else
        {
            return context;
        }
    }
}
