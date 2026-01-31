package com.mediscan.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity for pill identification data (synced from MFDS public API).
 */
@Entity
@Table(name = "pill_info", indexes = {
	@Index(name = "idx_pill_print", columnList = "printFront"),
	@Index(name = "idx_pill_shape", columnList = "drugShape"),
	@Index(name = "idx_pill_color", columnList = "colorClass")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PillInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String itemSeq;

	@Column(nullable = false, length = 500)
	private String itemName;

	@Column(length = 50)
	private String entpSeq;

	@Column(length = 500)
	private String entpName;

	@Column(length = 500)
	private String chart;

	@Column(length = 1000)
	private String itemImage;

	@Column(length = 200)
	private String printFront;

	@Column(length = 100)
	private String printBack;

	@Column(length = 100)
	private String drugShape;

	@Column(length = 100)
	private String colorClass;

	@Column(length = 100)
	private String colorClass2;

	@Column(length = 100)
	private String lineFront;

	@Column(length = 100)
	private String lineBack;

	@Column(length = 50)
	private String lengLong;

	@Column(length = 50)
	private String lengShort;

	@Column(length = 50)
	private String thick;

	@Column(length = 20)
	private String imgRegistTs;

	@Column(length = 50)
	private String classNo;

	@Column(length = 200)
	private String className;

	@Column(length = 100)
	private String etcOtcName;

	@Column(length = 20)
	private String itemPermitDate;

	@Column(length = 100)
	private String formCodeName;

	@Column(length = 500)
	private String markCodeFrontAnal;

	@Column(length = 500)
	private String markCodeBackAnal;

	@Column(length = 500)
	private String markCodeFrontImg;

	@Column(length = 500)
	private String markCodeBackImg;

	@Column(length = 500)
	private String itemEngName;

	@Column(length = 20)
	private String changeDate;

	@Column(length = 100)
	private String markCodeFront;

	@Column(length = 100)
	private String markCodeBack;

	@Column(length = 50)
	private String ediCode;

	@Column(length = 50)
	private String bizrno;

	@Column(length = 500)
	private String stdCd;

	@Column(length = 4000)
	private String efcyQesitm;

	@Column(length = 4000)
	private String useMethodQesitm;

	@Column(length = 4000)
	private String atpnQesitm;
}
