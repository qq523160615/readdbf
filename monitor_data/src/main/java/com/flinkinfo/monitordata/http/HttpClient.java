package com.flinkinfo.monitordata.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flinkinfo.monitordata.http.bean.RequestVO;
import com.flinkinfo.monitordata.http.bean.ResponseVO;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 网络访问
 *
 * @author nico huangwenzeng1@163.com
 */
@Component
public class HttpClient
{
    //数据编码格式
    public static final MediaType JSONMTYPE = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    static
    {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * post请求
     *
     * @param requestVO 请求数据
     * @return
     * @throws IOException
     */
    public ResponseVO post(RequestVO requestVO, String url) throws Exception
    {
        ResponseVO responseVO = null;
        try
        {
            RequestBody body = RequestBody.create(JSONMTYPE, JSONObject.toJSONString(requestVO));

            //请求参数设置
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            //请求数据
            Response response = client.newCall(request).execute();


            String result = response.body().string();
            responseVO = JSON.parseObject(result, ResponseVO.class);


            //返回解密数据
        }
        catch (IOException e)
        {
            throw new Exception("IOException");
        }
        return responseVO;

    }

}
