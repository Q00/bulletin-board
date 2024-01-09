package com.example.jpa.common.aop;

import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserLogin;
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
public class LoginLogger {

    @Around("execution(* com.example.jpa..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();

        if("login".equals(joinPoint.getSignature().getName())) {
            StringBuilder sb = new StringBuilder();

            sb.append("\n");
            sb.append("함수명:").append(joinPoint.getSignature().getDeclaringType()).append(", ").append(joinPoint.getSignature().getName());
            sb.append("\n");
            sb.append("매개변수:");

            Object[] args = joinPoint.getArgs();
            if (args != null) {
                for (Object x : args) {
                    if (x instanceof UserLogin) {
                        sb.append( ((UserLogin)x).toString() );

                        sb.append("\n");
                        sb.append("리턴값: ").append(result.toString());
                    }
                }
            }

            log.info(sb.toString());
        }


        return result;
    }
}























