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

import com.pta.entities.Developer;
import com.pta.service.DeveloperService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({"admin/developer", "admin/developer/" })
public class DeveloperAdminController {

	@Autowired
	private DeveloperService developerService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {

		modelMap.put("developers", developerService.findAll());
		return "admin/developer/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Developer developer = new Developer();
		modelMap.put("developer", developer);
		return "admin/developer/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("developer") Developer developer, RedirectAttributes redirectAttributes) {
		try {
			
			LocalDateTime now = LocalDateTime.now();
			Date createdDate = Date.from(now.toInstant(ZoneOffset.UTC));
			developer.setCreatedTime(createdDate);
			
			if (developerService.save(developer)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/developer/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (developerService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/developer/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("developer", developerService.find(id));
		return "admin/developer/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("developer") Developer developer, RedirectAttributes redirectAttributes) {
		try {
			LocalDateTime now = LocalDateTime.now();
			Date updatedTime = Date.from(now.toInstant(ZoneOffset.UTC));
			developer.setUpdatedTime(updatedTime);
			
			if (developerService.save(developer)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/developer/index";
	}

}
