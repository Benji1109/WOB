package hu.wob.wobrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import hu.wob.wobrest.controller.StartUpController;

@SpringBootApplication
public class DemoApplication {
	Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	private StartUpController s;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		logger.info("Starting...");
		this.s.init();
		logger.info("Finishing...");
	}

}
