package com.n1solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class N1SolutionApplication {

	@Value("${RENDER_KEEP_ALIVE:false}")
	private boolean keepAliveEnabled;

	public static void main(String[] args) {
		SpringApplication.run(N1SolutionApplication.class, args);
	}

	@Scheduled(fixedRate = 840000) // 14 minutes in milliseconds
	public void keepAliveHeartbeat() {
		if (keepAliveEnabled) {
			System.out.println("[Heartbeat] Render Keep-Alive: Instance is active at " + java.time.LocalDateTime.now());
		}
	}

}
