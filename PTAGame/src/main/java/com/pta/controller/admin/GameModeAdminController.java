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

import com.pta.entities.GameMode;
import com.pta.service.GameModeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({"admin/gamemode", "admin/gamemode/" })
public class GameModeAdminController {

	@Autowired
	private GameModeService gamemodeService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {

		modelMap.put("gamemodes", gamemodeService.findAll());
		return "admin/gamemode/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		GameMode gamemode = new GameMode();
		modelMap.put("gamemode", gamemode);
		return "admin/gamemode/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("gamemode") GameMode gamemode, RedirectAttributes redirectAttributes) {
		try {
			if (gamemodeService.save(gamemode)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/gamemode/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (gamemodeService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/genres/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("gamemode", gamemodeService.find(id));
		return "admin/gamemode/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("gamemode") GameMode gamemode, RedirectAttributes redirectAttributes) {
		try {

			if (gamemodeService.save(gamemode)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/gamemode/index";
	}

}
