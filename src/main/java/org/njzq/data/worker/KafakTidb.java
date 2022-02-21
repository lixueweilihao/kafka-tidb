package org.njzq.data.worker;

import org.njzq.data.utils.Configuration;
import org.njzq.data.utils.Consumer;
import org.njzq.data.utils.TidbSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author LH
 * @description: 实时解析kafka数据并写入tidb中
 * @date 2021-05-25 10:45
 */
public class KafakTidb {
    private static final Logger logger = LoggerFactory.getLogger(KafakTidb.class);
    public static void main(String[] args) {
        if (args.length == 0) {
            logger.debug("您调用main方法时没有指定任何参数！");
            return;
        }
        Properties prop = Configuration.getConf(args[0]);
        TidbSink tidbSink=new TidbSink(prop);
        try {
            Consumer.buildSource(prop,tidbSink);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
