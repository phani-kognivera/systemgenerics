package com.kv.generics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kv.generics.bean.GenericMapperBean;
import com.kv.generics.repository.GenericMapperRepository;
import com.kv.generics.service.GenericMapperService;

@Service
public class GenericMapperServiceImpl implements GenericMapperService {
	
	@Autowired
	private GenericMapperRepository genericMapperRepository;

	@Override
	public void createGenericObject(GenericMapperBean bean) {
		genericMapperRepository.save(bean);
		
	}

}
