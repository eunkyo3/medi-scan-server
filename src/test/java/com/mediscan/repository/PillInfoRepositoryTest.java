package com.mediscan.repository;

import com.mediscan.entity.PillInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan("com.mediscan.entity")
@EnableJpaRepositories("com.mediscan.repository")
class PillInfoRepositoryTest {

	@Autowired
	private PillInfoRepository pillInfoRepository;

	@BeforeEach
	void setUp() {
		pillInfoRepository.deleteAll();

		PillInfo p1 = PillInfo.builder()
			.itemSeq("1").itemName("가스디알정").printFront("IDG").drugShape("원형").colorClass("연두")
			.build();
		PillInfo p2 = PillInfo.builder()
			.itemSeq("2").itemName("타이레놀").printFront("TY").drugShape("원형").colorClass("하양")
			.build();
		PillInfo p3 = PillInfo.builder()
			.itemSeq("3").itemName("우리집알약").printFront("ABC").drugShape("장방형").colorClass("분홍")
			.build();

		pillInfoRepository.saveAll(List.of(p1, p2, p3));
	}

	@Test
	@DisplayName("Search by print - contains match")
	void searchByPrint_shapeContains() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("IDG", null, null);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getPrintFront()).isEqualTo("IDG");
	}

	@Test
	@DisplayName("Search by print - itemName fallback")
	void searchByPrint_itemNameFallback() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("가스디", null, null);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getItemName()).contains("가스디알정");
	}

	@Test
	@DisplayName("Search by shape filters correctly")
	void searchByShape_filters() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("", "원형", null);
		assertThat(result).hasSize(2);
		assertThat(result).allMatch(p -> "원형".equals(p.getDrugShape()));
	}

	@Test
	@DisplayName("Search by color filters correctly")
	void searchByColor_filters() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("", null, "연두");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getColorClass()).isEqualTo("연두");
	}

	@Test
	@DisplayName("Search with all params - combined filter")
	void searchAllParams_combined() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("IDG", "원형", "연두");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getPrintFront()).isEqualTo("IDG");
	}

	@Test
	@DisplayName("Search with blank params - returns all (no filter)")
	void searchBlankParams_returnsAll() {
		List<PillInfo> result = pillInfoRepository.searchByPrintShapeColor("", null, null);
		assertThat(result).hasSize(3);
	}
}
