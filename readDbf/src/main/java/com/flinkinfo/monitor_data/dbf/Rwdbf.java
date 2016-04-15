package com.flinkinfo.monitor_data.dbf;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取dbf文件
 *
 * @author jimmy
 */
public class Rwdbf
{
    //列名
    private List<String> columns;

    //行数据
    private List<Object[]> records;

    /**
     * 初始化数据
     *
     * @param path
     */
    public void init(String path)
    {
        InputStream fis = null;
        columns = new ArrayList<String>();
        records = new ArrayList<Object[]>();
        try
        {
            //读取文件的输入流
            fis = new FileInputStream(path);

            //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);
            reader.setCharactersetName("GBK");

            //调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            System.out.println("字段数:" + fieldsCount);

            //取出字段信息
            for (int i = 0; i < fieldsCount; i++)
            {
                DBFField field = reader.getField(i);
                columns.add(field.getName());
            }
            Object[] rowValues;

            //一条条取出path文件中记录
            while ((rowValues = reader.nextRecord()) != null)
            {
                records.add(rowValues);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fis.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * 获取数据记妹
     *
     * @return
     */
    public List<Object[]> getRecords()
    {
        return records;
    }

    /**
     * 获取列名
     *
     * @return
     */
    public List<String> getColumns()
    {
        return columns;
    }
}