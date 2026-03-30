package com.example.sms.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    int reqId = ThreadLocalRandom.current().nextInt(1, 1000000);


    @Pointcut("execution(* com.example.sms.services..*(..))")
    public void serviceLogging() {
    }

    @Around("serviceLogging()")
    public Object loggingMethodExecution(ProceedingJoinPoint pjp) throws Throwable {
        String serviceName = pjp.getTarget().getClass().getSimpleName();

        String methodeName = pjp.getSignature().getName();

        LocalDateTime startTime = LocalDateTime.now();
        String startTimerStr = startTime.format(FORMATTER);

        log.info("============ Start For ReqId {} =================", reqId);
        log.info("Service: {}, Method: {}", serviceName, methodeName);
        log.info("StartTimer: {}", startTimerStr);
        log.info("=============================");

        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            exception = throwable;
            throw exception;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            String endTimerStr = endTime.format(FORMATTER);

            Duration duration = Duration.between(startTime, endTime);
            long durationsMillis = duration.toMillis();

            log.info("============== End For ReqId {} ===============", reqId);
            log.info("Service: {}, Method: {}", serviceName, methodeName);
            if (exception != null) {
                log.error("Status: Failed | Exception: {}", exception.getMessage());
            }
            log.info("EndTimer: {}", endTimerStr);
            log.info("executionTime: {}", durationsMillis);
            log.info("=============================");
        }

    }
}

