package tech.ouyu.user;


import online.paly.annotation.EnableJpaFastJson;
import online.paly.annotation.EnableJpaRedis;
import online.paly.annotation.EnableRedisManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tech.ouyu.user.configration.Lookconfig;

import java.util.ArrayList;
import java.util.List;

@SpringCloudApplication
@EnableJpaRedis
@EnableJpaFastJson
@EnableRedisManager
public class UserServerApplication {


	/**
	 * 启动Ribbon 负载均衡能力
	 *
	 * @param fastJsonHttpMessageConverters json转换器
	 * @return restTemplate
	 */
	@Bean
	@LoadBalanced // 注解开启均衡负载能力
	public RestTemplate restTemplate(
			HttpMessageConverters fastJsonHttpMessageConverters) {
		HttpMessageConverter converter = new FormHttpMessageConverter();
		List<HttpMessageConverter<?>> converterList = new ArrayList<>();
		converterList.add(converter);
		converterList.addAll(fastJsonHttpMessageConverters.getConverters());
		RestTemplate restTemplate = new RestTemplate(converterList);

		return restTemplate;
	}

	@Bean
	public Lookconfig testModel() {
		return new Lookconfig();
	}
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(UserServerApplication.class);
		//springApplication.addListeners(new StartupApplicationListener());

		springApplication.run(args);
		//SpringApplication.run(UserServerApplication.class, args);
	}
}
