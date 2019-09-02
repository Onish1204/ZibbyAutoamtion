package com.zibby.api.utils;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.zibby.api.core.Res;

public class ConfigurationIRest {

    private List<Element> elements = new ArrayList<Element>();

    public List<Element> getElements() {
        return elements;
    }

    public ConfigurationIRest() {
        URL folderPath = Res.getResource("project/resources");
        File folder = null;
        try {
            folder = new File(folderPath.toURI());
        } catch (URISyntaxException ex) {
            throw new RuntimeException("Unable to find the file", ex);
        }

        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (isXmlFile(fileName)) {
                    createXmlElement(file);
                }
            }
        }
    }

    private boolean isXmlFile(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        return fileName.substring(beginIndex + 1).equals("xml");
    }

    private void createXmlElement(File xmlFile) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            Element root = doc.getDocumentElement();
            root.normalize();
            elements.add(root); 
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Unable to parse the xml", ex);
        }
        catch (SAXException ex) {
            throw new RuntimeException("Unable to parse the xml", ex);
        }
        catch (IOException ex) {
            throw new RuntimeException("Unable to parse the xml", ex);
        }
    }

}