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
public class test1 {
    private static final Logger logger = LoggerFactory.getLogger(test1.class);
    public static void main(String[] args) throws SQLException {
        String str = "{\n" +
                "    \"actseq\": \"3624.1485\",\n" +
                "    \"jstime\": \"1632790243120350\",\n" +
                "    \"xid\": \"0x0015.002.009a61ff\",\n" +
                "    \"op\": 1,\n" +
                "    \"scn\": \"167511795209\",\n" +
                "    \"scntime\": 1632790243,\n" +
                "    \"owner\": \"MARGIN\",\n" +
                "    \"name\": \"TXY_RZQYZCTZ\",\n" +
                "    \"optype\": \"DELETE\",\n" +
                "    \"before\": {\n" +
                "        \"LSH\": \"4075\",\n" +
                "        \"JYS\": \"SH\",\n" +
                "        \"ZQDM\": \"600068\",\n" +
                "        \"ZQMC\": \"葛洲坝\",\n" +
                "        \"ZQLB\": \"A0\",\n" +
                "        \"QYLB\": \"2\",\n" +
                "        \"YYB\": \"3202\",\n" +
                "        \"KHH\": \"320200009999\",\n" +
                "        \"KHXM\": \"东方合盈\",\n" +
                "        \"GDH\": \"E058236396\",\n" +
                "        \"QYDM\": \"600068\",\n" +
                "        \"GQDJRQ\": \"20210909\",\n" +
                "        \"CQCXRQ\": \"20210909\",\n" +
                "        \"FHPXRQ\": \"20210930\",\n" +
                "        \"HLJE\": \"0\",\n" +
                "        \"HGSL\": \"3700\"\n" +
                "    },\n" +
                "    \"before_key\": {\n" +
                "        \"LSH\": \"4075\"\n" +
                "    },\n" +
                "    \"rowid\": \"AAHoQ3AA5AAAdIPAAv\",\n" +
                "    \"sync\": 1,\n" +
                "    \"sdbtype\": \"oracle\"\n" +
                "}\n";
        Properties prop = Configuration.getConf("conf/rzqytz.properties");
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
            while(true){
                tidbSink.write(str);
            Thread.sleep(200000);}
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(ObjectMap1);

    }
}
