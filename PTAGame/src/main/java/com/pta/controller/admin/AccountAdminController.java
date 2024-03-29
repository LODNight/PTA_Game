package com.pta.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pta.entities.Account;
import com.pta.service.AccountService;
import com.pta.service.RoleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "", "/" })
public class AccountAdminController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		// modelMap.put("accounts", accountService.findAll());
		return "admin/account/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "admin/account/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			
			account.setPassword(encoder.encode(account.getPassword()));

			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/account/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (accountService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/account/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", accountService.find(id));
		modelMap.put("roles", roleService.findAll());
		return "admin/account/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/account/index";
	}

	// ----------- LOGIN
	

	// ------------------ Change PassWord
	// Update Password
	@GetMapping({ "updatePassword/{id}" })
	public String updatePassword(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		Account account = accountService.find(id);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
		} else {
			modelMap.put("account", account);
			return "admin/account/updatePassword";
		}
		return "redirect:/admin/account/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {

		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
			return "redirect:/admin/account/updatePassword/" + account.getId();
		}

		String hashedCurrentPassword = accountService.findPassword(account.getId());
		
		if (!encoder.matches(currentPassword, hashedCurrentPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Current password is incorrect");
			return "redirect:/admin/account/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
			return "redirect:/admin/account/updatePassword/" + account.getId();
		}
		account.setPassword(encoder.encode(newPassword));
		redirectAttributes.addFlashAttribute("msg",
				accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/admin/account/index";
	}

}
