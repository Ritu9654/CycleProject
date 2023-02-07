package com.cycle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycle.entities.Cycle;
import com.cycle.entities.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
