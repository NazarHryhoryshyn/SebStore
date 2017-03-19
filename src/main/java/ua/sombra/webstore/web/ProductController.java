package ua.sombra.webstore.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

@Controller
public class ProductController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = { "/products/{categoryName}-{page}", }, method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("categoryName") String categoryName
			, @PathVariable("page") int page) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		List<String> catNames = new ArrayList<String>();
		List<String> categoryTree = new ArrayList<String>();
		Set<Product> catProducts = new TreeSet<Product>();
		Set<Product> pageProducts = new TreeSet<Product>();
		Map<Integer, Integer> photos = new HashMap<Integer, Integer>();
		
		for(Category c : categoryService.listTopCategories()){
			catNames.add(c.getName());
		}
		
		if(categoryName.equals("All category")){
			for(Product p : productService.listProducts()){
				catProducts.add(p);
			}
		} else {
			Category cat = categoryService.findByName(categoryName);
			catProducts = categoryService.productsTreeFromCategory(cat);
			
			categoryTree = categoryService.categoriesTreeToTop(cat.getId());
			
			if(!categoryTree.contains(categoryName)){
				categoryTree.add(categoryName);
			}

			List<String> subCatNames = new ArrayList<String>();
			List<String> addingItems = new ArrayList<String>();
			
			for(String catName : categoryTree){
				subCatNames.clear();
				addingItems.clear();
				 for(Category c : categoryService.listSubCategories(categoryService.findByName(catName).getId())){
					 subCatNames.add(c.getName());
				 }
				 if(subCatNames.size() > 0){
					 addingItems.add("topSeparator");
					 for(int j = 0; j < subCatNames.size(); j++){
						 addingItems.add(subCatNames.get(j));
					 }
					 addingItems.add("bottomSeparator");
					 int indexInsert = catNames.indexOf(catName);
					 List<String> newCatNames = new ArrayList<String>();
					 int ind = 0;
					 for(; ind <= indexInsert; ind++){
						 newCatNames.add(catNames.get(ind));
					 }
					 newCatNames.addAll(addingItems);
					 for(; ind < catNames.size(); ind++){
						 newCatNames.add(catNames.get(ind));
					 }
					 catNames = newCatNames;
				 }
			}
		}
		
		PageMaker<Product> pgMaker = new PageMaker<Product>();
		pgMaker.setObjects(catProducts);

		int totalPages = pgMaker.totalPages();
		int block = pgMaker.getBlock(page);
		
		pageProducts = pgMaker.getFromPage(page);

		for(Product p : pageProducts){
			photos.put(p.getId(), p.getPhotos().iterator().next().getId());
		}
		
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("categories", catNames);
		model.addAttribute("currentCategory", categoryName);
		model.addAttribute("products", pageProducts);
		model.addAttribute("photos", photos);
		model.addAttribute("amountProducts", pageProducts.size());
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("block", block);
		
		return "products";
	}
	
	@RequestMapping(value = { "/product/{id}", }, method = RequestMethod.GET)
	public String productInfo(Model model, @PathVariable("id") int productId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		
		Product p = productService.findById(productId);
		Set<Integer> photos = new HashSet<Integer>();
		
		for(Photo ph : p.getPhotos()){
			photos.add(ph.getId());
		}

		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("product", p);
		model.addAttribute("photos", photos);
		model.addAttribute("amountPhotos", photos.size());
		
		return "product";
	}
	
	@RequestMapping(value = "/product/addToCart", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProductToCart(@RequestParam("productId") int productId){
		userService.addReferenceToProduct(userService.findByLogin(securityService.findLoggedInLogin()).getId(), productId);
	}
}