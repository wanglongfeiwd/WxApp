package com.yhxx.wxappboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySources;

@SpringBootApplication(scanBasePackages = {"com.yhxx.wxapp","com.yhxx.common"})
@EncryptablePropertySources({@EncryptablePropertySource("classpath:application.properties"),
		@EncryptablePropertySource("classpath:application-dev.properties"),
		@EncryptablePropertySource("classpath:application-test.properties")
})
@EnableEncryptableProperties
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class WxAppBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxAppBootApplication.class, args);
	}
}
