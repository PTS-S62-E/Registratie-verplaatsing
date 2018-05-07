package services;

import entities.Category;
import exceptions.CategoryException;

import java.util.List;

public interface CategoryService {
	List<Category> getCategories();
	void createCategory(Category category) throws CategoryException;
	boolean checkIfCategoryExists(String categoryName) throws CategoryException;
}
