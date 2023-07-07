package top.yuanql.train.member.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
@ComponentScan("top.yuanql.train") // 配置包扫描路径。默认扫描此Java文件所在目录以及其目录下是所有文件，可以通过此命令修改默认扫描的路径
public class MembernApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MembernApplication.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MembernApplication.class, args);

        // 添加启动日志
        ConfigurableEnvironment environment = run.getEnvironment();
        LOG.info("启动成功！！！！");
        LOG.info("地址：\thttp://127.0.0.1:{}{}", environment.getProperty("server.port"), environment.getProperty("server.servlet.context-path"));

    }

}
