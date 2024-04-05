package com.pta.controller.admin;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pta.entities.Publisher;
import com.pta.service.PublisherService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({"admin/publisher", "admin/publisher/" })
public class PublisherAdminController {

	@Autowired
	private PublisherService publisherService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("publishers", publisherService.findAll());
		return "admin/publisher/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Publisher publisher = new Publisher();
		modelMap.put("publisher", publisher);
		return "admin/publisher/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
		try {
			LocalDateTime now = LocalDateTime.now();
			Date createdDate = Date.from(now.toInstant(ZoneOffset.UTC));
			publisher.setCreatedTime(createdDate);
			
			if (publisherService.save(publisher)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/publisher/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (publisherService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/publisher/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", publisherService.find(id));
		return "admin/publisher/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
		try {
			LocalDateTime now = LocalDateTime.now();
			Date updatedTime = Date.from(now.toInstant(ZoneOffset.UTC));
			publisher.setUpdatedTime(updatedTime);
			
			if (publisherService.save(publisher)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/publisher/index";
	}

}
