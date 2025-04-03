package com.hsbc.pws.common.config;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@EnableAsync(proxyTargetClass = true, mode = AdviceMode.ASPECTJ)
@Configuration
@Slf4j
@RefreshScope
@ConfigurationProperties(prefix = "spring.async-executor")
@Data
public class AsyncExecutorConfig implements AsyncConfigurer {
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private int maxPoolSize = 200;
    private int queueCapacity = 65535;
    private int keepAliveTimeout = 60;
    private String threadNamePrefix = "spring-async-thread-";
    private Duration keepAliveTimeUnit = Duration.ofSeconds(60);

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setDaemon(false);
        executor.setKeepAliveSeconds(keepAliveTimeout);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            String msg = StringUtils.EMPTY;
            Joiner joiner = Joiner.on(",").skipNulls();
            if (!ArrayUtils.isEmpty(params)) {
                msg = StringUtils.join("线程执行出现错误，方法名�?", method.getName(), CharUtils.LF, "参数�?");
                msg += joiner.join(params);
            }

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            msg = StringUtils.join(CharUtils.LF, msg, CharUtils.LF, "异常信息�?", CharUtils.LF);
            msg = StringUtils.join(msg, stringWriter.toString());

            if (!Strings.isNullOrEmpty(msg)) {
                log.error(msg);
            }
        };
    }
}
