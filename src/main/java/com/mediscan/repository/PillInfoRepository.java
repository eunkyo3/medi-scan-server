package com.mediscan.repository;

import com.mediscan.entity.PillInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PillInfoRepository extends JpaRepository<PillInfo, Long> {

	@Query("""
		SELECT p FROM PillInfo p WHERE
		(:print IS NULL OR :print = '' OR
		 (p.printFront IS NOT NULL AND LOWER(p.printFront) LIKE LOWER(CONCAT('%', :print, '%')))
		 OR (p.itemName IS NOT NULL AND LOWER(p.itemName) LIKE LOWER(CONCAT('%', :print, '%'))))
		AND (:shape IS NULL OR :shape = '' OR (p.drugShape IS NOT NULL AND LOWER(p.drugShape) LIKE LOWER(CONCAT('%', :shape, '%'))))
		AND (:color IS NULL OR :color = '' OR (p.colorClass IS NOT NULL AND LOWER(p.colorClass) LIKE LOWER(CONCAT('%', :color, '%'))))
		""")
	List<PillInfo> searchByPrintShapeColor(String print, String shape, String color);
}
