package com.owoonan.owoonan;

import com.owoonan.owoonan.global.config.properties.AppProperties;
import com.owoonan.owoonan.global.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
@EnableJpaAuditing
@SpringBootApplication
public class OwoonanApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwoonanApplication.class, args);
	}

}
