package services;

import entities.Category;
import exceptions.CategoryException;

public interface CategoryService {
	void createCategory(Category category) throws CategoryException;
	boolean checkIfCategoryExists(String categoryName) throws CategoryException;
}
