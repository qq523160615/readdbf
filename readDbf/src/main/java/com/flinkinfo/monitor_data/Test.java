package com.flinkinfo.monitor_data;

import com.flinkinfo.monitor_data.dbf.Rwdbf;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 *
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        String path = "/Users/jimmy/Downloads/aaa.DBF";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Rwdbf rwdbf = applicationContext.getBean(Rwdbf.class);
        rwdbf.init(path);
        List<String> columns = rwdbf.getColumns();
        for (String column : columns)
        {
            System.out.println(column);
        }
    }
}
