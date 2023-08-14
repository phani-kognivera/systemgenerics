package com.kv.generics.utils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import com.kv.generics.service.impl.CustomBeanFacory;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition;
import net.bytebuddy.jar.asm.Opcodes;

@Service
public class GenerateObjectFromFile {

	@Autowired
	private FileLoader filerLoader;
	
	@Autowired
	private CustomBeanFacory registerBean;
	
	public void fileLoader() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		generateObjectFromJson(filerLoader.readFromFile("D:\\poc\\cart\\cart.json"));
	}
	
	public void generateObjectFromJson(JSONObject json) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		System.out.println(ClassFileVersion.ofThisVm().getJavaVersion());
		DynamicType.Builder<?> buddy = new ByteBuddy(ClassFileVersion.ofThisVm()).subclass(Object.class).name("Cart");
		buddy = buddy.defineField("id",String.class,Opcodes.ACC_PUBLIC).annotateField(AnnotationDescription.Builder.ofType(Id.class).build(),AnnotationDescription.Builder.ofType(Getter.class).build(),
				AnnotationDescription.Builder.ofType(Setter.class).build());
		addClassAnnotation(json, buddy);
		JSONArray properties = json.getJSONArray("properties");

		for(Object node: properties) {
			buddy = properyLoader((JSONObject) node ,buddy);
		}
		
		System.out.println("hello 3");
		Class<?> c = buddy.make().load(getClass().getClassLoader()).getLoaded();
		System.out.println(c.getFields().length+"--------"+c.getDeclaredFields().length);
		registerBean.registerBean("cart", c.getDeclaredConstructor().newInstance());
		
	}
	
	public DynamicType.Builder<?> addClassAnnotation(JSONObject json, DynamicType.Builder<?> buddyBuilder) {
		if(json.getString("repository").equalsIgnoreCase("mongoDB")) {
			buddyBuilder = buddyBuilder.annotateType(AnnotationDescription.Builder.ofType(Document.class).define("collection", json.getString("serviceName")).build());
		}
		return buddyBuilder;
	}
	
	public DynamicType.Builder<?> properyLoader(JSONObject node, DynamicType.Builder<?> buddyBuilder) {
		System.out.println("hello 2");
		if(node.getString("type").equalsIgnoreCase("String")) {
			System.out.println("hello 22" + node.getString("name"));
			buddyBuilder = buddyBuilder.defineField(node.getString("name"),String.class,Opcodes.ACC_PUBLIC)
						.annotateField(AnnotationDescription.Builder.ofType(Getter.class).build(),
										AnnotationDescription.Builder.ofType(Setter.class).build());
		}
		return buddyBuilder;
	}
}
