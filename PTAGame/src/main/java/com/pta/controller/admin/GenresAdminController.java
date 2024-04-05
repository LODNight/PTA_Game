package com.pta.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pta.entities.Genres;
import com.pta.service.GenresService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({"admin/genres", "admin/genres/" })
public class GenresAdminController {

	@Autowired
	private GenresService genresService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {

		modelMap.put("genres", genresService.findAll());
		return "admin/genres/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Genres genres = new Genres();
		modelMap.put("genres", genres);
		return "admin/genres/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("genres") Genres genres, RedirectAttributes redirectAttributes) {
		try {
			if (genresService.save(genres)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/genres/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (genresService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/genres/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", genresService.find(id));
		return "admin/genres/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("genres") Genres genres, RedirectAttributes redirectAttributes) {
		try {

			if (genresService.save(genres)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/genres/index";
	}

}
