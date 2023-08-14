package com.kv.generics.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileLoader {

	public JSONObject readFromFile(String filePath) throws IOException {

		FileInputStream fis = new FileInputStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		StringBuffer stBf = new StringBuffer();
		String st;
		while ((st = br.readLine()) != null) {
			stBf.append(st);
		}

		JSONObject json = new JSONObject(stBf.toString());
		log.info("File Loaded with the Json -----> " + json);
		return json;
	}
}