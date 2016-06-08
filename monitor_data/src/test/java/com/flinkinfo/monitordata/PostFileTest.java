package com.flinkinfo.monitordata;

import com.flinkinfo.monitordata.http.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * 传送文件测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class PostFileTest
{
    @Autowired
    HttpClient httpClient;

    @Test
    public void testPushFile() throws IOException
    {
        httpClient.postFile(new File("/Users/jimmy/testData/NQZSXX.json"),"http://124.172.184.212:9395/data/update/pushFile.php","NQZSXX");
    }
}
