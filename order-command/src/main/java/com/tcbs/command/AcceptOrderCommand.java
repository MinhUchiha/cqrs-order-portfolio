package com.tcbs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class AcceptOrderCommand {

	@TargetAggregateIdentifier
	private String id;
	private String userId;
	private String action;
	private String product;
	private Double quantity;
}