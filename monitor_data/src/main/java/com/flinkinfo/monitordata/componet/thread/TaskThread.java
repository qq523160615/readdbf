package com.flinkinfo.monitordata.componet.thread;

import com.flinkinfo.monitordata.componet.cache.AppCache;
import com.flinkinfo.monitordata.componet.util.DateUtil;
import com.flinkinfo.monitordata.service.DBFService;
import com.flinkinfo.monitordata.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 *
 * @author jimmy
 */
@Component
public class TaskThread extends Thread
{
    @Autowired
    DBFService dbfService;

    @Autowired
    PushService pushService;

    @Value("${monitor.rootdir}")
    String dbfPath;

    @Autowired
    AppCache appCache;

    private String[] names = {"NQHQ", "NQXX", "NQZSXX", "NQXYXX"};

    @Override
    public void run()
    {
        super.run();
        while (true)
        {
            Date nowDate = new Date();
            if (DateUtil.changeToHHMMSS(nowDate).equals("090000") || DateUtil.changeToHHMMSS(nowDate).equals("130000"))
            {
                for (String name : names)
                {
                    try
                    {
                        String result = dbfService.readDBF(dbfPath + name + ".DBF");
                        pushService.push(name, result);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            if (DateUtil.changeToHHMMSS(nowDate).equals("000000"))
            {
                System.out.println("清除缓存" + appCache.flushDB());
            }
        }
    }
}
