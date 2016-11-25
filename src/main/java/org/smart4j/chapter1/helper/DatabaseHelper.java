package org.smart4j.chapter1.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 陈亮 on 2016/11/23.
 * 操作数据库助手
 */
public final class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER;
    private static final BasicDataSource DATA_SOURCE;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);

    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    //关闭
//    public static void closeConnection() {
//        Connection conn = CONNECTION_HOLDER.get();
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                LOGGER.error("close connection failure", e);
//                throw new RuntimeException(e);
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    //查询实体类列表
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
//            closeConnection();
        }
        return entityList;
    }

    //查询单个实体
    public static <T> T queryEntity(Class<T> clazz, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(clazz), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
//            closeConnection();
        }
        return entity;
    }

    //执行查询语句
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            throw new RuntimeException(e);
        } finally {
//            closeConnection();
        }
        return result;
    }

    //执行更新语句(增删改)
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
//            closeConnection();
        }
        return rows;
    }

    //更新实体
    public static <T> boolean updateEntity(Class<T> clazz, int id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }
        String sql = "update" + getTableName(clazz) + "set";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?,");
        }
        sql += columns.substring(0, columns.lastIndexOf(",")) + " where id=?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    //删除实体
    public static <T> boolean deleteEntity(Class<T> clazz, int id) {
        String sql = "update " + getTableName(clazz) + " set "+"del_flag='1' where id=?";
        return executeUpdate(sql, id) == 1;
    }

    private static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    //新增实体
    public static <T> boolean insertEntity(Class<T> clazz, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }

        String sql = "insert into " + getTableName(clazz);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + " values " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    //执行sql文件
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }
}
