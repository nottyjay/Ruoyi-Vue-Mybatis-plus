package com.alphay.boot.common.utils;

/**
 * 处理并记录日志文件
 *
 * @author d3code
 */
public class LogUtils
{
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
