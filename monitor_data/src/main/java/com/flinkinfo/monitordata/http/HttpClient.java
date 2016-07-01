package com.flinkinfo.monitordata.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flinkinfo.monitordata.http.bean.RequestVO;
import com.flinkinfo.monitordata.http.bean.ResponseVO;
import com.flinkinfo.monitordata.util.LoggerUtil;
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
        client.setConnectTimeout(3, TimeUnit.MINUTES);
        client.setWriteTimeout(3, TimeUnit.MINUTES);
        client.setReadTimeout(3, TimeUnit.MINUTES);
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
        System.out.println(JSON.toJSONString(requestVO));
        LoggerUtil.info(JSON.toJSONString(requestVO));

        ResponseVO responseVO = null;
        RequestBody body = RequestBody.create(JSONMTYPE, JSONObject.toJSONString(requestVO));

        //请求参数设置
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        //请求数据
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        System.out.println(result);
        LoggerUtil.info(result);

        responseVO = JSON.parseObject(result, ResponseVO.class);

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
    public ResponseVO postFormFile(File file, String url, String fileName) throws IOException
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

    /**
     * 上传文件
     *
     * @param file     文件
     * @param url      上传地址
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public ResponseVO postFile(File file, String url, String fileName) throws IOException
    {
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("multipart/form-data; charset=utf-8");

        Request request = new Request.Builder()
                .url(url + "?name=" + fileName)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        System.out.println("开始上传文件");
        LoggerUtil.info("\"开始上传文件\"");
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String result = response.body().string();

        System.out.println(result);
        LoggerUtil.info(result);
        return new ResponseVO();
    }
}
