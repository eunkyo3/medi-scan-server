package com.mediscan;

import com.mediscan.client.PublicDataPillApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MediScanServerApplicationTests {

	@MockBean
	private PublicDataPillApiClient publicDataPillApiClient;

	@Test
	void contextLoads() {
	}
}
