package br.com.virtualab.newproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    public static Logger getLogger(Class clazz) {
        return LogManager.getLogger(clazz);
    }

    public static void debug(Class clazz, String msg) {
        getLogger(clazz).debug(msg);
    }

    public static void debug(Class clazz, String msg, Throwable throwable) {
        getLogger(clazz).debug(msg, throwable);
    }

    public static void debug(Class clazz, String msg, Object object) {
        getLogger(clazz).debug(msg, object);
    }

    public static void error(Class clazz, String msg) {
        getLogger(clazz).error(msg);
    }

    public static void error(Class clazz, String msg, Throwable throwable) {
        getLogger(clazz).error(msg, throwable);
    }

    public static void error(Class clazz, String msg, Object object) {
        getLogger(clazz).error(msg, object);
    }

    public static void info(Class clazz, String msg) {
        getLogger(clazz).info(msg);
    }

    public static void info(Class clazz, String msg, Throwable throwable) {
        getLogger(clazz).info(msg, throwable);
    }

    public static void info(Class clazz, String msg, Object object) {
        getLogger(clazz).info(msg, object);
    }

    public static void warn(Class clazz, String msg) {
        getLogger(clazz).warn(msg);
    }

    public static void warn(Class clazz, String msg, Throwable throwable) {
        getLogger(clazz).warn(msg, throwable);
    }

    public static void warn(Class clazz, String msg, Object object) {
        getLogger(clazz).warn(msg, object);
    }

    public static void trace(Class clazz, String msg) {
        getLogger(clazz).trace(msg);
    }

    public static void trace(Class clazz, String msg, Throwable throwable) {
        getLogger(clazz).trace(msg, throwable);
    }

    public static void trace(Class clazz, String msg, Object object) {
        getLogger(clazz).trace(msg, object);
    }

}
