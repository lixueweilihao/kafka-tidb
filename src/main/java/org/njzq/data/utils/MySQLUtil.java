package org.njzq.data.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created on 2019-12-30
 *
 * @author :hao.li
 */
public class MySQLUtil {
    private static final Logger logger = LoggerFactory.getLogger(MySQLUtil.class);

    public static ComboPooledDataSource getConnection(Properties props) {
        Connection connection = null;
        ComboPooledDataSource dataSource =null;
        try {
            // 获取数据库连接
            String tidb_database = props.get(PropertiesConstants.TIDB_DATABASE).toString();
            String tidb_host = props.get(PropertiesConstants.TIDB_HOST).toString();
            String tidb_password = props.get(PropertiesConstants.TIDB_PASSWORD).toString();
            String tidb_port = props.get(PropertiesConstants.TIDB_PORT).toString();
            String tidb_username = props.get(PropertiesConstants.TIDB_USERNAME).toString();
            String tidb_driver = props.get(PropertiesConstants.TIDB_DRIVER).toString();
            String tidb_url = "jdbc:mysql://" + tidb_host + ":" + tidb_port + "/" + tidb_database + "?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncodeing=UTF-8&serverTimezone=GMT";

            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(tidb_driver);//驱动
            dataSource.setJdbcUrl(tidb_url);//URL
            dataSource.setUser(tidb_username);//用户名
            dataSource.setPassword(tidb_password); //密码
            //池参数基本配置
            dataSource.setMaxPoolSize(60);//设置连接池拥有的最大连接数。默认值15
            dataSource.setAcquireIncrement(10);//设置增量
            dataSource.setMinPoolSize(3); //设置连接池最少连接数
            dataSource.setInitialPoolSize(3); //连接池初始化创建的连接数。默认值3
            dataSource.setIdleConnectionTestPeriod(10000);
            dataSource.setPreferredTestQuery("SELECT 1");
            dataSource.setTestConnectionOnCheckout(true);
            dataSource.setTestConnectionOnCheckin(false);
            dataSource.setMaxIdleTime(100000);
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

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            logger.debug(e.toString());
        }
        return dataSource;
    }
}
