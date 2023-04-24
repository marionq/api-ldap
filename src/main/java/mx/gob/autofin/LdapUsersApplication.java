package mx.gob.autofin;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class LdapUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(LdapUsersApplication.class, args);
	}
	
	@Bean
	 public GroupedOpenApi publicApi(){
		 return GroupedOpenApi.builder()
				 .group("springshop-public")
				 .packagesToScan("mx.gob.autofin")
				 .build();
	 }

}
