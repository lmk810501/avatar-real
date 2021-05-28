package com.skt.treal.openavatar.build.api;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ComponentScan(
		basePackages = {
				"com.skt.treal.openavatar.build.api.*"
		}
)
@MapperScan(
		basePackages = {
				"com.skt.treal.openavatar.build.api.mapper"
		}
)
public class OpenAvatarBuildApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenAvatarBuildApiApplication.class, args);
	}
	
	@Bean
	public Queue activeMqQueueTest() {
		return new ActiveMQQueue("ActiveMqQueueTest-API");
	}
}
