package com.asdev.springdata.repository;

import com.asdev.springdata.entity.CompanyEntity;
import com.asdev.springdata.entity.filter.CompanyFilter;

import java.util.List;

public interface CompanyCustomRepository {

    List<CompanyEntity> findByFilter(CompanyFilter filter);
}
