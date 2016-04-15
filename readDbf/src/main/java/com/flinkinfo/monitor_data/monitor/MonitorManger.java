package com.flinkinfo.monitor_data.monitor;


import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 监测管理类
 */
public class MonitorManger
{
    private FileAlterationMonitor monitor;

    public MonitorManger(String rootDir)
    {
        // 轮询间隔 5 毫秒
        long interval = 5l;
        FileAlterationObserver observer = new FileAlterationObserver(
                rootDir, null,
                null);
        observer.addListener(new MonitorListen());
        monitor = new FileAlterationMonitor(interval, observer);
    }

    public void start() throws Exception
    {
        monitor.start();
    }
}
