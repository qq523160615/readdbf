package com.flinkinfo.monitordata;

import com.alibaba.fastjson.JSONArray;
import com.flinkinfo.monitordata.http.HttpClient;
import com.flinkinfo.monitordata.http.bean.RequestVO;
import com.flinkinfo.monitordata.http.bean.ResponseVO;
import com.flinkinfo.monitordata.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        ResponseVO responseVO = httpClient.postFile(new File("/Users/jimmy/testData/NQHQ.json"),"http://localhost:8080/uploadFile","aaa");
    }

    @Test
    public void testPostNQHQ() throws Exception
    {
        RequestVO requestVO = new RequestVO();
        requestVO.setServiceName("updateNQHQ");
        Map map = new HashMap();
        map.put("data", JSONArray.parseArray(JsonUtil.readJsonFile("/Users/jimmy/testData/","NQHQ")));
        requestVO.setParams(map);
        httpClient.post(requestVO,"http://localhost:8080/api");
    }
}
