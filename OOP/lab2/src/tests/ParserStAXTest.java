import main.java.models.Device;
import main.java.parsers.ParserStAX;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class ParserStAXTest {

    @Test
    public void parseStAX() throws XMLStreamException, FileNotFoundException {
        ParserStAX parserStAX = new ParserStAX();
        File xmlFile = new File("src/main/resources/devices.xml");
        List<Device> devices = parserStAX.parseStAX(xmlFile);

        Device firstDevice = devices.get(0);
        assertEquals(firstDevice.getName(), "AMD Ryzen 9 5900X");
        assertEquals(firstDevice.getOrigin(), "USA");
        assertEquals(firstDevice.getPrice().toString(), "700");
        assertTrue(firstDevice.isCritical());
        assertFalse(firstDevice.getTypes().isPeripherals());
        assertEquals(firstDevice.getTypes().getEnergyConsumption(), 105);
        assertTrue(firstDevice.getTypes().isCooler());
        assertEquals(firstDevice.getTypes().getGroup(), "CPU");
        assertEquals(firstDevice.getTypes().getPort(), "Not Applicable");

        Device secondDevice = devices.get(1);
        assertEquals(secondDevice.getName(), "Logitech G Pro X Mechanical Gaming Keyboard");
        assertEquals(secondDevice.getOrigin(), "Switzerland");
        assertEquals(secondDevice.getPrice().toString(), "150");
        assertFalse(secondDevice.isCritical());
        assertTrue(secondDevice.getTypes().isPeripherals());
        assertEquals(secondDevice.getTypes().getEnergyConsumption(), 2);
        assertFalse(secondDevice.getTypes().isCooler());
        assertEquals(secondDevice.getTypes().getGroup(), "Input");
        assertEquals(secondDevice.getTypes().getPort(), "USB");

        Device thirdDevice = devices.get(2);
        assertEquals(thirdDevice.getName(), "Samsung 970 EVO Plus 1TB NVMe M.2 Internal SSD");
        assertEquals(thirdDevice.getOrigin(), "South Korea");
        assertEquals(thirdDevice.getPrice().toString(), "180");
        assertTrue(thirdDevice.isCritical());
        assertFalse(thirdDevice.getTypes().isPeripherals());
        assertEquals(thirdDevice.getTypes().getEnergyConsumption(), 5);
        assertFalse(thirdDevice.getTypes().isCooler());
        assertEquals(thirdDevice.getTypes().getGroup(), "Storage");
        assertEquals(thirdDevice.getTypes().getPort(), "Not Applicable");

        Device fourthDevice = devices.get(3);
        assertEquals(fourthDevice.getName(), "Canon PIXMA Pro-100 Wireless Color Professional Inkjet Printer");
        assertEquals(fourthDevice.getOrigin(), "Japan");
        assertEquals(fourthDevice.getPrice().toString(), "400");
        assertFalse(fourthDevice.isCritical());
        assertTrue(fourthDevice.getTypes().isPeripherals());
        assertEquals(fourthDevice.getTypes().getEnergyConsumption(),30);
        assertFalse(fourthDevice.getTypes().isCooler());
        assertEquals(fourthDevice.getTypes().getGroup(), "Printer");
        assertEquals(fourthDevice.getTypes().getPort(), "USB");
    }
}