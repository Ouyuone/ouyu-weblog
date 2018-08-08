package tech.ouyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer // 注解启动一个服务注册中心提供给其他应用进行对话
@SpringBootApplication
public class RegisterServerApplication {
	/**
	 * 程序入口
	 *
	 * @param args 启动参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(RegisterServerApplication.class, args);
	}
}
