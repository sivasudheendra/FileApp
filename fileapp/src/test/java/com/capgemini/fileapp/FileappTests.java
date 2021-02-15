package com.capgemini.fileapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.capgemini.fileapp.config.ApplicationConfig;
import com.capgemini.fileapp.service.FileAppServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileappTests {

	@InjectMocks
	private FileAppServiceImpl fileAppServiceImpl;

	@Mock
	private ApplicationConfig  applicationConfig;

	

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEmptyInputFilePath() {
		try {
			assertNotNull(applicationConfig.getInputfilename());
			fileAppServiceImpl.parseAndCreateFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
