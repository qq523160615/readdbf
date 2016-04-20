package com.flinkinfo.monitordata.dbf;

import com.flinkinfo.monitordata.dao.DBHelper;
import com.flinkinfo.monitordata.dao.DbOperationManager;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * dbf文件管理类
 *
 * @author jimmy
 */
public class DBFFileManager
{
    //输入流
    private InputStream fis;

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
     * @param path     dbf文件地址
     * @param dbHelper 数据库帮助类
     * @param talbe    表名
     * @throws IOException
     * @throws SQLException
     */
    public void writeToDb(String path, DBHelper dbHelper, String talbe) throws IOException, SQLException
    {
        //获取dbf文件实体
        DBFFile dbfFile = readDBF(path);

        //数据库操作管理
        DbOperationManager dbOperationManager = new DbOperationManager(dbHelper);

        //删除表
        dbOperationManager.delete(talbe);

        //创建表
        dbOperationManager.create(talbe, dbfFile.getColumns());

        //获取dbf行数据
        List<Object[]> records = dbfFile.getRecords();

        //将数据插入表中
        for (Object[] record : records)
        {
            dbOperationManager.insert(talbe, record);
        }

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
}
