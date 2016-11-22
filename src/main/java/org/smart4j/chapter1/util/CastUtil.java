package org.smart4j.chapter1.util;

import com.sun.deploy.util.StringUtils;

/**
 * Created by 陈亮 on 2016/11/22.
 * 转型操作工具类
 */
public final class CastUtil {
    /*
    * 转String
    * */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /*
    * 转String(提供默认值)
    * */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /*
   * 转Double
   * */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /*
    * 转Double(提供默认值)
    * */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue=defaultValue;
        if(obj!=null){
            String strval=castString(obj);
//            if (StringUtil.isNotEmpty(strval)){
                try {
                    doubleValue=Double.parseDouble(strval);
                }catch (NumberFormatException e){
                    doubleValue=defaultValue;
                }
//            }
        }
        return doubleValue;
    }

    /*
  * 转String
  * */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /*
    * 转String(提供默认值)
    * */
    public static long castLong(Object obj, long defaultValue) {
        long longval=defaultValue;
        if(obj!=null){
            String strval=castString(obj);
//            if(StringUtils.isNotEmpty()){
//
//            }
        }
        return longval;
    }

}
