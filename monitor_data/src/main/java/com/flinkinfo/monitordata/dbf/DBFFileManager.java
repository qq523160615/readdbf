package com.flinkinfo.monitordata.dbf;

import com.flinkinfo.monitordata.dao.DbOperationManager;
import com.flinkinfo.monitordata.http.HttpClient;
import com.flinkinfo.monitordata.http.bean.ResponseVO;
import com.flinkinfo.monitordata.util.JsonUtil;
import com.flinkinfo.monitordata.util.LoggerUtil;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    DbOperationManager dbOperationManager;

    @Value("${json.dir}")
    String jsonPath;

    @Autowired
    HttpClient httpClient;

    @Value("${transfer.url}")
    String url;

    /**
     * 获得dbf文件属性
     *
     * @param path
     * @return
     * @throws IOException
     */
    public DBFFile readDBF(String path) throws IOException
    {
        //列名
        List<String> columns = new ArrayList<String>();

        //行数据
        List<Object[]> records = new ArrayList<Object[]>();

        //dbf文件实体
        DBFFile dbfFile = new DBFFile();

        //行数据
        Object[] rowValues;

        //获取到dbf文件解读器
        DBFReader reader = getDBFreader(path);

        //调用DBFReader对实例方法得到path文件中字段的个数
        int fieldsCount = reader.getFieldCount();

        System.out.println("读取dbf文件" + new Date());
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

        //dbf设置属性
        dbfFile.setColumns(columns);
        dbfFile.setFiledCount(fieldsCount);
        dbfFile.setRecords(records);

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
    private DBFReader getDBFreader(String path) throws IOException
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
     * @param path  dbf文件地址
     * @param table 表名
     * @throws IOException
     * @throws SQLException
     */
    public void writeToDb(String path, String table, Date time) throws Exception
    {
        LoggerUtil.info("开始写入json文件" + table);
        System.out.println("开始写入json文件" + table);
        //获取dbf文件实体
        DBFFile dbfFile = readDBF(path);

        //删除表
        dbOperationManager.delete(table);
//        dbOperationManager.truncate(table);

        //创建表
        dbOperationManager.create(table, dbfFile.getColumns());

        //获取dbf行数据
        List<Object[]> records = dbfFile.getRecords();

        List<String> columns = dbfFile.getColumns();
        String json = "[";

        System.out.println("开始插入数据库" + new Date());
        //将数据插入表中
        for (int i = 0; i < records.size(); i++)
        {
            Object[] record = records.get(i);
            dbOperationManager.insert(table, record, time);
            if (i == records.size() - 1)
            {
                System.out.println(table + "插入完毕...\n插入总行数为:" + records.size());
                LoggerUtil.info(table + "插入完毕...\n插入总行数为:" + records.size());
            }


            //将数据转成json写入文件
            json = json + "{";
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

            if (i == records.size() - 1)
            {
                json = json + keyValue + "}";
            }
            else
            {
                json = json + keyValue + "},";
            }

        }
        json = json + "]";
        json = json.replace(" ", "");
        System.out.println("插入数据库完成" + new Date());
        System.out.println("开始写入文件" + new Date());
        File file = JsonUtil.writeJosnFile(jsonPath, json, table, time, records.size());
        System.out.println("写入文件结束" + new Date());
        postFile(file, table);
//        httpClient.post(requestVO,"");
        //关闭数据库
//        dbHelper.close();
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

    public void postFile(File file, String fileName) throws IOException
    {
        System.out.println("开始传送文件" + fileName + new Date());
        ResponseVO responseVO = httpClient.postFile(file, url, fileName);
        if (responseVO.getStatus() == ResponseVO.STATUS_FAILURE)
        {
            httpClient.postFile(file, url, fileName);
        }
        System.out.print("传送文件完成"  + fileName + new Date());
    }
}
