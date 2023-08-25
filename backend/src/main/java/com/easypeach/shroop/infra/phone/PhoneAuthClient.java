package com.easypeach.shroop.infra.phone;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easypeach.shroop.infra.phone.dto.PhoneAuthResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhoneAuthClient {
	@Value("${spring.phone.accessKey}")
	private String accessKey;

	@Value("${spring.phone.secretKey}")
	private String secretKey;

	@Value("${spring.phone.serviceId}")
	private String serviceId;

	@Value("${spring.phone.sender}")
	private String sender;

	String time = String.valueOf(System.currentTimeMillis());

	public PhoneAuthResponse makeSignature() throws
		UnsupportedEncodingException,
		InvalidKeyException,
		NoSuchAlgorithmException {
		String space = " ";                    // one space
		String newLine = "\n";                    // new line
		String method = "POST";                    // method
		String url = "/sms/v2/services/" + serviceId + "/messages";
		String timestamp = time;
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return new PhoneAuthResponse(accessKey, serviceId, sender, encodeBase64String, time);
	}

}
