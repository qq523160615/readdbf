package com.flinkinfo.monitordata.util;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * json文件操作
 */
public class JsonUtil
{
    public static void writeJosnFile(String path,String json,String fileName) throws IOException
    {
        File file = new File(path,fileName + ".json");
        file.createNewFile();
        byte[] b = json.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(b,0,b.length);
        fileOutputStream.close();
    }
}
