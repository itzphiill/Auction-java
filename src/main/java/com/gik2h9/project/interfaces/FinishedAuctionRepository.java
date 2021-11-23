package com.gik2h9.project.interfaces;


import com.gik2h9.project.models.FinishedAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishedAuctionRepository extends JpaRepository <FinishedAuction, Integer> {

}