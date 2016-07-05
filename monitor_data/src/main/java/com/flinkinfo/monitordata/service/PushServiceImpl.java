package com.flinkinfo.monitordata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.flinkinfo.monitordata.componet.http.HttpClient;
import com.flinkinfo.monitordata.componet.http.bean.RequestVO;
import com.flinkinfo.monitordata.componet.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送
 */
@Component
public class PushServiceImpl implements PushService
{
    @Value("${json.dir}")
    private String jsonPath;

    @Autowired
    HttpClient httpClient;

    public void push(String name, String data) throws Exception
    {
//        writeJsonFile(data,name,new Date());
        pushData(name, data);
    }

    /**
     * 写入json文件
     *
     * @param json
     * @param table
     * @param time
     * @throws IOException
     */
    private File writeJsonFile(String json, String table, Date time) throws IOException
    {
        System.out.println("开始写入文件" + new Date());
        JSONArray jsonArray = JSON.parseArray(json);
        File file = JsonUtil.writeJosnFile(jsonPath, json, table, time, jsonArray.size());
        System.out.println("写入文件结束" + new Date());

        return file;
    }

    private void pushData(String name, String data) throws Exception
    {
        RequestVO requestVO = new RequestVO();
        requestVO.setServiceName("updateData");

        Map map = new HashMap();
        map.put("data", data);
        map.put("name", name);

        requestVO.setParams(map);

        httpClient.post(requestVO, "http://localhost:8080/sts_receive");
    }
}
