package com.flinkinfo.monitor_data;


import com.flinkinfo.monitor_data.monitor.MonitorManger;

/**
 * 文件监视类
 *
 * @author chen 635722150@qq.com
 */
public class MonitoringManager
{

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        // 监控目录
        String rootDir = "/Users/jimmy/jpa/";
        MonitorManger monitorManger = new MonitorManger(rootDir);
        monitorManger.start();
    }
}
