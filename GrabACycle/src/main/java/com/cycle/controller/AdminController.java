
package com.cycle.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cycle.dao.CycleRepository;
import com.cycle.dao.UserRepository;
import com.cycle.entities.Cycle;
import com.cycle.entities.User;
import com.cycle.helper.Message;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CycleRepository cycleRepository;

//	 method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		User user = null;

		if (principal != null)
			user = userRepository.getUserByUserName(principal.getName());
		else
			System.out.print("no session");

		model.addAttribute("user", user);

	}

	// dashboard home
	@RequestMapping("/index/{page}")
	public String dashboard(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "All cycles");

		Pageable pageable = PageRequest.of(page, 2);

		Page<Cycle> cycles = this.cycleRepository.findAllCycle(pageable);
		m.addAttribute("cycles", cycles);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", cycles.getTotalPages());

		return "admin/adminViewCycle";
	}

//	view all bookings
	@RequestMapping("/viewAllBookings/{page}")
	public String viewAllBookings(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "All Bookings");

		Pageable pageable = PageRequest.of(page, 8);

		Page<Cycle> cycles = this.cycleRepository.findAllBookings(pageable);
		m.addAttribute("cycles", cycles);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", cycles.getTotalPages());

		return "admin/ViewAllBookings";
	}

	@GetMapping("/add-cycle")
	public String openAddcycleForm(Model model) {
		model.addAttribute("title", "Add Cycle");
		model.addAttribute("cycle", new Cycle());

		return "admin/add_cycle_form";
	}

	@PostMapping("/process-cycle")
	public String processCycle(Cycle cycle, @RequestParam("profileImage") MultipartFile file, Principal principal,
			HttpSession session) {
		System.out.println(cycle);

		try {

			if (file.isEmpty()) {
				// if the file is empty then try our message
				System.out.println("File is empty");
				cycle.setImageurl("cyclelogo.png");

			} else {
				// file the file to folder and update the name to contact
				cycle.setImageurl(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("Image is uploaded");

			}

			cycleRepository.save(cycle);

			// message success.......
			session.setAttribute("message", new Message("Cycle added !! ", "success"));

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "danger"));

		}

		return "redirect:/admin/index/0";

	}

	// delete cycle handler

	@GetMapping("/delete/{cid}")
	@Transactional
	public String deleteCycle(@PathVariable("cid") Integer cid, Model model, HttpSession session, Principal principal) {

		Optional<Cycle> cycle = cycleRepository.findById(cid);

		cycleRepository.delete(cycle.get());

		System.out.println("DELETED");
		session.setAttribute("message", new Message("Contact deleted succesfully...", "success"));

		return "redirect:/admin/index/0";
	}

	// open cycle update form handler
	@PostMapping("/update-cycle/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid, Model m) {

		m.addAttribute("title", "Update Cycle");

		Cycle cycle = this.cycleRepository.findById(cid).get();

		m.addAttribute("cycle", cycle);

		return "admin/update_form";
	}

	// update cycle handler
	@RequestMapping(value = "/process-update", method = RequestMethod.POST)
	public String updateHandler(@ModelAttribute Cycle cycle, @RequestParam("profileImage") MultipartFile file, Model m,
			HttpSession session, Principal principal) {

		try {

			// old contact details
			Cycle oldcycleDetail = this.cycleRepository.findById(cycle.getCid()).get();

			// image..
			if (!file.isEmpty()) {
				// file work..
				// rewrite

//					delete old photo

				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldcycleDetail.getImageurl());
				file1.delete();

//					update new photo

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				cycle.setImageurl(file.getOriginalFilename());

			} else {
				cycle.setImageurl(oldcycleDetail.getImageurl());
			}

			this.cycleRepository.save(cycle);

			session.setAttribute("message", new Message("Your contact is updated...", "success"));
//			session.setMaxInactiveInterval(15);

		} catch (Exception e) {
			e.printStackTrace();
		}

//		System.out.println("Cycle name " + cycle.getName());
//		System.out.println("cycle id " + cycle.getCid());

		return "redirect:/admin/index/0";

	}

}
