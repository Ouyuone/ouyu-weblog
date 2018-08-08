package tech.ouyu.gateway;

import online.paly.annotation.EnableFastJson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tech.ouyu.gateway.zuul.JsonFallbackProvider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringCloudApplication
@EnableZuulProxy
@EnableFastJson
public class ApiGatewayApplication {
	/**
	 * 启动Ribbon 负载均衡能力
	 *
	 * @return restTemplate
	 */
	@Bean
	@LoadBalanced // 注解开启均衡负载能力
	public RestTemplate restTemplate(
			HttpMessageConverters fastJsonHttpMessageConverters) {
		RestTemplate restTemplate = new RestTemplate(
				fastJsonHttpMessageConverters.getConverters());
		return restTemplate;
	}

	/**
	 * zuul断路器配置
	 *
	 * @param zuulProperties zuul参数
	 * @return zuulFallbackProviders
	 */
	@Bean
	public Set<ZuulFallbackProvider> zuulFallbackProviders(
			ZuulProperties zuulProperties) {
		Set<ZuulFallbackProvider> zuulFallbackProviders = new HashSet<>();
		Map<String, ZuulProperties.ZuulRoute> zMap = zuulProperties.getRoutes();
		zMap.values().forEach(zuulRoute -> zuulFallbackProviders
				.add(new JsonFallbackProvider(zuulRoute.getServiceId())));
		return zuulFallbackProviders;
	}

	/**
	 * 程序入口
	 *
	 * @param args 启动参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
