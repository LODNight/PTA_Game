package com.pta.controller.admin;

import java.time.LocalDateTime;
import java.time.ZoneOffset; 
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pta.entities.Account;
import com.pta.entities.Role;
import com.pta.helpers.SecurityCodeHelper;
import com.pta.service.AccountService;
import com.pta.service.RoleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "admin/auth", "admin/auth/" })
public class AuthAdminController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	// ----------- LOGIN
	@GetMapping({ "login", "login/", "", "/" })
	public String login(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "admin/auth/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap) {

		Account account = accountService.findByEmail(email);
		try {
			if (email == null || password == null) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/admin/auth/login";
			}

			if (!account.getStatus().equals(1)) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/admin/auth/login";
			}

			if (accountService.login(email, password) == null) {
				redirectAttributes.addFlashAttribute("msg", "Cant Login Failed");
				return "redirect:/admin/auth/login";
			}

			if (!encoder.matches(password, account.getPassword())) {
				redirectAttributes.addFlashAttribute("msg", "Password Not Match");
				return "redirect:/admin/auth/login";
			}

			session.setAttribute("email", email);
			modelMap.put("accounts", accountService.findAll());
			return "admin/account/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/admin/auth/login";
		}
	}

	// ------ Register // For User
	@GetMapping({ "register" })
	public String register(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "admin/auth/register";
	}

	@PostMapping({ "register" })
	public String register(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		try {
			account.setStatus(0);
			account.setRole(new Role(3, "ROLE_MEMBER"));

			// Genereate Code
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);

			if (confirmPassword.matches(newPassword)) {
				account.setPassword(encoder.encode(newPassword));

				LocalDateTime now = LocalDateTime.now();
				Date createdDate = Date.from(now.toInstant(ZoneOffset.UTC));
				account.setCreatedTime(createdDate);

				if (!confirmPassword.equals(newPassword)) {
					redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
					return "redirect:/admin/auth/updatePassword/" + account.getId();
				}

				if (accountService.save(account)) {

					redirectAttributes.addFlashAttribute("msg", "Regsiter Success");
					return "redirect:/admin/auth/login";
				} else {
					redirectAttributes.addFlashAttribute("msg", "Register Failed");
				}
			} else {
				redirectAttributes.addFlashAttribute("msg", "Password not match");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/auth/register";
	}

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
			return "admin/auth/updatePassword";
		}
		return "redirect:/admin/auth/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {

		if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
			return "redirect:/admin/auth/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
			return "redirect:/admin/auth/updatePassword/" + account.getId();
		}

		account.setPassword(encoder.encode(newPassword));
		redirectAttributes.addFlashAttribute("msg",
				accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/admin/auth/login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.removeAttribute("email");
		redirectAttributes.addFlashAttribute("msg", "Logout Success");
		return "redirect:/admin/auth/login";
	}

}
