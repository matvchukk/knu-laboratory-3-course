package main.java.parsers;

import main.java.models.Device;
import main.java.models.Types;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class ParserDOM {
    static String name = "";
    static String origin = "";
    static Integer price = 0;
    static Boolean critical = false;

    static Boolean peripheral = false;
    static Short energyConsumption = 0;
    static Boolean cooler = false;
    static String port = "";
    static String group = "";
    public static ArrayList<Device> parse(File xml) throws IOException, SAXException, ParserConfigurationException{
        ArrayList<Device> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse (xml);

        NodeList nodeList1 = doc.getElementsByTagName("Device");
        NodeList nodeList2 = doc.getElementsByTagName("types");

        for (int i = 0; i < nodeList1.getLength(); i++) {
            Element element1 = (Element) nodeList1.item(i);

            name = element1.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
            origin = element1.getElementsByTagName("origin").item(0).getChildNodes().item(0).getNodeValue();
            price = Integer.parseInt(element1.getElementsByTagName("price").item(0).getChildNodes().item(0).getNodeValue());
            critical = Boolean.parseBoolean(element1.getElementsByTagName("critical").item(0).getChildNodes().item(0).getNodeValue());

            int j = i;
            for (; j < nodeList2.getLength(); j++) {
                Element element2 = (Element) nodeList2.item(j);

                peripheral = Boolean.parseBoolean(element2.getElementsByTagName("peripheral").item(0).getChildNodes().item(0).getNodeValue());
                energyConsumption = Short.parseShort(element2.getElementsByTagName("energyConsumption").item(0).getChildNodes().item(0).getNodeValue());
                cooler = Boolean.parseBoolean(element2.getElementsByTagName("cooler").item(0).getChildNodes().item(0).getNodeValue());
                port = element2.getElementsByTagName("port").item(0).getChildNodes().item(0).getNodeValue();
                group = element2.getElementsByTagName("group").item(0).getChildNodes().item(0).getNodeValue();

                break;
            }

            Device tempDevice = new Device();
            tempDevice.setName(name);
            tempDevice.setOrigin(origin);
            tempDevice.setPrice(price);
            tempDevice.setCritical(critical);
            Types types = new Types();
            types.setPeripherals(peripheral);
            types.setEnergyConsumption(energyConsumption);
            types.setCooler(cooler);
            types.setPort(port);
            types.setGroup(group);
            tempDevice.setTypes(types);
            list.add(tempDevice);

//            System.out.println("name: " + name);
//            System.out.println("origin: " + origin);
//            System.out.println("price: " + price);
//            System.out.println("critical: " + critical);
//            System.out.println("peripheral: " + peripheral);
//            System.out.println("energyConsumption: " + energyConsumption);
//            System.out.println("cooler: " + cooler);
//            System.out.println("port: " + port);
//            System.out.println("group: " + group);
//            System.out.println("---------------------------------");
        }

        return list;
    }
}
