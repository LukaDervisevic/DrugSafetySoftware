package com.lukadervisevic.drugsafety;

import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.service.LekService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BackendApplication.class, args);
		LekService service = context.getBean(LekService.class);
		service.fetchAndSave();
	}

}
