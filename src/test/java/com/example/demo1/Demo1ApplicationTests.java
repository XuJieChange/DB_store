package com.example.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("test1com.example.demo1.mapper")
public class Demo1ApplicationTests {
	public static void main(String[] args){
		SpringApplication.run(Demo1Application.class, args);
	}

}
