package com.easypeach.shroop.infra.phone;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.easypeach.shroop.infra.phone.dto.MessageRequest;
import com.easypeach.shroop.infra.phone.dto.PhoneAuthResponse;
import com.easypeach.shroop.infra.phone.dto.SmsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverPhoneAuthService {

	private final NaverPhoneAuthClient naverPhoneAuthClient;

	public String sendSms(final String to, final String content) {

		try {
			PhoneAuthResponse phoneAuthResponse = naverPhoneAuthClient.makeSignature();
			String accessKey = phoneAuthResponse.getAccessKey();
			String serviceId = phoneAuthResponse.getServiceId();
			String sender = phoneAuthResponse.getSender();
			String signature = phoneAuthResponse.getSignature();
			String time = phoneAuthResponse.getTime();

			List<MessageRequest> messageRequestList = new ArrayList<>();
			messageRequestList.add(new MessageRequest(to, content));
			log.info("time : " + time);

			SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", sender, "내용", messageRequestList);
			log.info("smsRequest : " + smsRequest.toString());

			ObjectMapper objectMapper = new ObjectMapper();
			String body = objectMapper.writeValueAsString(smsRequest);
			log.info("body : " + body);
			log.info("makeSignature() : " + signature);

			WebClient webClient = WebClient.builder()
				.baseUrl("https://sens.apigw.ntruss.com/sms/v2/services")
				.build();

			return webClient
				.post()
				.uri("/" + serviceId + "/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-ncp-apigw-timestamp", time)
				.header("x-ncp-iam-access-key", accessKey)
				.header("x-ncp-apigw-signature-v2", signature)
				.bodyValue(body)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException |
				 JsonProcessingException ex) {
			throw new RuntimeException("문자시스템에 문제가 있어서, 해결중 입니다.");
		}

	}

}
