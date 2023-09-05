package com.easypeach.shroop.infra.aop.log.user;

import java.util.ArrayList;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.dto.request.ProfileEditRequest;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class UserTraceAspect {

	private final UserLogRepository logRepository;

	private final Class[] checkArgsType = {ProductRequest.class,
		Long.class,
		ProfileEditRequest.class,
		ArrayList.class};

	@Around("@annotation(userTrace)")
	public Object doTrace(ProceedingJoinPoint joinPoint,UserTrace userTrace) throws Throwable {
		Object[] args = joinPoint.getArgs();
		UserLogDto logDto = new UserLogDto();

		Arrays.stream(args).forEach(o -> {
			if(o instanceof Member){ // 로그인된 사용자만 로그를 기록한다
				Member member = (Member)o;
				logDto.setMemberId(member.getId());
				logDto.setLog(userTrace.value());
			}
		});

		for(Object obj : args) {
			if(obj !=null) {
				for(Class c : checkArgsType){
					if(c.equals(obj.getClass())){
						UserLog userLog = UserLog.createUserLog(logDto.getMemberId(), logDto.getLog());
						userLog.setParamName(obj.getClass().getName()); //파라미터 이름 저장
						userLog.setParamValue(obj.toString()); // 파라미터 값 저장
						logRepository.save(userLog);
					}
				}
			}
		}
		return joinPoint.proceed();

	}

}
