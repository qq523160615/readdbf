package com.flinkinfo.monitordata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.flinkinfo.monitordata.componet.http.HttpClient;
import com.flinkinfo.monitordata.componet.http.bean.RequestVO;
import com.flinkinfo.monitordata.componet.util.JsonUtil;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
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

    @Value("${push.url}")
    String pushUrl;

    public void push(String name, String data) throws Exception
    {
        writeJsonFile(data, name, new Date());
        pushData(name, data);
    }

    private void pushDataFile(String name, String data) throws Exception
    {
        File file = writeJsonFile(data, name, new Date());
        httpClient.postFile(file, "", name);
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
        System.out.println("开始写入文件" + table + new Date());
        LoggerUtil.info("开始写入文件" + table + new Date());
        JSONArray jsonArray = JSON.parseArray(json);
        File file = JsonUtil.writeJosnFile(jsonPath, json, table, time, jsonArray.size());
        System.out.println("写入文件结束" + table + new Date());
        LoggerUtil.info("写入文件结束" + table + new Date());

        System.out.println("总共写入" + jsonArray.size() + "条记录");
        LoggerUtil.info("总共写入" + jsonArray.size() + "条记录");

        return file;
    }

    private void pushData(String name, String data) throws Exception
    {
        RequestVO requestVO = new RequestVO();
        requestVO.setServiceName("update" + name);

        Map map = new HashMap();
        map.put("data", data);
        map.put("name", name);

        requestVO.setParams(map);

        httpClient.post(requestVO, pushUrl);
    }

}
