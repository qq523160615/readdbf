package com.flinkinfo.monitordata.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * json文件操作
 */
public class JsonUtil
{
    /**
     * 将json数据写入文件
     *
     * @param path
     * @param json
     * @param fileName
     * @param date
     * @throws IOException
     */
    public static void writeJosnFile(String path,String json,String fileName,Date date,int size) throws IOException
    {
        //时间日期目录 例:20160522
        String path1 = path + DateUtil.changeToYYYYMMDD(date);
        File fileDir = new File(path1);
        //判断文件夹是否已存在
        if(!fileDir.exists() && !fileDir.isDirectory())
        {
            fileDir.mkdir();
        }

        //文件日录,在日期下建立 例:HQXX
        String path2 = path1 + "/" + fileName;
        File fileDir1 = new File(path2);
        //判断文件夹是否存在
        if(!fileDir1.exists() && !fileDir1.isDirectory())
        {
            fileDir1.mkdir();
        }

        //在文件目录下建立json文件以时间为名 例:031545
        File file = new File(path2,DateUtil.changeToHHMMSS(date) +"(" + size + ")" + ".json");
        file.createNewFile();
        byte[] b = json.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(b,0,b.length);
        fileOutputStream.close();
    }
}
