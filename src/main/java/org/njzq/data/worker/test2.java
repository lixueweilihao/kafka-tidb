package org.njzq.data.worker;

import org.njzq.data.utils.Configuration;
import org.njzq.data.utils.TidbSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author LH
 * @description:
 * @date 2021-05-24 16:08
 */
public class test2 {
    private static final Logger logger = LoggerFactory.getLogger(test2.class);
    public static void main(String[] args) throws SQLException {
        String str = "{\n" +
                "    \"actseq\": \"187.179711\",\n" +
                "    \"jstime\": \"1622446419644494\",\n" +
                "    \"xid\": \"0x0009.00b.007b76f6\",\n" +
                "    \"op\": 2,\n" +
                "    \"scn\": \"159488223974\",\n" +
                "    \"scntime\": 1622446419,\n" +
                "    \"owner\": \"ACCOUNT\",\n" +
                "    \"name\": \"TZJZH\",\n" +
                "    \"optype\": \"INSERT\",\n" +
                "    \"after\": {\n" +
                "        \"KHH\": \"320100000086\",\n" +
                "        \"KHXM\": \"丰亮\",\n" +
                "        \"ZJZH\": \"32010000008680\",\n" +
                "        \"ZHMC\": \"丰亮\",\n" +
                "        \"BZ\": \"RMB\",\n" +
                "        \"ZHGLJG\": \"3201\",\n" +
                "        \"ZHLB\": \"2\",\n" +
                "        \"YWFW\": \"5\",\n" +
                "        \"SRYE\": \"61.3\",\n" +
                "        \"ZHYE\": \"100861.3\",\n" +
                " \t\t \"DJJE\": \"100000\",\n" +
                "        \"LXJS\": \"1741554.19\",\n" +
                "        \"DZLX\": \"0\",\n" +
                "        \"YJLX\": \"16.93\",\n" +
                "        \"TZJS\": \"0\",\n" +
                "        \"ZHZT\": \"0\",\n" +
                "        \"ZZHBZ\": \"1\",\n" +
                "        \"ZHSX\": \"0\",\n" +
                "        \"KHRQ\": \"20150428\",\n" +
                "        \"XHRQ\": \"20210531\",\n" +
                "        \"BDRQ\": \"20210531\",\n" +
                "        \"ZRSZ\": \"0\",\n" +
                "        \"DRCKJE\": \"0\",\n" +
                "        \"ZPYE\": \"0\",\n" +
                "        \"XJYE\": \"338340.33\",\n" +
                "        \"YYB\": \"3201\",\n" +
                "        \"KHQZ\": \"0A\",\n" +
                "        \"GSFL\": \"00\",\n" +
                "        \"ZCFZ\": \"A\",\n" +
                "        \"WJSZJ\": \"0\"\n" +
                "    },\n" +
                "    \"before_key\": {\n" +
                "        \"ZJZH\": \"32010000008680\"\n" +
                "    },\n" +
                "    \"after_key\": {\n" +
                "        \"ZJZH\": \"32010000008680\"\n" +
                "    },\n" +
                "    \"rowid\": \"AAGu9AAAGAAARvqAAN\",\n" +
                "    \"sync\": 1,\n" +
                "    \"sdbtype\": \"oracle\"\n" +
                "}";
        Properties prop = Configuration.getConf("conf/zjzh.properties");
        logger.debug("dsafadsfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        TidbSink tidbSink=new TidbSink(prop);
//        Gson gson = new Gson();
//        Map<?, ?> ObjectMap = null;
//        Map<?, ?> ObjectMap1 = null;
//        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?,?>>() {}.getType();
//        ObjectMap = gson.fromJson(str, type);
//        System.out.println(ObjectMap.get("optype"));
//        ObjectMap1= gson.fromJson(ObjectMap.get("after").toString(),type);
        try {
            tidbSink.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(ObjectMap1);

    }
}
