package Nhom09_WebNgheNhac.Nhom09_WebNgheNhac.Controller;

import Nhom09_WebNgheNhac.Nhom09_WebNgheNhac.Model.Category;
import Nhom09_WebNgheNhac.Nhom09_WebNgheNhac.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryService.getAlCatologies());
        return "/category/list-category";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "/category/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@Valid Category category, BindingResult result, MultipartFile imageFile) throws IOException {
        if (result.hasErrors()) {
            return "/category/add-category";
        }
        category.setImage(categoryService.saveImage(imageFile));
        categoryService.addCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/edit/{categoryId}")
    public String showUpdateForm(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        model.addAttribute("category", category);
        return "/category/update-category";
    }
    @PostMapping("/edit/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") int categoryId, @Valid Category
            category, BindingResult result, Model model,MultipartFile imageFile) throws IOException {
        if (result.hasErrors()) {
            category.setCategoryId(Math.toIntExact(categoryId));
            return "/category/update-category";
        }
        category.setImage(categoryService.saveImage(imageFile));
        categoryService.updateCategory(category);
        model.addAttribute("categories", categoryService.getAlCatologies());
        return "redirect:/category";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        categoryService.deleteCategoryById(categoryId);
        model.addAttribute("categories", categoryService.getAlCatologies());
        return "redirect:/category";
    }
}
