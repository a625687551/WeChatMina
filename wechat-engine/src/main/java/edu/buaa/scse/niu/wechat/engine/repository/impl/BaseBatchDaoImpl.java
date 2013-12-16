package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.repository.BatchDao;

/**
 * 实现了批量插入、更新、删除操作的基本Dao
 * @author Niu
 *
 * @param <T>
 */
@Transactional
public class BaseBatchDaoImpl<T extends Serializable> extends BaseDaoImpl<T> implements BatchDao<T>{

	private static int batchSize=40;
	
	@Override
	public List<T> save(List<T> data) {
		Session session=getSession();
		for(int i=0,size=data.size();i<size;i++){
			T entity=data.get(i);
			session.save(entity);
			if(i>0 && i%batchSize==0){
				session.flush();
				session.clear();
			}
		}
		return data;
		
	}

	@Override
	public List<T> update(List<T> data) {
		Session session=getSession();
		for(int i=0,size=data.size();i<size;i++){
			T entity=data.get(i);
			session.update(entity);
			if(i>0 && i%batchSize==0){
				session.flush();
				session.clear();
			}
		}
		return data;
	}

	@Override
	public List<T> delete(List<T> data) {
		Session session=getSession();
		for(int i=0,size=data.size();i<size;i++){
			T entity=data.get(i);
			session.delete(entity);
			if(i>0 && i%batchSize==0){
				session.flush();
				session.clear();
			}
		}
		return data;
	}

}
