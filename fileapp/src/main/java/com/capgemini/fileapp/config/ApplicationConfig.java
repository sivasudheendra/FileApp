package com.capgemini.fileapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
@Value("${input.file.name}")	
private String inputfilename;
@Value("${output.xml.file.name}")	
private String outxmlfilename;
@Value("${output.csv.file.name}")	
private String outputcsvfilename;


public String getInputfilename() {
	return inputfilename;
}
public void setInputfilename(String inputfilename) {
	this.inputfilename = inputfilename;
}
public String getOutxmlfilename() {
	return outxmlfilename;
}
public void setOutxmlfilename(String outxmlfilename) {
	this.outxmlfilename = outxmlfilename;
}
public String getOutputcsvfilename() {
	return outputcsvfilename;
}
public void setOutputcsvfilename(String outputcsvfilename) {
	this.outputcsvfilename = outputcsvfilename;
}
@Override
public String toString() {
	return "ApplicationConfig [inputfilename=" + inputfilename + ", outxmlfilename=" + outxmlfilename
			+ ", outputcsvfilename=" + outputcsvfilename + "]";
}
			


}
