package com.kv.generics.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GenericPropertyBean {
	
	@Getter
	@Setter
	private String propertyName;
	
	@Getter
	@Setter
	private String propertyType;

}
