package com.flinkinfo.monitordata.componet.thread;

/**
 * 定时任务
 *
 * @author jimmy
 */
//@Component
public class TaskThread extends Thread
{
//    @Autowired
//    DBFService dbfService;
//
//    @Autowired
//    PushService pushService;
//
//    @Value("${monitor.rootdir}")
//    String dbfPath;
//
//    @Autowired
//    AppCache appCache;
//
//    private String[] names = {"NQHQ", "NQXX", "NQZSXX", "NQXYXX"};
//
//    @Override
//    public void run()
//    {
//        super.run();
//        while (true)
//        {
//            try
//            {
//                Date nowDate = new Date();
//
////                if (nowDate.getTime() == DateUtil.specialDate("121200").getTime())
////                {
////                    System.out.println("清除缓存" + appCache.flushDB());
////                }
//
//                if (nowDate.getTime() == DateUtil.specialDate("090000").getTime() ||
//                        nowDate.getTime() == DateUtil.specialDate("150300").getTime())
//                {
//                    System.out.println("清除缓存" + appCache.flushDB());
//                    LoggerUtil.info("清除缓存" + appCache.flushDB());
//                    for (String name : names)
//                    {
//                        try
//                        {
//                            String result = dbfService.readChangeDBF(dbfPath + name + ".DBF");
//                            pushService.push(name, result);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
}
