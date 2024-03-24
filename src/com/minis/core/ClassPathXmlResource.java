package com.minis.core;

import com.minis.exception.XmlReadException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

public class ClassPathXmlResource implements Resource {
    private final Document document;
    private final Element rootElement;
    private final Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            document = saxReader.read(xmlPath);
            rootElement = document.getRootElement();
            elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
            throw new XmlReadException();
        }

    }

    @Override
    public Iterator<Object> iterator() {
        return (Iterator) elementIterator;
    }
}
