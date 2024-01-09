package com.example.jpa.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class BoardLogger {

    @Around("execution(* com.example.jpa..*.*Controller.detail(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {


        Object result = joinPoint.proceed();


        if (joinPoint.getSignature().getDeclaringTypeName().contains("ApiBoardController")
        && "detail".equals(joinPoint.getSignature().getName())) {

            StringBuilder sb = new StringBuilder();

            sb.append("파라미터:");
            Object[] args = joinPoint.getArgs();
            for(Object x: args) {
                sb.append(x.toString());
            }
            sb.append("결과:");
            sb.append( result.toString());

            log.info(sb.toString());
        }

        return result;
    }
}























