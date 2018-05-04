package services;

import dao.CategoryDao;
import entities.Category;
import exceptions.CategoryException;
import exceptions.VehicleException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CategoryServiceImpl implements CategoryService {

	@Inject
	CategoryDao categoryDao;

	@Override
	public void createCategory(Category category) throws CategoryException {
		if (checkIfCategoryExists(category.getCategoryName())){
			StringBuilder builder = new StringBuilder();
			builder.append("Category: ");
			builder.append(category.getCategoryName());
			builder.append(" already exists.");
		}

		category.setCategoryName(category.getCategoryName().toUpperCase());
		categoryDao.createCategory(category);
	}

	@Override
	public boolean checkIfCategoryExists(String category) throws CategoryException {
		List<Category> categories = categoryDao.getCategories();

		for(Category c : categories){
			if(c.getCategoryName().equals(category)){
				return true;
			}
		}
		return false;
	}
}
