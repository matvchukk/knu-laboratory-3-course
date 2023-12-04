import main.Cat;
import main.Client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServerTest {
    @Test
    void clientServerTest() throws IOException {
        try {
            Client client = new Client();
            client.connect("localhost", 8888);

            Cat cat = new Cat("Kit", 2);

            String response = client.sendObject(cat);
            Assertions.assertEquals("Object is deserialized", response);

            response = client.sendObject(null);
            Assertions.assertEquals("Error, received null object!", response);

            client.stop();
        }







        finally {
        }
    }
}