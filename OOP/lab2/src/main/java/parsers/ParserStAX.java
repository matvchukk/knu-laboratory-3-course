package main.java.parsers;

import main.java.models.Device;
import main.java.models.Types;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.namespace.QName;


public class ParserStAX {
    public List<Device> parseStAX(File xml){
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        ArrayList<Device> list = new ArrayList<>();
        Device tempDevice = new Device();
        Types types = new Types();
        try {
            FileInputStream fileInputStream = new FileInputStream(xml);
            XMLEventReader xmlreader = xmlInputFactory.createXMLEventReader(fileInputStream);
            while (xmlreader.hasNext()) {
                XMLEvent xmlEvent = xmlreader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Device")) {
                        tempDevice = new Device();
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        if (idAttr != null) {
                            xmlEvent = xmlreader.nextEvent();
                        }
                    } else if (startElement.getName().getLocalPart().equals("name")) {
                        xmlEvent = xmlreader.nextEvent();
                        tempDevice.setName(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("origin")) {
                        xmlEvent = xmlreader.nextEvent();
                        tempDevice.setOrigin(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("price")) {
                        xmlEvent = xmlreader.nextEvent();
                        tempDevice.setPrice(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("critical")) {
                        xmlEvent = xmlreader.nextEvent();
                        tempDevice.setCritical(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("types")) {
                        types = new Types();
                    } else if (types != null) {
                        if (startElement.getName().getLocalPart().equals("peripheral")) {
                            xmlEvent = xmlreader.nextEvent();
                            types.setPeripherals(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals("energyConsumption")) {
                            xmlEvent = xmlreader.nextEvent();
                            types.setEnergyConsumption(Short.parseShort(xmlEvent.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals("cooler")) {
                            xmlEvent = xmlreader.nextEvent();
                            types.setCooler(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals("port")) {
                            xmlEvent = xmlreader.nextEvent();
                            types.setPort(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("group")) {
                            xmlEvent = xmlreader.nextEvent();
                            types.setGroup(xmlEvent.asCharacters().getData());
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Device")) {
                        tempDevice.setTypes(types);
                        list.add(tempDevice);
                    }
                }
            }

        } catch (XMLStreamException | FileNotFoundException exc) {
            exc.printStackTrace();
        }

        return list;
    }
}
