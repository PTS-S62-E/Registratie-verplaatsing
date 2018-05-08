package services;

import dao.CategoryDao;
import entities.Category;
import exceptions.CategoryException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CategoryServiceImpl implements CategoryService {

	@Inject
	CategoryDao categoryDao;

	@Override
	public List<Category> getCategories() {
		return categoryDao.getCategories();
	}

	@Override
	public void createCategory(Category category) throws CategoryException {
		if (checkIfCategoryExists(category.getName())){
			StringBuilder builder = new StringBuilder();
			builder.append("Category: ");
			builder.append(category.getName());
			builder.append(" already exists.");
			throw new CategoryException(builder.toString());
		}

		category.setName(category.getName().toUpperCase());
		categoryDao.createCategory(category);
	}

	@Override
	public boolean checkIfCategoryExists(String categoryName) {
		List<Category> categories = categoryDao.getCategories();

		for(Category c : categories){
			if(c.getName().equals(categoryName)){
				return true;
			}
		}
		return false;
	}
}
