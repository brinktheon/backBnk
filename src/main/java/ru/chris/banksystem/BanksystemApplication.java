package ru.chris.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.chris.model.Role;
import ru.chris.model.User;

@SpringBootApplication
@ComponentScan("ru.chris")
@EnableJpaRepositories("ru.chris.repositories")
@EntityScan(basePackageClasses = {User.class, Role.class})
public class BanksystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanksystemApplication.class, args);
	}
}
