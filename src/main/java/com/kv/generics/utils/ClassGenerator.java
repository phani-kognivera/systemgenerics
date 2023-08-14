package com.kv.generics.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kv.generics.repository.GenericMapperRepository;
import com.kv.generics.service.impl.CustomBeanFacory;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

@Service
public class ClassGenerator {
	
	@Autowired
	private CustomBeanFacory registerBean;
	
	@Autowired
	private GenericMapperRepository genericMapperRepository;
	
	public void generateClassFromRepository() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		 // create an empty source file 
		
		String fileName = "Hello";
        File sourceFile = new File(fileName+".java");
        sourceFile.deleteOnExit(); 
        
        Files.deleteIfExists(Paths.get("d:/testclass/Hello.class"));
 
        // generate the source code, using the source filename as the class name 
        String classname = fileName; 
        String sourceCode = "import java.util.*; public class " + classname + "{ public void hello() { List<String> s = new ArrayList<>(); s.add(\"abc\"); System.out.print(\"Hello world \"+s);}}"; 
 
        // write the source code into the source file 
        FileWriter writer = new FileWriter(sourceFile); 
        writer.write(sourceCode); 
        writer.close(); 
         
        // compile the source file 
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null); 
        File parentDirectory = new File("/d:/testclass");//sourceFile.getParentFile(); 
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory)); 
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)); 
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call(); 
        fileManager.close(); 
         System.out.println("------"+parentDirectory.toURI().toURL());
        // load the compiled class 
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { parentDirectory.toURI().toURL() }); 
        Class<?> helloClass = classLoader.loadClass(classname); 
        registerBean.registerBean("Hello", helloClass.getDeclaredConstructor().newInstance());
        sourceFile.deleteOnExit(); 
        generateClassFromRepository1();
		/*
		 * registerBean.destoryBean("Hello");
		 * 
		 * registerBean.registerBean("Hello2",
		 * helloClass.getDeclaredConstructor().newInstance());
		 */
	}
	
	public void generateClassFromRepository1() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		DynamicType.Builder<?> buddy = new ByteBuddy(ClassFileVersion.ofThisVm())
				  .subclass(Object.class);
		buddy = buddy
		  .defineField("test", String.class)
		  .annotateField(AnnotationDescription.Builder.ofType(Getter.class).build(),AnnotationDescription.Builder.ofType(Setter.class).build());
		Class<?> dynamicType = buddy
				  .make()
				  .load(getClass().getClassLoader())
				  .getLoaded();
		
		try {
			System.out.println(dynamicType.getDeclaredFields().length );
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(dynamicType.getDeclaredConstructor().newInstance().toString());
	}
	
	
}
