package org.njzq.data.utils;

import org.apache.kafka.common.protocol.types.Field;
import org.njzq.data.worker.KafakTidb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    public static Properties getConf(String str) {
        Properties prop = new Properties();
        try {
            //读取属性文件a.properties
            InputStream inputStream = new FileInputStream(str);
            prop.load(inputStream);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return prop;
    }
}
