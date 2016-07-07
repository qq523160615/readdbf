package com.flinkinfo.monitordata.componet.thread;

import com.flinkinfo.monitordata.componet.cache.AppCache;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import com.flinkinfo.monitordata.service.DBFService;
import com.flinkinfo.monitordata.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskJob")
public class TaskJob
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

    @Scheduled(cron = "0 00 09 ? * *")
    public void amJob()
    {
        pushData();
    }

    @Scheduled(cron = "0 45 12 ? * *")
    public void pmJob()
    {
        pushData();
    }

    private void pushData()
    {
        System.out.println("清除缓存" + appCache.flushDB());
        LoggerUtil.info("清除缓存" + appCache.flushDB());
        for (String name : names)
        {
            try
            {
                String result = dbfService.readChangeDBF(dbfPath + name + ".DBF");
                pushService.push(name, result);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
