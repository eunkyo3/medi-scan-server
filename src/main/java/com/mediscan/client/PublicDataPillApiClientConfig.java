package com.mediscan.client;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Feign config for MFDS pill API. Logs response body on error for debugging.
 */
@Slf4j
@Configuration
public class PublicDataPillApiClientConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
		return (methodKey, response) -> {
			String body = "";
			try {
				if (response.body() != null) {
					body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
					log.warn("Public data API error: status={}, url={}, responseBody={}",
						response.status(), response.request().url(), body);
				}
			} catch (IOException ignored) {
			}
			return defaultDecoder.decode(methodKey, response);
		};
	}
}
