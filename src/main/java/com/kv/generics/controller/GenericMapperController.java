package com.kv.generics.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kv.generics.bean.GenericMapperBean;
import com.kv.generics.service.GenericMapperService;
import com.kv.generics.utils.ClassGenerator;
import com.kv.generics.utils.GenerateObjectFromFile;

@RestController
public class GenericMapperController {
	
	@Autowired
	private ClassGenerator generator;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private GenerateObjectFromFile generatorFromFile;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@GetMapping(value = "/hello")
	public String getTest() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		generator.generateClassFromRepository1();
		/*
		 * Object o = context.getBean("Hello");
		 * o.getClass().getMethod("hello").invoke(o);
		 */
		return "Hello World!!!";
	}
	@PostMapping(value = "/redFile")
	public void generateObject(@RequestBody Map<String, Object> map)
			throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		
		generatorFromFile.fileLoader();
		map.put("id", "cart-" + UUID.randomUUID());
		System.out.println("Welcome --->"+ context.getBean("cart").getClass().getFields().length);
		for(Field f : context.getBean("cart").getClass().getFields()) {
			System.out.println(f.getName());
		}

		System.out.println("------->"+context.getBean("cart").getClass().getName());
		// Object obj = context.getBean("cart").getClass().getDeclaredConstructor().newInstance();
		ObjectMapper mapper = new ObjectMapper();

		Object obj = mapper.convertValue(map, context.getBean("cart").getClass());
		

	}


	@Autowired
	private GenericMapperService genericMapperSerivce;
	
	@PostMapping(value = "/systemObject")
	public void createSystemObject(@RequestBody GenericMapperBean bean) {
		genericMapperSerivce.createGenericObject(bean);
	}

}
