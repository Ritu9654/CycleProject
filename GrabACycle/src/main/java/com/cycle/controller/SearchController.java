package com.cycle.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cycle.dao.CycleRepository;
import com.cycle.dao.UserRepository;
import com.cycle.entities.Cycle;

@RestController
@Controller
public class SearchController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CycleRepository cycleRepository;

	// search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
		System.out.println(query);
//		User user=this.userRepository.getUserByUserName(principal.getName());		
		List<Cycle> cycle = this.cycleRepository.findByNameContaining(query);
		return ResponseEntity.ok(cycle);
	}

}
