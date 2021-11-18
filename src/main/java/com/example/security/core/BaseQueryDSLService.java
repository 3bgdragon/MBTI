package com.example.security.core;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public abstract class BaseQueryDSLService<T, ID extends Serializable> {
    protected BaseJpaQueryDSLRepository<T, ID> repository;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public BaseQueryDSLService() {}

    public BaseQueryDSLService(BaseJpaQueryDSLRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected JPAQuery<T> select() { return new JPAQuery(this.em); }

    protected JPAUpdateClause update(EntityPath<?> entityPath) {
        return new JPAUpdateClause(this.em, entityPath);
    }

    protected JPADeleteClause delete(EntityPath<?> entityPath) {
        return new JPADeleteClause(this.em, entityPath);
    }

    public List<T> findAll() { return this.repository.findAll(); }

    public List<T> findAll(Sort sort) { return this.repository.findAll(sort); }

    public Page<T> findAll(Pageable pageable) { return this.repository.findAll(pageable); }

    public T findOne(Predicate predicate) { return (T) this.repository.findOne(predicate).get(); }

    public List<T> findAll(Predicate predicate, Sort sort) {
        return this.toList(this.repository.findAll(predicate, sort));
    }

    public List<T> toList(Iterable<T> iterable) {
        if (iterable == null) {
            return Collections.emptyList();
        } else {
            List<T> list = new ArrayList();
            Iterator var3 = iterable.iterator();

            while(var3.hasNext()) {
                T item = (T) var3.next();
                list.add(item);
            }

            return list;
        }
    }

    public List<T> findAll(Predicate predicate, OrderSpecifier... orderSpecifiers) {
        return this.toList(this.repository.findAll(predicate, orderSpecifiers));
    }

    public List<T> findAll(OrderSpecifier... orderSpecifiers) {
        return this.toList(this.repository.findAll(orderSpecifiers));
    }

    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return this.repository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return this.repository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return this.repository.exists(predicate);
    }

    public void flush() {
        this.repository.flush();
    }

    @Transactional
    public <S extends T> S saveAndFlush(S object) {
        return this.repository.saveAndFlush(object);
    }

    @Transactional
    public void deleteInBatch(Iterable<T> iterable) {
        this.repository.deleteInBatch(iterable);
    }

    @Transactional
    public void deleteAllInBatch() {
        this.repository.deleteAllInBatch();
    }

    public T getOne(ID id) {
        return this.repository.getOne(id);
    }

    @Transactional
    public <S extends T> S save(S var) {
        this.repository.save(var);
        return var;
    }

    @Transactional
    public <S extends T> Collection<S> save(Collection<S> vars) {
        vars.forEach(this::save);
        return vars;
    }

    public Optional<T> findById(ID var1) {
        return this.repository.findById(var1);
    }

    public boolean existsById(ID var1) {
        return this.repository.existsById(var1);
    }

    public long count() {
        return this.repository.count();
    }

    @Transactional
    public void delete(T var1) {
        this.repository.delete(var1);
    }

    @Transactional
    public <S extends T> Collection<S> delete(Collection<S> vars) {
        vars.forEach(this::delete);
        return vars;
    }

    @Transactional
    public void deleteById(ID var1) {
        this.repository.deleteById(var1);
    }

    @Transactional
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Transactional
    public void deleteAll(Iterable<? extends T> var1) {
        this.repository.deleteAll(var1);
    }

    public int getInt(Integer integer) {
        return integer == null ? 0 : integer;
    }

    public long getLong(Long _long) {
        return _long == null ? 0L : _long;
    }

    public int getInt(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.intValue();
    }

    public long getLong(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0L : bigDecimal.longValue();
    }

    protected String like(String field) {
        return "%" + field + "%";
    }

    public boolean isNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }

    public boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    public boolean isEmpty(Collection<?> list) {
        return list == null || list.size() == 0;
    }

    public boolean isNotEmpty(Collection<?> list) {
        return !this.isEmpty(list);
    }

    public boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return false;
        } else {
            return o2 == null ? false : o1.equals(o2);
        }
    }

    public boolean notEquals(Object o1, Object o2) {
        return !this.equals(o1, o2);
    }


}
