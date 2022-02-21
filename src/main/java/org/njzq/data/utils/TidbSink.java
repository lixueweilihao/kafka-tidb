package org.njzq.data.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @author LH
 * @description:
 * @date 2021-05-25 13:50
 */
public class TidbSink {
    private static final Logger logger = LoggerFactory.getLogger(TidbSink.class);
    private Connection connection;
    private PreparedStatement preparedInsertStatement;
    private PreparedStatement preparedUpdateStatement;
    private PreparedStatement preparedDeleteStatement;
    private PreparedStatement preparedTruncateStatement;
    Properties properties = null;
    ComboPooledDataSource dataSource=null;

    public TidbSink(Properties prop) {
        this.properties = prop;
        dataSource = MySQLUtil.getConnection(properties);
//        try {
//            preparedInsertStatement = connection.prepareStatement(properties.get(PropertiesConstants.INSERT_SQL).toString());//insert sql在配置文件中
//            preparedUpdateStatement = connection.prepareStatement(properties.get(PropertiesConstants.UPDATE_SQL).toString());//update sql在配置文件中
//            preparedDeleteStatement = connection.prepareStatement(properties.get(PropertiesConstants.DELETE_SQL).toString());//delete sql在配置文件中
//            preparedTruncateStatement = connection.prepareStatement(properties.get(PropertiesConstants.TRUNCATE_SQL).toString());//truncate sql在配置文件中
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public synchronized void write(String value) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long a = System.currentTimeMillis();
        Map ObjectMap = (Map) JSON.parse(value);
        long b = System.currentTimeMillis() - a;
        Map<Object, String> ObjectMap1 = null;
        String optype = (String) ObjectMap.getOrDefault("optype", "null");
        if (optype != "null") {
//            String optype = ObjectMap.get("optype").toString();
            String topic = properties.get("kafka.topic").toString();
            String[] split = properties.get(topic + ".zb").toString().split(",");
            if (optype.equalsIgnoreCase("UPDATE")) {
                connection = dataSource.getConnection();
                connection.createStatement();
                preparedUpdateStatement = connection.prepareStatement(properties.get(PropertiesConstants.UPDATE_SQL).toString());//update sql在配置文件中
                Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
                String jsonStr = gson.toJson(ObjectMap.get("after"));
                ObjectMap1 = (Map) JSON.parse(jsonStr);
                long c = System.currentTimeMillis() - a;
                String format = df.format(new Date());
                int leng = split.length;
                for (int i = 0; i < leng; i++) {
                    if (i == 0) {
                        preparedUpdateStatement.setString(1, format);
                    } else {
                        String key = split[i].toUpperCase();
                        String result = ObjectMap1.getOrDefault(key, "null");
                        preparedUpdateStatement.setString(i + 1, result);
                    }
                }
                long d = System.currentTimeMillis() - a;
                int execute = preparedUpdateStatement.executeUpdate();

                if (!preparedUpdateStatement.isClosed()){
                    preparedUpdateStatement.close();
                }
                if(!connection.isClosed()){
                    connection.close();
                }
                logger.debug("数据更新成功，数据是： {} ,第一次解析耗时:{},二次解析耗时：{},for循环耗时:{},处理耗时： {} ms,sql是 {},执行结果 {}", ObjectMap, b, c, d, System.currentTimeMillis() - a, preparedUpdateStatement.toString(),execute);
            } else if (optype.equalsIgnoreCase("INSERT")) {
                connection = dataSource.getConnection();
                preparedInsertStatement = connection.prepareStatement(properties.get(PropertiesConstants.INSERT_SQL).toString());//insert sql在配置文件中
                Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
                String jsonStr = gson.toJson(ObjectMap.get("after"));
                ObjectMap1 = (Map) JSON.parse(jsonStr);
                long c = System.currentTimeMillis() - a;
                int leng = split.length;
                String format1 = df.format(new Date());
                for (int i = 0; i < leng; i++) {
                    if (i == 0) {
                        preparedInsertStatement.setString(1, format1);
                    } else {
                        String key = split[i].toUpperCase();
                        String result = ObjectMap1.getOrDefault(key, "null");
                        preparedInsertStatement.setString(i + 1, result);
                    }
                }
                long d = System.currentTimeMillis() - a;
//                boolean execute = preparedInsertStatement.execute();
                int execute = preparedInsertStatement.executeUpdate();
                if (!preparedInsertStatement.isClosed()){
                    preparedInsertStatement.close();
                }
                if(!connection.isClosed()){
                    connection.close();
                }
                logger.debug("数据插入成功，数据是： {} ,第一次解析耗时:{},二次解析耗时：{},for循环耗时:{},处理耗时： {} ms,sql是:{},执行结果是: {}", ObjectMap, b, c, d, System.currentTimeMillis() - a, preparedInsertStatement.toString(),execute);
            } else if (optype.equalsIgnoreCase("DELETE")) {
                connection = dataSource.getConnection();
                preparedDeleteStatement = connection.prepareStatement(properties.get(PropertiesConstants.DELETE_SQL).toString());//delete sql在配置文件中
                Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
                String jsonStr = gson.toJson(ObjectMap.get("before"));
                ObjectMap1 = (Map) JSON.parse(jsonStr);
                long c = System.currentTimeMillis() - a;
                int leng = split.length;
                for (int i = 1; i < leng; i++) {
                    String key = split[i].toUpperCase();
                    String result = ObjectMap1.getOrDefault(key, "null");
                    preparedDeleteStatement.setString(i, result);
                }
                int execute = preparedDeleteStatement.executeUpdate();
                if (!preparedDeleteStatement.isClosed()){
                    preparedDeleteStatement.close();
                }
                if(!connection.isClosed()){
                    connection.close();
                }
                logger.debug("数据删除成功，数据是： {},第一次解析耗时:{},二次解析耗时：{},处理耗时： {} ms,执行结果是: {}", ObjectMap, b, c, System.currentTimeMillis() - a,execute);
            } else if (optype.equalsIgnoreCase("TRUNCATE TABLE")) {
                connection = dataSource.getConnection();
                preparedTruncateStatement = this.connection.prepareStatement(properties.get(PropertiesConstants.TRUNCATE_SQL).toString());//truncate sql在配置文件中
                int execute = preparedTruncateStatement.executeUpdate();
                logger.debug("表删除成功： {},处理耗时： {} ms ，处理结果是 {}", ObjectMap, System.currentTimeMillis() - a,execute);
                Thread.sleep(5000);
                if (!preparedTruncateStatement.isClosed()){
                    preparedTruncateStatement.close();
                }
                if(!this.connection.isClosed()){
                    this.connection.close();
                }
            } else {
                logger.debug("操作类型不匹配，忽略！");
            }

        }
    }
}
