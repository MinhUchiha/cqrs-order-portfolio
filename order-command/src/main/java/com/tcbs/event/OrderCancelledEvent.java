package com.tcbs.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderCancelledEvent {

	private String id;
	private String userId;
	private String action;
	private String product;
	private Double quantity;
}