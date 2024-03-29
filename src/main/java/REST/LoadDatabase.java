package REST;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {
	@Bean
	CommandLineRunner initDatabase(CarRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Car("Subaru", "Forester")));
			log.info("Preloading " + repository.save(new Car("Toyota", "Hilux")));
			log.info("Preloading " + repository.save(new Car("Nissan", "Latio")));
		};
	}
}
