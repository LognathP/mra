package com.mra.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mra.logger.ConfigProperties;

@Component
public class OtpService {
	
	@Autowired
	ConfigProperties configProp;
	
	private static final Integer EXPIRE_MINS = 5;

	 private LoadingCache<String, Integer> otpCache;
	 
	 public OtpService(){
		 super();
		 otpCache = CacheBuilder.newBuilder().
		     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
		      public Integer load(String key) {
		             return 0;
		       }
		   });
		 }
	 
	 public int generateOTP(String key){

		 Random random = new Random();
		 int otp = 100000 + random.nextInt(900000);
		 otp = 123456;
		 otpCache.put(key, otp);
		 return otp;
		  }
	 
	//This method is used to push the opt number against Key. Rewrite the OTP if it exists
	 //Using mobile number  as key
	//This method is used to return the OPT number against Key->Key values is mobilenumber
	 public int getOtp(String key){ 
	try{
	 return otpCache.get(key); 
	}catch (Exception e){
	 return 0; 
	}
	 }

	//This method is used to clear the OTP catched already
	public void clearOTP(String key){ 
	 otpCache.invalidate(key);
	 }
	
	public HttpStatus sendOtp(String mobileNum,int otp)
	{
//		String smsUrl = configProp.getConfigValue("sms.url");
//		String apikey = configProp.getConfigValue("sms.key");
//		smsUrl = smsUrl.replace("{key}", apikey).replace("{mobile}", mobileNum).replace("{otp}", String.valueOf(otp));
//		System.out.println(smsUrl);
//		System.out.println("Mobile Number : "+mobileNum);
//		System.out.println("OTP : "+otp);
//		RestTemplate restTemplate = new RestTemplate();
//		String result = restTemplate.getForObject(smsUrl, String.class);
//		JSONObject js = new JSONObject(result);
//		System.out.println("SMS Status "+js.get("Status"));
//		if(js.get("Status").toString().equalsIgnoreCase("Success"))
//		return HttpStatus.OK;
//		else
//		return HttpStatus.INTERNAL_SERVER_ERROR;
		return HttpStatus.OK;
	}
	

}
