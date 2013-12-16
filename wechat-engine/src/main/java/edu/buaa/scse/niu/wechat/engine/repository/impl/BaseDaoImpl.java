package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Pagination;
import edu.buaa.scse.niu.wechat.engine.repository.BaseDao;
import edu.buaa.scse.niu.wechat.engine.repository.OrderBy;


/**
 * 基本Dao实现
 * 
 * @author NIU
 * 
 * @param <T>
 */
@Transactional
public class BaseDaoImpl<T extends Serializable> implements BaseDao<T> {

	// protected Logger logger = LoggerFactory.getLogger(getClass());

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	@Transactional(readOnly = true)
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
		} else {
			getSession().load(getPersistentClass(), id);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public T get(Serializable id) {
		Assert.notNull(id);
		return (T) getSession().get(getPersistentClass(), id);
	}

	@Override
	@Transactional(readOnly = true)
	public T load(Serializable id) {
		Assert.notNull(id);
		return load(id, false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<T> findAll(OrderBy... orders) {
		Criteria crit = createCriteria();
		if (orders != null) {
			for (OrderBy order : orders) {
				crit.addOrder(order.getOrder());
			}
		}
		return crit.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination<T> findAll(int pageNo, int pageSize, OrderBy... orders) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, pageNo, pageSize, null,
				OrderBy.asOrders(orders));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<T> findByProperty(String property, Object value) {
		Assert.hasText(property);
		return createCriteria(Restrictions.eq(property, value)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	@Override
	public T save(T entity) {
		Assert.notNull(entity);
		getSession().save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		Assert.notNull(entity);
		getSession().update(entity);
		return entity;
	}

	@Override
	public T saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T entity) {
		Assert.notNull(entity);
		return (T) getSession().merge(entity);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getSession().delete(entity);

	}

	@Override
	public T deleteById(Serializable id) {
		Assert.notNull(id);
		T entity = load(id);
		getSession().delete(entity);
		return entity;
	}

	@Override
	public void refresh(T entity) {
		getSession().refresh(entity);

	}

	/**
	 * 返回class对象
	 */
	@Override
	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	@Override
	public T createNewEntiey() {
		try {
			return getPersistentClass().newInstance();
		} catch (Exception e) {
			// logger.warn("不能创建实体对象");
			throw new RuntimeException("不能创建实体对象："
					+ getPersistentClass().getName());

		}
	}

	/**
	 * 按Criterion查询对象列表.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		return createCriteria(criterion).list();
	}

	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	protected Pagination<T> findByCriteria(Criteria crit, int pageNo,
			int pageSize, Projection projection, Order... orders) {
		int totalCount = ((Number) crit.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		Pagination<T> p = new Pagination<T>(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setPageData(new ArrayList<T>());
			return p;
		}
		crit.setProjection(projection);
		if (projection == null) {
			crit.setResultTransformer(Criteria.ROOT_ENTITY);
		}
		if (orders != null) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		crit.setFirstResult(p.getFirstIndex());
		crit.setMaxResults(p.getPageSize());
		p.setPageData(crit.list());
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<T> searchByProperty(String property, String value) {
		Assert.hasText(property);
		String exp = "%" + value + "%";
		return createCriteria(Restrictions.like(property, exp)).list();
	}
	
	@Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 从spring取得session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}