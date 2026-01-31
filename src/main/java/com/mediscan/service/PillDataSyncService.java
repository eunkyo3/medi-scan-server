package com.mediscan.service;

import com.mediscan.client.PublicDataPillApiClient;
import com.mediscan.client.dto.PublicDataPillItem;
import com.mediscan.entity.PillInfo;
import com.mediscan.repository.PillInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * On startup, fetches all pages from MFDS public API and stores into pill_info.
 * Runs only when api.public-data.sync-on-startup=true.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.public-data.sync-on-startup", havingValue = "true")
@Order(1)
public class PillDataSyncService implements ApplicationRunner {

	private final PublicDataPillApiClient publicDataPillApiClient;
	private final PillInfoRepository pillInfoRepository;

	@Value("${api.public-data.service-key:}")
	private String serviceKey;

	@Value("${api.public-data.page-size:100}")
	private int pageSize;

	@Value("${api.public-data.response-type:json}")
	private String responseType;

	@Value("${api.public-data.sync-only-when-empty:false}")
	private boolean syncOnlyWhenEmpty;

	@Override
	@Transactional
	public void run(ApplicationArguments args) {
		if (serviceKey == null || serviceKey.isBlank()) {
			log.warn("API_PUBLIC_DATA_SERVICE_KEY not set - skipping pill data sync");
			return;
		}

		if (syncOnlyWhenEmpty && pillInfoRepository.count() > 0) {
			log.info("Pill data already exists ({} items) - skipping sync (sync-only-when-empty=true)", pillInfoRepository.count());
			return;
		}

		log.info("Starting pill identification data sync");
		long start = System.currentTimeMillis();

		if (!syncOnlyWhenEmpty) {
			pillInfoRepository.deleteAllInBatch();
		}

		int totalSaved = 0;
		int pageNo = 1;

		try {
			while (true) {
				var response = publicDataPillApiClient.search(serviceKey, pageNo, pageSize, responseType, null, null, null);
				List<PublicDataPillItem> items = response != null ? response.getItems() : List.of();

				if (items.isEmpty()) {
					log.debug("Page {} empty, sync complete", pageNo);
					break;
				}

				List<PillInfo> entities = items.stream()
					.map(this::toPillInfo)
					.toList();

				pillInfoRepository.saveAll(entities);
				totalSaved += entities.size();

				Integer totalCount = response.body() != null ? response.body().totalCount() : null;

				if (totalCount != null && totalCount > 0) {
					int percent = (int) ((double) totalSaved / totalCount * 100);
					log.info("[Sync progress] Page {} | Batch {} | Total {} / {} ({}%)", pageNo, entities.size(), totalSaved, totalCount, percent);
				} else {
					log.info("[Sync progress] Page {} | Batch {} | Total {}", pageNo, entities.size(), totalSaved);
				}

				if (totalCount != null && totalCount > 0 && totalSaved >= totalCount) {
					break;
				}

				if (items.size() < pageSize) {
					break;
				}

				pageNo++;
			}

			log.info("Pill identification data sync complete. Total {} items ({}ms)", totalSaved, System.currentTimeMillis() - start);
		} catch (Exception e) {
			log.error("Error during pill data sync (saved {} items so far)", totalSaved, e);
			throw new RuntimeException("Pill data sync failed", e);
		}
	}

	private PillInfo toPillInfo(PublicDataPillItem item) {
		return PillInfo.builder()
			.itemSeq(item.itemSeq())
			.itemName(item.itemName())
			.entpSeq(item.entpSeq())
			.entpName(item.entpName())
			.chart(item.chart())
			.itemImage(item.itemImage())
			.printFront(item.printFront())
			.printBack(item.printBack())
			.drugShape(item.drugShape())
			.colorClass(item.colorClass())
			.colorClass2(item.colorClass2())
			.lineFront(item.lineFront())
			.lineBack(item.lineBack())
			.lengLong(item.lengLong())
			.lengShort(item.lengShort())
			.thick(item.thick())
			.imgRegistTs(item.imgRegistTs())
			.classNo(item.classNo())
			.className(item.className())
			.etcOtcName(item.etcOtcName())
			.itemPermitDate(item.itemPermitDate())
			.formCodeName(item.formCodeName())
			.markCodeFrontAnal(item.markCodeFrontAnal())
			.markCodeBackAnal(item.markCodeBackAnal())
			.markCodeFrontImg(item.markCodeFrontImg())
			.markCodeBackImg(item.markCodeBackImg())
			.itemEngName(item.itemEngName())
			.changeDate(item.changeDate())
			.markCodeFront(item.markCodeFront())
			.markCodeBack(item.markCodeBack())
			.ediCode(item.ediCode())
			.bizrno(item.bizrno())
			.stdCd(item.stdCd())
			.efcyQesitm(item.efcyQesitm())
			.useMethodQesitm(item.useMethodQesitm())
			.atpnQesitm(item.atpnQesitm())
			.build();
	}
}
