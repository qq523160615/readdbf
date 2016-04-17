package com.flinkinfo.monitordata.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串操作类
 *
 * @author jimmy
 */
public class StringUtil
{
    public static String cutSpace(String string)
    {
        char[] chars = string.toCharArray();

        //记录空格的位置
        List<Integer> positions = new ArrayList<Integer>();
        //标记是否一起为空格字符
        boolean flag = false;
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] != ' ')
            {
                Integer position = i;
                positions.add(position);
                flag = true;
            }
        }

        //将不是空格的字符串在一起
        String result = "";
        //如果为空字符串则返回null
        if (flag)
        {
            for (int position : positions)
            {
                result = result + chars[position];
            }
        }
        else
        {
            result = "null";
        }
        return result;
    }

    public static String replaceSpace(String s)
    {
        String[] strings = s.split(",");
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < strings.length; i++)
        {
            if (strings[i].equals(""))
            {
                positions.add(i);
            }
        }
        String result = "";

        for (int j : positions)
        {
            strings[j] = "null";
        }

        for (int i = 0; i < strings.length; i++)
        {
            if (i != strings.length - 1)
            {
                result = result + strings[i] + ",";
            }
            else
            {
                result = result + strings[i];
            }
        }

        return result;
    }

    public static String addChar(String s)
    {
        String[] s1 = s.split(",");
        for (int i = 0; i < s1.length; i++)
        {
            if (!s1[i].equals("null"))
            {
                s1[i] = "'" + s1[i] + "'";
            }
        }

        String result = "";
        for (int i = 0; i < s1.length; i++)
        {
            if (i != s1.length - 1)
            {
                result = result + s1[i] + ",";
            }
            else
            {
                result = result + s1[i];
            }
        }

        return result;
    }
}
