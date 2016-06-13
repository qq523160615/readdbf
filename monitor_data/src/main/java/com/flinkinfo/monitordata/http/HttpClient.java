package com.flinkinfo.monitordata.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flinkinfo.monitordata.http.bean.RequestVO;
import com.flinkinfo.monitordata.http.bean.ResponseVO;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;

import java.io.File;
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
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
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


    /**
     * 上传文件
     *
     * @param file     文件
     * @param url      访问地址
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public ResponseVO postFile(File file, String url, String fileName) throws IOException
    {
        MediaType FILE_TYPE = MediaType.parse("multipart/form-data;charset=utf-8;");
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(FILE_TYPE, file))
                .build();

        Request request = new Request.Builder()
                .url(url + "?name=" + fileName)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String result = response.body().string();
        ResponseVO responseVO = JSON.parseObject(result, ResponseVO.class);
        System.out.println(result);

        return responseVO;
    }

}
