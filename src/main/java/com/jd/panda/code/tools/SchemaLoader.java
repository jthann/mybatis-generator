package com.jd.panda.code.tools;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanjuntao
 * @datetime 2015-08-20 14:53
 */
public class SchemaLoader {


    public static Log logger = LogFactory.getLog(SchemaLoader.class);

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    public static void initDbConfig() {
        XMLConfiguration xmlConfig = FactoryUtils.xmlConfiguration;
        driver = xmlConfig.getString("jdbc.driver");
        url = xmlConfig.getString("jdbc.url");
        username = xmlConfig.getString("jdbc.username");
        password = xmlConfig.getString("jdbc.password");
    }

    public static List<FieldVO> loadTableSchema(String tableName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<FieldVO> fieldVOList = new ArrayList<FieldVO>();
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String query = "select * from " + tableName + " where 1=0";
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                FieldVO field = new FieldVO();
                field.setFieldName(rsmd.getColumnName(i + 1));
                field.setName(ConvertUtil.camelize(rsmd.getColumnName(i + 1)));
                field.setFieldSize(rsmd.getColumnDisplaySize(i + 1));
                field.setType(ConvertUtil.convertType(rsmd.getColumnClassName(i + 1)));
                if ("java.lang.Integer".equals(field.getType()) && field.getFieldSize().intValue() == 4) {
                    field.setType("java.lang.Boolean");
                }
                if ("[B".equals(field.getType())) {
                    field.setType("Byte[]");
                }
                field.setComment(rsmd.getColumnLabel(i + 1));
                fieldVOList.add(field);
            }
        } catch (Exception e) {
            logger.error("加载表元数据发生错误!", e);
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn, stmt, rs);
        }
        return fieldVOList;
    }

    public static void releaseConnection(Connection conn, Statement stmt,
                                         ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
            throws Exception {
        if ((driver == null) || (url == null) || (username == null)
                || (password == null)) {
            initDbConfig();
        }
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}
