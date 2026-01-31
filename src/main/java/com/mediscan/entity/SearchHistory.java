package com.mediscan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Search history entity: stores keyword, response code, result count for analytics.
 */
@Entity
@Table(name = "search_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SearchHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 500)
	private String searchKeyword;

	@Column(nullable = false)
	private Instant searchedAt;

	@Column(length = 10)
	private String responseCode;

	private Integer resultCount;
}
