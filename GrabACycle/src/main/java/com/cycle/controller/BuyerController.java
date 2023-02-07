package com.cycle.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cycle.dao.CycleRepository;
import com.cycle.dao.OrderRepository;
import com.cycle.dao.UserRepository;
import com.cycle.entities.Cycle;
import com.cycle.entities.Orders;
import com.cycle.entities.User;
import com.cycle.global.GlobalData;
import com.cycle.helper.Message;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CycleRepository cycleRepository;

//	 method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME " + userName);

		User user = userRepository.getUserByUserName(userName);
		System.out.println("USER " + user);
		model.addAttribute("user", user);

	}

	// buyer view all cycles
	// dashboard home

	@GetMapping("/dashboard/viewCycle/{page}")
	public String dashboard(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "All Bookings");

		Pageable pageable = PageRequest.of(page, 3);

		Page<Cycle> cycles = this.cycleRepository.findAllCycle(pageable);
		m.addAttribute("cycles", cycles);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", cycles.getTotalPages());

		m.addAttribute("cartCount", GlobalData.cart.size());

		return "buyer/buyerViewCycle";
	}

	// buyer viewing mybookings
	@GetMapping("/mybookings/{page}")
	public String myBookings(@PathVariable("page") Integer page, Model m, Principal principal) {

		try {

			m.addAttribute("title", "user bookings");
			// contact ki list ko bhejni hai

			String userName = principal.getName();
//			String userName="new@gmail.com";
			User user = this.userRepository.getUserByUserName(userName);

			// currentPage-page // Contact Per page - 5
			Pageable pageable = PageRequest.of(page, 8);
//
			Page<Cycle> cycles = this.cycleRepository.findCyclesByUser(user.getUid(), pageable);

			System.out.println("cycles = " + cycles);

//			Page<Cycle> cycles = this.cycleRepository.findCyclesByUser(user.getUid());
			m.addAttribute("cycles", cycles);
			m.addAttribute("currentPage", page);
			m.addAttribute("totalPages", cycles.getTotalPages());
			m.addAttribute("cartCount", GlobalData.cart.size());

			return "buyer/buyerbookings";

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			return "buyer/buyerbookings";
		}

	}

	// book cycle handler
	@GetMapping("/bookCycle")
	public String bookCycle(Model model, HttpSession session, Principal principal) {

		User user = this.userRepository.getUserByUserName(principal.getName());

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		List<Cycle> fcart = GlobalData.cart;

		try {

			for (Cycle cycle : fcart) {
				Orders order = new Orders();
				order.setUser(user);
				order.setOrderDate(date);
				order.setTotalAmount(cycle.getPrice());
				order.setQuantity(1);
				order.setCycle(cycle);

				Cycle cycle2 = this.cycleRepository.findById(cycle.getCid()).get();
				cycle2.setQuantity(cycle2.getQuantity() - 1);

				this.cycleRepository.save(cycle2);
				this.orderRepository.save(order);

			}

			System.out.println("Booked");
			session.setAttribute("message", new Message("Cycle booked succesfully...", "success"));

			GlobalData.cart.clear();
//			GlobalData.cart=null;
			model.addAttribute("cartCount", GlobalData.cart.size());
//			model.addAttribute("cartCount",0);
			return "home";

		} catch (Exception E) {

			model.addAttribute("cartCount", 0);

			session.setAttribute("message", new Message("Booking failed, Try again..!!", "danger"));
			return "home";
		}

	}

	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {

		Cycle cycle = this.cycleRepository.findById((id)).get();
		GlobalData.cart.add(cycle);

		return "redirect:/buyer/dashboard/viewCycle/0";
	}

	@GetMapping("/viewCart")
	public String getCart(Model model) {

		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Cycle::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "buyer/cart";
	}

}
