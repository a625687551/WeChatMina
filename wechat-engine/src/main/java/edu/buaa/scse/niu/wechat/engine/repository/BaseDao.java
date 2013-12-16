package edu.buaa.scse.niu.wechat.engine.repository;

import java.io.Serializable;
import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Pagination;

/**
 * 操作数据库的基本Dao接口
 * @author NIU
 *
 * @param <T>
 */
public interface BaseDao<T extends Serializable> 
{
        /**
         * 通过ID查找对象
         * 
         * @param id
         *            记录的ID
         * @return 实体对象
         * 
         * 1.get()采用立即加载方式,而load()采用延迟加载;
     *    get()方法执行的时候,会立即向数据库发出查询语句,而load()方法返回的是一个代理(此代理中只有一个id属性),只有等真正使用该对象属性的时候,才会发出sql语句
     * 2.如果数据库中没有对应的记录,get()方法返回的是null.而load()方法出现异常ObjectNotFoundException
         */
        public T load(Serializable id);
        public T load(Serializable id,boolean lock);//锁模式可以禁止不可重复读取发生
        public T get(Serializable id);
        
        
        /**
         * 查找所有对象
         * 
         * @return 对象列表
         */
        public List<T> findAll();

        public List<T> findAll(OrderBy... orders);

        public Pagination<T> findAll(int pageNo, int pageSize, OrderBy... orders);
        
        /**
         * 模糊查询  %value% 形式匹配
         * @param property 查询的字段必须为字符串类型
         * @param value
         * @return
         */
        public List<T> searchByProperty(String property,String value);

        /**
         * 按属性查找对象列表.
         */
        public List<T> findByProperty(String property, Object value);

        /**
         * 按属性查找唯一对象.
         */
        public T findUniqueByProperty(String property, Object value);
        
        /**
         * 按属性查找对象的数量
         * 
         * @param property
         * @param value
         * @return
         */
        public int countByProperty(String property, Object value);

        
        /**
         * 保存对象
         * 
         * @param entity
         *            实体对象
         * @return 实体对象
         */
        public T save(T entity);

        /**
         * 更新对象
         * 
         * @param entity
         *            实体对象
         * @return 实体对象
         */
        public T update(T entity);

        /**
         * 保存或更新对象
         * 
         * @param entity
         *            实体对象
         * @return 实体对象
         */
        public T saveOrUpdate(T entity);

        /**
         * 保存或更新对象拷贝
         * 
         * @param entity
         * @return 已更新的持久化对象
         */
        public T merge(T entity);

        /**
         * 删除对象
         * 
         * @param entity
         *            实体对象
         */
        public void delete(T entity);

        /**
         * 根据ID删除记录
         * 
         * @param id
         *            记录ID
         */
        public T deleteById(Serializable id);

        /**
         * 刷新对象
         * 
         * @param entity
         */
        public void refresh(T entity);
        
        /**
         * 获得实体Class
         * 
         * @return
         */
        public Class<T> getPersistentClass();

        /**
         * 创建实体类的对象
         * 
         * @return
         */
        public T createNewEntiey();
        
}