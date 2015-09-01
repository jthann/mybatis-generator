package com.jd.panda.code.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class ConvertUtil {
    public static String camelize(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        String[] strPart = StringUtils.split(str, "_");
        for (int j = 0; j < strPart.length; j++) {
            ret.append(j != 0 ? upperCaseFirst(strPart[j]) : strPart[j]);
        }
        return ret.toString();
    }

    public static String thinType(String type) {
        if (type == null) {
            return "";
        }
        return type.substring(type.lastIndexOf(".") + 1, type.length());
    }

    public static String convertType(String type) {
        if (("java.sql.Timestamp".equals(type)) || ("java.sql.Date".equals(type))) {
            return "java.util.Date";
        }
        return type;
    }


    public static List<String> importType(List<FieldVO> fields) {
        List<String> types = new ArrayList<String>();
        for (FieldVO field : fields) {
            if ((field.getType().indexOf("java.lang") == -1) && (!types.contains(field.getType()))) {
                if (!field.getType().endsWith("[]"))
                    types.add(field.getType());
            }
        }
        return types;
    }

    public static String upperCaseFirst(String str) {
        if ((str != null) && (!str.equals(""))) {
            return
                    new StringBuilder(String.valueOf(str.charAt(0))).toString().toUpperCase() + str.substring(1, str.length());
        }
        return "";
    }

    public static String lowerCaseFirst(String str) {
        if ((str != null) && (!str.equals(""))) {
            return
                    new StringBuilder(String.valueOf(str.charAt(0))).toString().toLowerCase() + str.substring(1, str.length());
        }
        return "";
    }

    public static String replaceChar(String str, String oldStr, String newStr) {
        if (str != null) {
            return str.replace(oldStr, newStr);
        }
        return "";
    }
}
