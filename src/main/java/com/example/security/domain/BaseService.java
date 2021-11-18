package com.example.security.domain;

import com.example.security.core.BaseJpaQueryDSLRepository;
import com.example.security.core.BaseQueryDSLService;
import com.example.security.domain.user.QUserInfo;

import java.io.Serializable;

public class BaseService<T, ID extends Serializable> extends BaseQueryDSLService<T, ID> {

    protected QUserInfo qUserInfo = QUserInfo.userInfo;


    protected BaseJpaQueryDSLRepository<T, ID> repository;

    public BaseService(BaseJpaQueryDSLRepository<T, ID> repository) {
        super(repository);
        this.repository = repository;
    }
}
