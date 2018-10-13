package com.tcbs.repository;

import com.tcbs.model.PortfolioItem;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, String> {
    PortfolioItem findFirstByUserIdAndProductOrderByUpdatedDateDesc(
        String userId,
        String product
    );
}
