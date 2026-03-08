package com.example.sms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BasicCURD<E, ID> implements CURD<E, ID> {

    protected JpaRepository<E, ID> repo;

    public BasicCURD(JpaRepository<E, ID> repo) {
        this.repo = repo;
    }

    @Override
    public E save(E entity) {
        return repo.save(entity);
    }

    @Override
    public E findById(ID id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean existById(ID id) {
        return repo.existsById(id);
    }

    @Override
    public void deleteById(ID id) {
        repo.deleteById(id);
    }

    @Override
    public List<E> findAll() {
        return repo.findAll();
    }


}
