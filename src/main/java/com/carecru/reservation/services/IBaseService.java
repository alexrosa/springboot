package com.carecru.reservation.services;

import java.util.List;

public interface IBaseService <T> {

    public T save(T obj);
    public List<T> findAll();
    public T findById(Long id);
    public void delete(Long id);
}
