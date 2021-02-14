package com.capgemini.fileapp.vobs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "sentence")
public class Sentence {
	
	public Sentence() {
		
	}

	public Sentence(String sentenceName) {
		super();
		this.sentenceName = sentenceName;
	}

	private String sentenceName;

	private List<String> word = new ArrayList<String>();

	@XmlElement
	public List<String> getWord() {
		return word;
	}

	public void setWord(List<String> word) {
		this.word = word;
	}

	public String getSentenceName() {
		return sentenceName;
	}

	public void setSentenceName(String sentenceName) {
		this.sentenceName = sentenceName;
	}
	
	
}
