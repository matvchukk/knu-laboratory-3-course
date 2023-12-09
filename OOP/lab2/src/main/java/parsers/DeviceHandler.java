package main.java.parsers;

import main.java.models.Device;
import main.java.models.Elements;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class DeviceHandler extends DefaultHandler {
    private List<Device> devicesList = new ArrayList<>();
    private Device currentDevice;
    private String currentElementType;

    public List getDeviceList() {
        return devicesList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (qName.equals("Device")) {
            currentDevice = new Device();
            currentDevice.setId(attrs.getValue(0));
        }
        if (qName.equals("name"))
            currentElementType = Elements.NAME;
        else if (qName.equals("origin"))
            currentElementType = Elements.ORIGIN;
        else if (qName.equals("price"))
            currentElementType = Elements.PRICE;
        else if (qName.equals("critical"))
            currentElementType = Elements.CRITICAL;
        else if (qName.equals("peripheral"))
            currentElementType = Elements.PERIPHERAL;
        else if (qName.equals("energyConsumption"))
            currentElementType = Elements.ENERGY_CONSUMPTION;
        else if (qName.equals("cooler"))
            currentElementType = Elements.COOLER;
        else if (qName.equals("group"))
            currentElementType = Elements.GROUP;
        else if (qName.equals("port"))
            currentElementType = Elements.PORT;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("Device"))
            devicesList.add(currentDevice);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String valueOfElement = new String(ch, start, length).trim(); // Trim whitespace

        if (!valueOfElement.isEmpty()){
            try {
                switch (currentElementType) {
                    case Elements.NAME:
                        currentDevice.setName(valueOfElement);
                        break;
                    case Elements.ORIGIN:
                        currentDevice.setOrigin(valueOfElement);
                        break;
                    case Elements.PRICE:
                        currentDevice.setPrice(Integer.valueOf(valueOfElement));
                        break;
                    case Elements.CRITICAL:
                        currentDevice.setCritical(Boolean.parseBoolean(valueOfElement));
                        break;
                    case Elements.PERIPHERAL:
                        currentDevice.getTypes().setPeripherals(Boolean.parseBoolean(valueOfElement));
                        break;
                    case Elements.ENERGY_CONSUMPTION:
                        currentDevice.getTypes().setEnergyConsumption(Short.parseShort(valueOfElement));
                        break;
                    case Elements.COOLER:
                        currentDevice.getTypes().setCooler(Boolean.parseBoolean(valueOfElement));
                        break;
                    case Elements.GROUP:
                        currentDevice.getTypes().setGroup(valueOfElement);
                        break;
                    case Elements.PORT:
                        currentDevice.getTypes().setPort(valueOfElement);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
