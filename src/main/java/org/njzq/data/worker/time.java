package org.njzq.data.worker;

import org.njzq.data.utils.TidbSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LH
 * @description:
 * @date 2021-06-01 14:54
 */
public class time {
    private static final Logger logger = LoggerFactory.getLogger(time.class);
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       logger.debug(df.format(new Date()));// new Date()为获取当前系统时间
    }
}
