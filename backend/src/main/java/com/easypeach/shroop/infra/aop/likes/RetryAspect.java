package com.easypeach.shroop.infra.aop.likes;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
@Aspect
public class RetryAspect {

	@Around("@annotation(retry)")
	public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
		int maxRetry = retry.value();

		log.info("[maxRetry] {}", maxRetry);

		Exception exceptionHolder = null;
		for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
			try {
				return joinPoint.proceed();
			} catch (Exception e) {
				log.error("[retry] try count ={}/{}", retryCount, maxRetry);
				exceptionHolder = e;
			}
		}
		throw exceptionHolder;
	}
}
