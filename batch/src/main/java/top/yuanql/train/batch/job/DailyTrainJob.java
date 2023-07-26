package top.yuanql.train.batch.job;

import cn.hutool.core.util.RandomUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.batch.job
 * @BelongsClassName: TestJob
 * @Author: yuanql
 * @CreateTime: 2023-07-24  16:43
 * @Description: test
 * @Version: 1.0
 */


@DisallowConcurrentExecution // 禁用并发执行
public class DailyTrainJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);
    private void test() {
        // 增加分布式锁，解决集群问题
        System.out.println("SpringBootTestJob  ====   test");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 增加日志流水号
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));

        LOG.info("生成每日车次数据开始");

        LOG.info("生成每日车次数据结束");
    }
}
