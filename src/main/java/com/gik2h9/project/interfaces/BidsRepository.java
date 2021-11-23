package com.gik2h9.project.interfaces;


import com.gik2h9.project.models.Bid;
import com.gik2h9.project.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidsRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findAllByItemOrderByPrice(Item item);

    List<Bid> findTop3ByItemOrderByPriceDesc(Item item);

}
