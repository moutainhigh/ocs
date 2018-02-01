package com.rong.persist.base;

public interface BaseService<T> {
	public T findById(long id);
	public boolean deleteById(long id);
}
