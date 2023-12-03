package main.java.parsers;
import main.java.models.Device;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParserSAX {
    public List<Device> parseSAX(File xml) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        DeviceHandler deviceHandler = new DeviceHandler();
        saxParser.parse(xml, deviceHandler);
        return deviceHandler.getDeviceList();
    }
}
