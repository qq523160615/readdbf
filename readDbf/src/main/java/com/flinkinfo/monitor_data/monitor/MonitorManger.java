package com.flinkinfo.monitor_data.monitor;


import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 监测管理类
 */
public class MonitorManger
{
    private FileAlterationMonitor monitor;

    private FileAlterationObserver observer;

    private long interval;

    public MonitorManger(String rootDir, long interval)
    {
        // 轮询间隔 5 毫秒
        this.interval = interval;
        observer = new FileAlterationObserver(rootDir, null, null);
    }

    public void setListener(MonitorListen monitorListen)
    {
        observer.addListener(monitorListen);
        monitor = new FileAlterationMonitor(interval, observer);
    }

    public void start() throws Exception
    {
        monitor.start();
    }
}
