import main.java.parsers.XMLValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class XMLValidatorTest {

    @Test
    public void validate() {
        assertTrue(XMLValidator.validate("src/main/resources/devices.xml", "src/main/resources/devices.xsd"));
    }
}
