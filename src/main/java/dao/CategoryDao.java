package dao;

import entities.Category;
import java.util.List;

public interface CategoryDao {
	List<Category> getCategories();
	void createCategory(Category category);
}
