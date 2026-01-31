package com.mediscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * MediScan API Server - pill identification service using MFDS public data.
 * Runs data sync on startup (when enabled), then serves search requests from local DB.
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
public class MediScanServerApplication {

	public static void main(String[] args) {
		log.info("MediScanServer starting");
		SpringApplication.run(MediScanServerApplication.class, args);
	}
}
