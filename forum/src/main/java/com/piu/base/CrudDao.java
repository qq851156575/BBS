package com.piu.base;

import java.util.List;

public interface CrudDao<T> extends BaseDao {
	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	T get(String id);

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	T get(T entity);

	/**
	 * 查询数据列表
	 * 
	 * @param entity
	 * @return
	 */
	List<T> findList(T entity);

	/**
	 * 查询所有数据列表
	 * 
	 * @param entity
	 * @return
	 */
	List<T> findAllList(T entity);

	

	/**
	 * 插入数据
	 * 
	 * @param entity
	 * @return
	 */
	int insert(T entity);

	/**
	 * 更新数据
	 * 
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	int delete(String id);

	/**
	 * 删除数据
	 * 
	 * @param entity
	 * @return
	 */
	int delete(T entity);

	/**
	 * 在MySQL中，取得ID的最大值。
	 * 
	 * @param entity
	 * @return
	 */
	int max(T entity);

}
