package com.capgemini.fileapp.vobs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
public class Text {

	List<Sentence> sentence = new ArrayList<Sentence>();

	@XmlElement(name = "sentence")
	public List<Sentence> getSentence() {
		return sentence;
	}

	public void setSentence(List<Sentence> sentence) {
		this.sentence = sentence;
	}
	public void addSentences(Sentence sentence) {
		this.sentence.add(sentence);
	}

	@Override
	public String toString() {
		String result ="";
		for(Sentence sen:sentence) {
			System.out.println(""+sen.getWord());
		}
		return result;
	}

	
	
	
}
