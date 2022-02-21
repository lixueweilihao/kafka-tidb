package org.njzq.data.utils;


import com.mchange.v2.beans.swing.TestBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created on 2019-12-30
 *
 * @author :hao.li
 */
public class MySQLUtil_dbcp {
    private static final Logger logger = LoggerFactory.getLogger(MySQLUtil.class);

    public static Connection getConnection_1(Properties props) {
        Connection connection = null;
        try {
            // 获取数据库连接
            String tidb_database = props.get(PropertiesConstants.TIDB_DATABASE).toString();
            String tidb_host = props.get(PropertiesConstants.TIDB_HOST).toString();
            String tidb_password = props.get(PropertiesConstants.TIDB_PASSWORD).toString();
            String tidb_port = props.get(PropertiesConstants.TIDB_PORT).toString();
            String tidb_username = props.get(PropertiesConstants.TIDB_USERNAME).toString();
            String tidb_driver = props.get(PropertiesConstants.TIDB_DRIVER).toString(


            );
            String tidb_url = "jdbc:mysql://" + tidb_host + ":" + tidb_port + "/" + tidb_database + "?rewriteBatchedStatements=true&useUnicode=true&characterEncodeing=UTF-8&serverTimezone=GMT";
             BasicDataSource dataSource = new BasicDataSource();
//            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClassName(tidb_driver);//驱动
            dataSource.setUrl(tidb_url);//URL
            dataSource.setUsername(tidb_username);//用户名
            dataSource.setPassword(tidb_password); //密码
            //池参数基本配置
//            dataSource.setMaxActive(200);//设置连接池拥有的最大连接数。默认值15
//            dataSource.setMaxWait(30); //设置连接池最少连接数
//            dataSource.setMaxIdle(30); //连接池初始化创建的连接数。默认值3
            dataSource.setTimeBetweenEvictionRunsMillis(90);
            dataSource.setMinEvictableIdleTimeMillis(48);
            dataSource.setRemoveAbandonedTimeout(48);

            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestOnBorrow(true);
            dataSource.setTestOnReturn(true);
            dataSource.setTestWhileIdle(true);

            dataSource.setNumTestsPerEvictionRun(48);
            dataSource.setRemoveAbandoned(true);
//            dataSource.setInitialSize(1);//设置增量

            connection = dataSource.getConnection();//得到连接


//        Connection connection = null;
//        try {
//
//            // 获取数据库连接
//            String tidb_database = props.get(PropertiesConstants.TIDB_DATABASE).toString();
//            String tidb_host = props.get(PropertiesConstants.TIDB_HOST).toString();
//            String tidb_password = props.get(PropertiesConstants.TIDB_PASSWORD).toString();
//            String tidb_port = props.get(PropertiesConstants.TIDB_PORT).toString();
//            String tidb_username = props.get(PropertiesConstants.TIDB_USERNAME).toString();
//            String tidb_driver = props.get(PropertiesConstants.TIDB_DRIVER).toString();
//            Class.forName(tidb_driver);
//            String tidb_url = "jdbc:mysql://" + tidb_host + ":" + tidb_port + "/" + tidb_database + "?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncodeing=UTF-8&serverTimezone=GMT";
//            connection = DriverManager.getConnection(tidb_url, tidb_username, tidb_password);//写入mysql数据库

        } catch (SQLException e) {
            logger.debug(e.getMessage());
            logger.debug(e.toString());
            e.printStackTrace();
//        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            logger.debug(e.toString());
        }
        return connection;
    }
}
