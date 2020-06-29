package com.asdev.springdata.repository;

import com.asdev.springdata.entity.AddressEntity;
import com.asdev.springdata.entity.filter.AddressFilter;

import java.util.List;

public interface AddressCustomRepository {

    List<AddressEntity> findByFilter(AddressFilter filter);
}
