package com.cycle.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cycle.dao.CycleRepository;
import com.cycle.dao.UserRepository;
import com.cycle.entities.Cycle;
import com.cycle.entities.User;

@Controller
public class CycleController {

	@Autowired
	private CycleRepository cycleRepository;

	@Autowired
	private UserRepository userRepository;

	// showing particular contact details.

	@RequestMapping("/{cid}/cycle")
	public String showCycleDetail(@PathVariable("cid") Integer cid, Model model, Principal principal) {
		System.out.println("CID " + cid);

		Optional<Cycle> contactOptional = this.cycleRepository.findById(cid);
		Cycle cycle = contactOptional.get();

		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		model.addAttribute("cycle", cycle);
		model.addAttribute("title", cycle.getName());
		model.addAttribute("user", user);

		return "/particularCycleDetail";
	}

}
