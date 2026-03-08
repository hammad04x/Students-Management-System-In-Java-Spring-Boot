package com.example.sms.dao;

import java.util.List;

public interface CURD<E, ID> {

    E save(E entity);

    E findById(ID id);

    boolean existById(ID id);

    void deleteById(ID id);

    List<E> findAll();

}
