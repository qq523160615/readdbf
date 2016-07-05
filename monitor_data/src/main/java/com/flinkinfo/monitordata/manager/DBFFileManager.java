package com.flinkinfo.monitordata.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flinkinfo.monitordata.bean.DBFFile;
import com.flinkinfo.monitordata.componet.cache.AppCache;
import com.flinkinfo.monitordata.componet.util.DateUtil;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * dbf文件管理类
 *
 * @author jimmy
 */
@Component
public class DBFFileManager
{
    //输入流
    private InputStream fis;

    @Autowired
    AppCache appCache;

    /**
     * 获得dbf文件属性
     *
     * @param path
     * @return
     * @throws IOException
     */
    private DBFFile readDBF(String path) throws IOException
    {
        //列名
        List<String> columns = new ArrayList<String>();

        //行数据
        List<Object[]> records = new ArrayList<Object[]>();

        //文件名
        File file = new File(path);
        String filename = file.getName().substring(0, file.getName().indexOf("."));

        //dbf文件实体
        DBFFile dbfFile = new DBFFile();

        //行数据
        Object[] rowValues;

        //获取到dbf文件解读器
        DBFReader reader = getDBFReader(path);

        //调用DBFReader对实例方法得到path文件中字段的个数
        int fieldsCount = reader.getFieldCount();

        System.out.println("读取dbf文件" + new Date());
        LoggerUtil.info("读取dbf文件" + new Date());

        //取出字段信息
        for (int i = 0; i < fieldsCount; i++)
        {
            DBFField field = reader.getField(i);
            columns.add(field.getName());
        }

        //一条条取出path文件中记录
        while ((rowValues = reader.nextRecord()) != null)
        {
            records.add(rowValues);
        }

        System.out.println("读取dbf文件结束" + new Date());
        LoggerUtil.info("读取dbf文件结束" + new Date());

        //dbf设置属性
        dbfFile.setColumns(columns);
        dbfFile.setFiledCount(fieldsCount);
        dbfFile.setRecords(records);
        dbfFile.setName(filename);

        closeInputStream();

        return dbfFile;
    }


    /**
     * 获取dbf文件解读器
     *
     * @param path 文件地址
     * @return
     * @throws FileNotFoundException
     * @throws DBFException
     */
    private DBFReader getDBFReader(String path) throws IOException
    {
        //读取文件的输入流
        fis = new FileInputStream(path);

        //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
        DBFReader reader = new DBFReader(fis);

        //设置读取字符编码
        reader.setCharactersetName("GBK");

        return reader;
    }

    /**
     * 将dbf文件数据写入数据库中
     *
     * @param path dbf文件地址
     * @throws IOException
     * @throws SQLException
     */
    public String readDBF(String path, boolean isCache) throws Exception
    {
        //获取dbf文件实体
        DBFFile dbfFile = readDBF(path);

        //插入数据库
        String result = changeToJson(dbfFile, isCache);

        return result;
    }

    /**
     * 插入数据库
     *
     * @throws SQLException
     * @throws IOException
     */
    private String changeToJson(DBFFile dbfFile, boolean isCache) throws SQLException, IOException
    {
        List<Object[]> records = dbfFile.getRecords();
        List<String> columns = dbfFile.getColumns();
        String table = dbfFile.getName();

        JSONArray jsonArray = new JSONArray();
        String json = "";

        String id = "";
        //将数据插入表中
        for (int i = 0; i < records.size(); i++)
        {
            Object[] record = records.get(i);
            if (table.equals("NQXX") || table.equals("NQHQ"))
            {
                id = table + record[0];
            }
            else if (table.equals("NQXYXX"))
            {
                id = table + record[0] + DateUtil.changeToYYYYMMDD(new Date()) + record[5];
            }
            else if (table.equals("NQZXXX"))
            {
                id = table + record[0] + DateUtil.changeToYYYYMMDD(new Date()) + record[5];
            }

            //将数据转成json写入文件
            String keyValue = "";
            for (int j = 0; j < columns.size(); j++)
            {
                String result = record[j] + "";
                if (columns.get(j).equals("XXGPRQ") || columns.get(j).equals("XXZQQXR") || columns.get(j).equals("XXDQR"))
                {
                    try
                    {
                        Date date = new Date(result);
                        result = date.getTime() + "";
                    }
                    catch (Exception e)
                    {
                        result = record[j] + "";
                    }
                }


                if (j != columns.size() - 1)
                {
                    keyValue = keyValue + "\"" + columns.get(j) + "\":" + "\"" + result + "\",";
                }
                else
                {
                    keyValue = keyValue + "\"" + columns.get(j) + "\":" + "\"" + result + "\"";
                }
            }

            json = "{" + keyValue + "}";
            json = json.replace(" ", "");
            if (isCache)
            {
                String value = appCache.get(id);
                if (value == null)
                {
                    appCache.put(id, json);
                    JSONObject jsonObject = JSON.parseObject(json);
                    jsonArray.add(jsonObject);
                }
                else if (!value.equals(json))
                {
                    JSONObject jsonObject = JSON.parseObject(json);
                    appCache.put(id, json);
                    jsonArray.add(jsonObject);
                }
            }
            else
            {
                JSONObject jsonObject = JSON.parseObject(json);
                jsonArray.add(jsonObject);
            }
        }

        if (jsonArray.size() == 0)
        {
            return "";
        }
        else
        {
            return JSON.toJSONString(jsonArray);
        }
    }




    /**
     * 关闭文件流
     *
     * @throws IOException
     */
    public void closeInputStream() throws IOException
    {
        fis.close();
    }


//    public void postFile(File file, String fileName) throws IOException
//    {
//        System.out.println("开始传送文件" + fileName + new Date());
//        ResponseVO responseVO = httpClient.postFile(file, url, fileName);
//        if (responseVO.getStatus() == ResponseVO.STATUS_FAILURE)
//        {
//            httpClient.postFile(file, url, fileName);
//        }
//        System.out.print("传送文件完成" + fileName + new Date());
//    }
}
