package kz.oj.fjhw9.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "kz.oj.fjhw9.feignclient")
public class FeignConfiguration {

}
