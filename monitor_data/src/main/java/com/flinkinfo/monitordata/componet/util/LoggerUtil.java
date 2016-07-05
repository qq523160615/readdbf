package com.flinkinfo.monitordata.componet.util;


import org.apache.log4j.Logger;

/**
 * 日志打印
 *
 * @author jimmy
 */
public class LoggerUtil
{
    private static Logger logger = Logger.getLogger(LoggerUtil.class);

    /**
     * 打印错误
     *
     * @param message 错误信息
     */
    public static void error(String message)
    {
        logger.error(message);
    }

    /**
     * 打印信息
     *
     * @param message 信息
     */
    public static void info(String message)
    {
        logger.info(message);
    }

    /**
     * 打印调试信息
     *
     * @param message 调试信息
     */
    public static void debug(String message)
    {
        logger.debug(message);
    }

}
