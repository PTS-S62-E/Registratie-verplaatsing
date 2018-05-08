package dao;

import entities.Category;
import java.util.List;

public interface CategoryDao {
	List<Category> getCategories();
	Category getCategory(String name);
	void createCategory(Category category);
}
