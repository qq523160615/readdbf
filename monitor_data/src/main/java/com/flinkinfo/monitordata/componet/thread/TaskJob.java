package com.flinkinfo.monitordata.componet.thread;

import com.flinkinfo.monitordata.componet.cache.AppCache;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import com.flinkinfo.monitordata.service.DBFService;
import com.flinkinfo.monitordata.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    @Value("${shell.path}")
    String shellPath;

    @Value("${process.name}")
    String processName;

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

    @Scheduled(cron = "0 30 08 ? * *")
    public void amCallApp()
    {
        killProcess();
        callCmd();
    }

    @Scheduled(cron = "0 30 12 ? * *")
    public void pmCallApp()
    {
        if (!isAlive())
        {
            killProcess();
            callCmd();
        }
    }

    private void pushData()
    {
//        System.out.println("清除缓存" + appCache.flushDB());
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

    /**
     * 调用脚本文件
     **/
    private void callCmd()
    {
        try
        {
            LoggerUtil.info("cmd.exe /C start " + shellPath);
            Process child = Runtime.getRuntime().exec("cmd.exe /C start " + shellPath);
            InputStream in = child.getInputStream();
            int c;
            while ((c = in.read()) != -1)
            {
            }
            in.close();
            try
            {
                child.waitFor();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            LoggerUtil.info("done");
            System.out.println("done");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 进程是否存在
     *
     * @return
     */
    private boolean isAlive()
    {
        //如下判断QQ.exe程序是否在运行，有则返回true
        BufferedReader br = null;
        try
        {
            Process proc = Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + processName + '"');
            br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                //判断指定的进程是否在运行
                if (line.contains(processName))
                {
                    return true;
                }
            }

            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (Exception ex)
                {
                }
            }
        }
    }

    /**
     * 杀死进程
     */
    private void killProcess()
    {
        try
        {
            String[] cmd =
                    {"tasklist"};
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc
                    .getInputStream()));
            String string_Temp = in.readLine();
            while (string_Temp != null)
            {
                System.out.println(string_Temp);
                if (string_Temp.indexOf(processName) != -1)
                    Runtime.getRuntime().exec("Taskkill /IM " + processName);
                string_Temp = in.readLine();
            }
        }
        catch (Exception e)
        {
        }
    }
}
