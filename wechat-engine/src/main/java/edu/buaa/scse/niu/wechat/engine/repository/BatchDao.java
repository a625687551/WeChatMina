package edu.buaa.scse.niu.wechat.engine.repository;

import java.io.Serializable;
import java.util.List;

public interface BatchDao<T extends Serializable> extends BaseDao<T>{
	/**
     * 批量储存数据 
     * @param data
     */
    public List<T> save(List<T> data);
    
    /**
     * 批量更新数据
     * @param data
     */
    public List<T> update(List<T> data);
    
    /**
     * 批量删除数据
     * @param data
     */
    public List<T> delete(List<T> data);
}
