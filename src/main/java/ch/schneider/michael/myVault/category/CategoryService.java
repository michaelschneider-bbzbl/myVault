package ch.schneider.michael.myVault.category;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getCategories() {
        return repository.findByOrderByNameAsc();
    }

    public Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category " + id + " not found"));
    }

    public Category insertCategory(Category category) {
        return repository.save(category);
    }

    public Category updateCategory(Category category, Long id) {
        return repository.findById(id)
                .map(categoryOrig -> {
                    categoryOrig.setName(category.getName());
                    return repository.save(categoryOrig);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    return repository.save(category);
                });
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
