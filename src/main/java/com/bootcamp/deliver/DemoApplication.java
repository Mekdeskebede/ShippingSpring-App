package com.bootcamp.deliver;

import java.io.File;

import com.bootcamp.deliver.Controller.Productcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// new File(Productcontroller.uploadDirectory).mkdir();
		SpringApplication.run(DemoApplication.class, args);
	}

}
