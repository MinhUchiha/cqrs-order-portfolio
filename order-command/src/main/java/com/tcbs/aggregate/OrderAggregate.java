package com.tcbs.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import com.tcbs.command.AcceptOrderCommand;
import com.tcbs.command.CancelOrderCommand;
import com.tcbs.event.OrderAcceptedEvent;
import com.tcbs.event.OrderCancelledEvent;

@Slf4j
@Getter
@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String id;
	private String userId;
	private String action;
	private String product;
    private Double quantity;
    private String status;

    @CommandHandler
    public OrderAggregate(AcceptOrderCommand cmd) {
        log.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Id should not be empty or null.");
        Assert.hasLength(cmd.getUserId(), "UserId should not be empty or null.");
        Assert.hasLength(cmd.getProduct(), "Product should not be empty or null.");
        Assert.notNull(cmd.getQuantity(), "Quantity should not be null.");

        apply(new OrderAcceptedEvent(
            cmd.getId(), 
            cmd.getUserId(),
            cmd.getAction(), 
            cmd.getProduct(), 
            cmd.getQuantity()));
        log.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
    }

    @CommandHandler
    public void handle(CancelOrderCommand cmd) {
        log.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Id should not be empty or null.");

        apply(new OrderCancelledEvent(
                cmd.getId(),
                this.userId,
                this.action,
                this.product,
                this.quantity
            ));
        log.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
    }

    @EventSourcingHandler
    public void on(OrderAcceptedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.id = event.getId();
        this.userId = event.getUserId();
        this.action = event.getAction();
        this.product = event.getProduct();
        this.quantity = event.getQuantity();
        this.status = "ACCEPTED";
        log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = "CANCELLED";
        log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }
}