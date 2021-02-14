package com.capgemini.fileapp.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.fileapp.config.ApplicationConfig;
import com.capgemini.fileapp.vobs.Sentence;
import com.capgemini.fileapp.vobs.Text;
import com.opencsv.CSVWriter;

/**
 * @author sivasudheendra
 * This is a Implementation Class which parses the Text File and Create a 
 * CSV and XML output file. opencsv lib is used for csv file generation.
 * jaxb-api for XML file generation.
 *
 */
@Service
public class FileAppServiceImpl implements IFileAppService {
	private static final Logger LOGGER = LogManager.getLogger(FileAppServiceImpl.class);
	public final static String REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s";
	@Autowired
	ApplicationConfig applicationConfig;

	/**
	 * This is the main method which is used to create CSV and XML files by 
	 * parsing the Text.
	 * 
	 */
	@Override
	public void parseAndCreateFiles() {
		// TODO Auto-generated method stub
		LOGGER.debug("Entered parseAndCreateFiles Method..");
		LOGGER.info("applicationConfig::" + applicationConfig.toString());
		String inputFilePath = applicationConfig.getInputfilename();

		try {
			LOGGER.info("Reading File:"+inputFilePath);
			List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
			List<String> sentences = getFormattedSentences(lines);
			LOGGER.info("Sentences size:" + sentences.size());
			
			Map<Sentence, String[]> wordMap = getFormattedWords(sentences);
			LOGGER.info("Creating CSV File..");
			CSVWriter writer = new CSVWriter(new FileWriter(applicationConfig.getOutputcsvfilename() ));

			for (Map.Entry<Sentence, String[]> mapValues : wordMap.entrySet()) {

				writer.writeNext(mapValues.getValue());
			}
			writer.flush();
			LOGGER.info("CSV File Created in path:"+applicationConfig.getOutputcsvfilename());
			LOGGER.info("Creating XML File.."); 
			Text text = setXmlVobs(wordMap);
		
			JAXBContext context = JAXBContext.newInstance(Text.class);
			Marshaller jaxbMarshaller = context.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// if you want to output to file.
			OutputStream os = new FileOutputStream(applicationConfig.getOutxmlfilename());
			jaxbMarshaller.marshal(text, os);
			LOGGER.info("XML FIle created in the Path:"+applicationConfig.getOutxmlfilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("IOException occured in the method parseAndCreateFiles:" + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Exception occured in the method parseAndCreateFiles:" + e.getMessage());
		}

	}
	/**
	 * This is the  method which contain business logic to parse the lines and create the 
	 * Formatted Sentences
	 * 
	 * @return Formatted Sentences
	 */
	public List<String> getFormattedSentences(List<String> lines) {

		List<String> formattedLines = new ArrayList<String>();
		List<String> sentences = new ArrayList<String>();
		String sentenceFormed = "";
		for (String s : lines) {
			String sentenceArray[] = s.split(REGEX);
			for (String sentence : sentenceArray) {
				System.out.println("sentence:" + sentence);
				formattedLines.add(sentence);
			}

		}
		for (String line : formattedLines) {

			if (!line.trim().isEmpty()) {
				if (!sentenceFormed.trim().isEmpty()) {
					line = sentenceFormed + line;
					sentenceFormed = "";
				}

				if (!(line.endsWith(".") || line.endsWith("!") || line.endsWith("?"))) {
					sentenceFormed = line;
				} else {
					line = line.replaceAll(",", " ").replaceAll(",", " ").replaceAll(":", "").replaceAll("\\(", "")
							.replaceAll("-", " ").replaceAll("\\)", "").replaceAll("\t", " ").replaceAll("\\s+", " ")
							.replaceAll("\\s", " ").replaceAll("  ", " ");
					sentences.add(line);
				}
			}
		}
		if (!sentenceFormed.trim().isEmpty()) {
			sentences.add(sentenceFormed);
		}
		return sentences;
	}

	/**
	 * This is the  method which contain business logic to parse the sentences and create the 
	 * Words
	 * 
	 * @return Map which contains array of Words
	 */
	public Map<Sentence, String[]> getFormattedWords(List<String> sentences) {
		int count = 0;
		Map<Sentence, String[]> map = new LinkedHashMap<Sentence, String[]>();
		for (String sentence : sentences) {
			String[] wordArr = sentence.trim().split(" ");
			String[] words = new String[wordArr.length];
			int i = 0;
			//System.out.println("words length:" + words.length);
			if (count == 0) {
				words = new String[50];
				for (int k = 0; k < 50; k++) {
					if (k == 0) {
						words[k] = "";
					} else {
						words[k] = "Word " + k;
					}
				}
				
				map.put(new Sentence("Sentence 0"), words);
				count = 1;

			}
			int j = 0;
			for (String word : wordArr) {
				//word = word.replaceAll("[^a-zA-Z0-9]", " ");
				if (word != null && word.trim().length() > 0) {
					j++;
				}
			}
			words = new String[j + 1];
			for (String word : wordArr) {
				//word = word.replaceAll("[^a-zA-Z0-9]", " ");
				//System.out.println("wordarr:" + word);
				if (word != null && word.trim().length() > 0) {

					if (i == 0) {
						words[0] = "a";
						i = 1;
					}

					//System.out.println("wordarrinserted:" + word);
					words[i] = word;
					i++;
				}
			}
			//System.out.println("out:" + words.length);
			Arrays.sort(words, String.CASE_INSENSITIVE_ORDER);
			words[0] = "Sentence " + count;
			map.put(new Sentence("" + count), words);
			count++;
		}
		return map;
	}
	/**
	 * This is the  method which is used to set the Vobs for XML creation
	 * 
	 * @return Text VOB which will be sent to Jaxb-Api for XML creation
	 */
	public Text setXmlVobs(Map<Sentence, String[]> result) {

		Text text = new Text();
		for (Map.Entry<Sentence, String[]> entry : result.entrySet()) {
			if (entry.getKey().getSentenceName().equals("Sentence 0"))
				continue;
			Sentence sentence = new Sentence();
			List<String> newList = new ArrayList<>(Arrays.asList(entry.getValue()));
			newList.remove(0);
			sentence.setWord(newList);
			text.addSentences(sentence);
		}
		return text;
	}
}
