package top.yuanql.train.member.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("top.yuanql.train.member")
public class MembernApplication {

    public static void main(String[] args) {
        SpringApplication.run(MembernApplication.class, args);
    }

}
