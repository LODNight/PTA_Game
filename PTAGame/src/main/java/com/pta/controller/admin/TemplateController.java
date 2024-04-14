package com.pta.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "template" })
public class TemplateController {

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {

		return "layout/main";
	}
	
	@GetMapping({ "index1" })
	public String test2(ModelMap modelMap) {

		return "layout/index1";
	}



}
