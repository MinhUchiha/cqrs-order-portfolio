package com.tcbs.components;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.tcbs.event.OrderAcceptedEvent;
import com.tcbs.event.OrderCancelledEvent;
import com.tcbs.exception.PortfolioNotFoundException;
import com.tcbs.model.PortfolioItem;
import com.tcbs.repository.PortfolioItemRepository;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
@ProcessingGroup("amqpEvents")
public class OrderAMQPEventProcessor {

    private final PortfolioItemRepository repository;

    @EventHandler
    public void on(OrderAcceptedEvent event) {
        PortfolioItem latestItem = repository.findFirstByUserIdAndProductOrderByUpdatedDateDesc(
            event.getUserId(),
            event.getProduct()
        );

        Double delta = event.getAction().equals("BUY")
            ?event.getQuantity()
            :((-1)*event.getQuantity());

        PortfolioItem newItem = null;
        if(latestItem != null) {
            newItem = latestItem;
            newItem.setBalance(latestItem.getBalance() + delta);
            newItem.setDelta(delta);
            newItem.setId(UUID.randomUUID().toString());
            newItem.setUpdatedDate(new Date());
        } else {
            newItem = new PortfolioItem(
                UUID.randomUUID().toString(),
                event.getUserId(),
                event.getProduct(),
                delta,
                delta,
                new Date()
            );
        }

        PortfolioItem item = repository.save(newItem);
        log.info("A portfolio item was added! {}", item );
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        PortfolioItem latestItem = repository.findFirstByUserIdAndProductOrderByUpdatedDateDesc(
            event.getUserId(),
            event.getProduct()
        );

        if (latestItem != null) {
            Double revertedDelta = event.getAction().equals("BUY")
                ?(-1)*event.getQuantity()
                :event.getQuantity();

            PortfolioItem newItem = latestItem;
            newItem.setBalance(newItem.getBalance() + revertedDelta);
            newItem.setDelta(revertedDelta);
            newItem.setId(UUID.randomUUID().toString());
            newItem.setUpdatedDate(new Date());

            PortfolioItem item = repository.save(newItem);

            log.info("A portfolio was reverted! {}", item);
        } else throw new PortfolioNotFoundException();
    }
}
