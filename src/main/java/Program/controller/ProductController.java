package Program.controller;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import Program.entity.Category;
import Program.entity.Product;
import Program.service.ICategoryService;
import Program.service.IProductService;
import Program.utils.UploadFile;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private IProductService productService;
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping()
	public String findAll(Pageable pageable, Model model) {
		List<Product> products = productService.findAll(Pageable.unpaged()).toList();
		List<Category> categories = categoryService.findAll(Pageable.unpaged()).toList();
		model.addAttribute("products",products);
		model.addAttribute("categories",categories);
		model.addAttribute("addProduct", new Product());
		model.addAttribute("editProduct", new Product());
		return "manage_products";
	}
	
	@PostMapping()
	public String addProduct(@ModelAttribute("addProduct") Product product, 
							 @RequestParam(value = "addImage", required = false) MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			String productImage = UploadFile.uploadImage(file);
			product.setImage(productImage);
		}
		product.setStatus(true);
		productService.save(product);
		return "redirect:/products";
	}

	@GetMapping("/{id}")
	public String getProduct(@PathVariable(value = "id", required = false) String id, Model model) {
		if(!(id == null) && !id.isEmpty()) {
			Product product = productService.findById(Long.valueOf(id)).get();
			model.addAttribute("editProduct", product);
		}
		return "redirect:/products";
	}

	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable String id) {
		productService.deleteById(Long.valueOf(id));
		return "redirect:/products";
	}
}
