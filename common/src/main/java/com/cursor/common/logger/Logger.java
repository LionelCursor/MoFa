package com.cursor.common.logger;


import com.cursor.common.BuildConfig;

/**
 * Created by ldx on 2015/2/17.
 * a log util to generate custom log. 
 */
public class Logger {
    /**
     * decide whether the normal log should be print
     */
    public static boolean debug = BuildConfig.DEBUG;
    /**
     * prefix of the log which can be customized.
     */
    public static String prefix = "";

    /**
     * enum of debug level 
     */
    public enum LogLevel{
        VERBOSE(0),
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        WTF(5);
        private int level;        
        private LogLevel(int _level){
            this.level = _level;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * The debug level of Logger.(default level is VERBOSE) 
     */
    public static LogLevel level = LogLevel.VERBOSE;
    /**
     * Custom Logger  
     */
    private static LogInterface customLogger = new SimpleCustomLogger();

    /**
     * set method of customLogger
     * @param logger implementation of Log
     */
    public static void setCustomLogger(LogInterface logger){
        if (logger == null ) return;
        customLogger = logger;
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = isEmpty(prefix) ? tag : prefix + ":" + tag;
        return tag;
    }
    
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    /**
     * Judge if the log should be ignored
     * @param methodLevel level of method calling the Log
     * @param allowLevel Logger's level set in advance
     * @return boolean true if should be ignored
     */
    private static boolean shouldIgnore(LogLevel methodLevel, LogLevel allowLevel){
        return methodLevel.compareTo(allowLevel)<0;
    }

    /**
     * Key method a StackTraceElement which can get linenumber, method, and class
     * @return StackTraceElement
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
    public static void d(String content) {
        if ((shouldIgnore(LogLevel.DEBUG ,level))) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.DEBUG ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.d(tag, content, tr);
    }

    public static void e(String content) {
        if (shouldIgnore(LogLevel.ERROR ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.ERROR ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.e(tag, content, tr);
    }

    public static void i(String content) {
        if (shouldIgnore(LogLevel.INFO ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.INFO ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.i(tag, content, tr);
    }

    public static void v(String content) {
        if (shouldIgnore(LogLevel.VERBOSE ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.VERBOSE ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.v(tag, content, tr);
    }

    public static void w(String content) {
        if (shouldIgnore(LogLevel.WARN ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.WARN ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (shouldIgnore(LogLevel.WARN ,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.w(tag, tr);
    }


    public static void wtf(String content) {
        if (shouldIgnore(LogLevel.WTF,level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (shouldIgnore(LogLevel.WTF, level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (shouldIgnore(LogLevel.WTF, level)) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        customLogger.wtf(tag, tr);
    }
}
