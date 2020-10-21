package com.activiti.interceptor;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;  
  
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME) //在运行期保留注解信息  

@Component
public @interface MyAnnotation {  
    //类名注解，默认即为当前类名  
    //String name() default "className";
	String role() default "1";
}
