package com.kv.generics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kv.generics.bean.GenericMapperBean;

public interface GenericMapperRepository extends MongoRepository<GenericMapperBean, String> {

}
