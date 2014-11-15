/**
 * 
 */
package services;

import java.sql.SQLException;
import java.util.List;

import ro.incrys.internship.entities.Entity;

/**
 * @author user
 *
 */
public abstract class EntityCRUDService<T extends Entity> {

	public abstract int createEntity(T entity) throws SQLException;

	public abstract List<T> getEntityList();

//	public abstract List<Entity> getEntityChildren(int idParent) throws IllegalMethodCallException;

	public abstract T getEntityById(int id);

	public abstract void updateEntity(T entity);

	public void updateEntityList(List<T> entityList) {
		for (T entity : entityList) {
			updateEntity(entity);
		}
	}

	public abstract void deleteEntity(int id);

}
