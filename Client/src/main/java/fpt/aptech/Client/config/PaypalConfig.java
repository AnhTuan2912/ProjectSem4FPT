/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Configuration.java to edit this template
 */
package fpt.aptech.Client.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Admin
 */
@Configuration
public class PaypalConfig {
	
	@Value("${paypal.client.app}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;
	
	@Bean
	public Map<String, String> paypalSdkConfig(){
		Map<String, String> sdkConfig = new HashMap<>();
		sdkConfig.put("mode", mode);
		return sdkConfig;
	}
	
	@Bean
	public OAuthTokenCredential authTokenCredential() {
		return new OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig());
	}
	
	/*
	 * Undeprecated method is not working, maybe because the version we're using is different.
	 */
	@Bean
	public APIContext apiContext() throws PayPalRESTException{
		@SuppressWarnings("deprecation")
		APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
		apiContext.setConfigurationMap(paypalSdkConfig());
		return apiContext;
	}
	
}
