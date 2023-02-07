package com.cycle.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cycle.dao.UserRepository;
import com.cycle.entities.User;
import com.cycle.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String home(Model model, Principal principal) {

		System.out.print("Home method  called");
		User user = null;

		if (principal != null)
			user = userRepository.getUserByUserName(principal.getName());
		else
			System.out.print("no session");

		model.addAttribute("user", user);

		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About grab a cycle");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model, Principal principal) {

		User user = null;

		if (principal != null)
			user = userRepository.getUserByUserName(principal.getName());
		else
			System.out.print("no session");

		model.addAttribute("user", user);

		model.addAttribute("title", "Register- About grab a cycle");
		model.addAttribute("user1", new User());

		return "signup";
	}

	// handler for registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, BindingResult result1, Model model,
			HttpSession session) {
		try {

			if (result1.hasErrors()) {

				System.out.println("error " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_BUYER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Registered successfully ", "alert-success"));

			session.setMaxInactiveInterval(150);
			return "login";

		} catch (Exception e) {

			e.printStackTrace();

			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something went wrong..!! " + e.getMessage(), "alert-danger"));

			return "signup";
		}

	}

	// handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title", "Login Page");
		return "login";
	}

}
