package com.capgemini.fileapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.capgemini.fileapp.service.FileAppServiceImpl;
/**
 * @author sivasudheendra
 *	Main Class where Application Starts
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class FileApp implements CommandLineRunner{

	@Autowired
	FileAppServiceImpl fileAppServiceImpl;
	public static void main(String[] args) {
		SpringApplication.run(FileApp.class, args);
	}
	@Override
	public void run(String... args) {
	
		try {
			fileAppServiceImpl.parseAndCreateFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
