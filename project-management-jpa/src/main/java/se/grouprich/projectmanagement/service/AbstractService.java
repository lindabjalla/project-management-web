package se.grouprich.projectmanagement.service;

import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.transaction.annotation.Transactional;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.UserException;
import se.grouprich.projectmanagement.model.AbstractEntityData;

public abstract class AbstractService<E extends AbstractEntityData, R extends CrudRepository<E, Long>>
{
	protected final R superRepository;
	private final Class<E> classType;

	AbstractService(final R superRepository, Class<E> classType)
	{
		this.superRepository = superRepository;
		this.classType = classType;
	}

	public E findById(final Long id) throws RepositoryException
	{
		E entity = superRepository.findOne(id);
		if (entity == null)
		{
			throw new RepositoryException(classType.getSimpleName().replace("Data", "") + " with id: " + id + " was not found");
		}

		return entity;
	}

	public E createOrUpdate(final E entity) throws UserException
	{
		return superRepository.save(entity);
	}

	@Transactional
	public E deleteById(final Long id) throws RepositoryException
	{
		E entity = findById(id);
		superRepository.delete(id);
		return entity;
	}

	public Iterable<E> findAll() throws RepositoryException
	{
		Iterable<E> entities = superRepository.findAll();
		if (Iterables.isEmpty(entities))
		{
			throw new RepositoryException("No entity was found");
		}
		return entities;
	}
}