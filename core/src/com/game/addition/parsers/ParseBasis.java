package com.game.addition.parsers;

import com.game.GameSystem;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.net.URLDecoder;

public abstract class ParseBasis{
	protected static boolean isFromIDEA = true; //флаг, хранящий откуда мы запускаем проект, с IDEA или с .jar архива
	protected static String pathFromIDEA; //путь к папке с ресурсами для запуска с IDEA
	protected static String pathFromDesktop; //путь к папке с ресурсами для запуска с .jar архива
	
	
	protected static Document getDocument (String fromIDEA, String fromDesktop){
		pathFromDesktop = fromDesktop;
		pathFromIDEA = fromIDEA;
		Document document;
		try{
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance ().newDocumentBuilder ();
			
			URLDecoder decoder = new URLDecoder ();
			//строкой ниже мы полчили абсолютный путь к месту где находится файл класса ParseBasis
			StringBuilder path = new StringBuilder (decoder.decode (ParseBasis.class.getProtectionDomain ().getCodeSource ().getLocation ().getPath ()));
			int index = path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE); //ищем в этом пути имя архива
			if (index == -1){ //если не нашли, значит мы запускаем проект с IDEA
				isFromIDEA = true;
				document = documentBuilder.parse (fromIDEA);
			}
			else{ //если нашли, значит запускаем с .jar архива
				isFromIDEA = false;
				path.delete (index, path.length ()); //удаляем все начиная с имя архива
				path.append (fromDesktop);
				document = documentBuilder.parse (path.toString ());
			}
			return document;
			
		}
		catch (SAXException | IOException | ParserConfigurationException ex){
			ex.printStackTrace (System.out);
			return null;
		}
	}
}
