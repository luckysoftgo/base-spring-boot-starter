package com.application.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : 孤狼
 * @NAME: BootStartApplication
 * @DESC:
 **/
@SpringBootApplication
public class BootStartApplication {
	/**
	 * 启动.
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(BootStartApplication.class, args);
	}

}
