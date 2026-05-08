package ch.schneider.michael.myVault.category;

import ch.schneider.michael.myVault.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // POST /api/category
    @PostMapping
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Category> newCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.insertCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // GET /api/category
    @GetMapping
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Category>> all() {
        List<Category> result = categoryService.getCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // PUT /api/category/{id}
    @PutMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(category, id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // DELETE /api/category/{id}
    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
