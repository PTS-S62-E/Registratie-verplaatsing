package services;

import entities.Category;
import exceptions.CategoryException;

import java.util.List;

public interface CategoryService {
	Category getCategory(String name) throws CategoryException;
	List<Category> getCategories();
	void createCategory(Category category) throws CategoryException;
}
