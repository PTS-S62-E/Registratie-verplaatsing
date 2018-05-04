package dao;

import entities.Category;
import entities.Translocation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDaoImpl {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	public List<Category> getCategories(){
		TypedQuery<Category> query =
				em.createNamedQuery("Category.getCategories", Category.class);
		return query.getResultList();
	}
}
