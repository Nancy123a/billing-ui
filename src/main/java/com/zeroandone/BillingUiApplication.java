package com.zeroandone;

import com.zeroandone.repositoryImpl.QueryDslJpaEnhancedRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass=QueryDslJpaEnhancedRepositoryImpl.class)

public class BillingUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingUiApplication.class, args);
	}
}
