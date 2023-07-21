package top.yuanql.train.generator.server;

import top.yuanql.train.generator.util.FreemarkerUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String toPath = "generator/src/main/java/top/yuanql/train/generator/test/";
     static {
         new File(toPath).mkdirs();
     }

    public static void main(String[] args) throws Exception {
        FreemarkerUtil.initConfig("test.ftl");
        Map<String, Object> param = new HashMap<>();
        param.put("domain", "Test");
        FreemarkerUtil.generator(toPath + "Test.java", param);
    }




}
