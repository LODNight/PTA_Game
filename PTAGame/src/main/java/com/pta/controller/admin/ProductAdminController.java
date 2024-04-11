package com.pta.controller.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pta.entities.Product;
import com.pta.helpers.FileHelper;
import com.pta.service.DeveloperService;
import com.pta.service.ProductService;
import com.pta.service.PublisherService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "admin/product", "admin/product/" })
public class ProductAdminController {

	@Autowired
	private ProductService productService;
	@Autowired
	private DeveloperService developerService;
	@Autowired
	private PublisherService publisherService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		try {
			Path folderPath = Paths.get("src/main/resources/static/images/product");
			if (!Files.exists(folderPath)) {
				Files.createDirectories(folderPath);
			}

			modelMap.put("products", productService.findAll());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "admin/product/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Product product = new Product();

		modelMap.put("publishers", publisherService.findAll());
		modelMap.put("developers", developerService.findAll());
		modelMap.put("product", product);

		return "admin/product/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			// Add Created Time
			LocalDateTime now = LocalDateTime.now();
			Date createdDate = Date.from(now.toInstant(ZoneOffset.UTC));
			product.setCreatedTime(createdDate);

			// ADD Image to Product
			if (file != null && !file.isEmpty()) {
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path imagePath = Paths.get("src/main/resources/static/images/product/" + fileName);

				Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
				product.setImage(fileName);
			} else {
				product.setImage("noimage.png");
			}

			// Save Product
			if (productService.save(product)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
				return "redirect:/admin/product/index";
			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/product/add";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (productService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/product/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("publishers", publisherService.findAll());
		modelMap.put("developers", developerService.findAll());
		modelMap.put("product", productService.find(id));
		return "admin/product/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			// Add Created Time
			LocalDateTime now = LocalDateTime.now();
			Date updatedDate = Date.from(now.toInstant(ZoneOffset.UTC));
			product.setUpdatedTime(updatedDate);

			// ADD Image to Product
			if (file != null && !file.isEmpty()) {
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path imagePath = Paths.get("src/main/resources/static/images/product/" + fileName);

				Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
				product.setImage(fileName);
			}

			else {
				if (productService.find(product.getId()).getImage() != null) {
					product.setImage(productService.find(product.getId()).getImage());
				} else {
					product.setImage("noimage.png");					
				}
			}

			if (productService.save(product)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/product/index";
	}

}
