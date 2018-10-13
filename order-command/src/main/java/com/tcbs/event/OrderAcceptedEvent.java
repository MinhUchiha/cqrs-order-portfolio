package com.tcbs.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderAcceptedEvent {

	private String id;
	private String userId;
	private String action;
	private String product;
	private Double quantity;
}