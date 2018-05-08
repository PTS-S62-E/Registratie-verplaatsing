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
		if (categoryDao.getCategory(category.getName()) != null){
			StringBuilder builder = new StringBuilder();
			builder.append("Category: ");
			builder.append(category.getName());
			builder.append(" already exists.");
			throw new CategoryException(builder.toString());
		}
		category.setName(category.getName().toUpperCase());
		categoryDao.createCategory(category);
	}
}
