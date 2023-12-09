package main.java.parsers;
import main.java.models.Device;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import main.java.models.DeviceComparator;
public class ParserSAX {
    public static List<Device> parseSAX(File xml){
        List<Device> list = new ArrayList<>();
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            DeviceHandler deviceHandler = new DeviceHandler();
            parser.parse(xml, deviceHandler);
            list = deviceHandler.getDeviceList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
