package com.cycle.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cycle.entities.Cycle;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Integer> {


	@Query("from Orders o join o.cycle c where o.user.uid=:userId")

	public Page<Cycle> findCyclesByUser(@Param("userId") int userId, Pageable pePageable);
	
//	@Query(value="select * from orders join cycle_details on cycle_details.cid=orders.cycle_cid where orders.user_uid=userId", nativeQuery=true)
//	public Page<Cycle> findCyclesByUser(@Param("userId") int userId, Pageable pePageable);
	
	@Query("select c from Cycle c")
	public Page<Cycle> findAllCycle(Pageable pePageable);

	@Query("from Orders o join o.user u join o.cycle c ")
	public Page<Cycle> findAllBookings(Pageable pePageable);

	// search
	public List<Cycle> findByNameContaining(String name);

	// search for specific booking


}
