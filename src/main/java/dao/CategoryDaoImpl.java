package dao;

import entities.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoryDaoImpl implements CategoryDao{

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	public List<Category> getCategories(){
		TypedQuery<Category> query =
				em.createNamedQuery("Category.getCategories", Category.class);
		return query.getResultList();
	}

	public void createCategory(Category category){
		em.persist(category);
	}
}
