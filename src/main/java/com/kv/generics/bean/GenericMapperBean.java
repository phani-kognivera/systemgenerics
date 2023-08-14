package com.kv.generics.bean;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "mapper")
@Data
public class GenericMapperBean {
	
	@Id
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private List<GenericPropertyBean> genericProperties;

}
