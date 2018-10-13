package com.tcbs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private String id;
}
