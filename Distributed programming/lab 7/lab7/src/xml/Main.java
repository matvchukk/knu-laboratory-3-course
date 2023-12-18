package xml;

import xml.model.VideoStore;
import xml.parser.DomParser;
import xml.utils.Constants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        VideoStore videoStore = DomParser.parse(Constants.PATH_TO_XML);
        videoStore.addFilmToGenre(Integer.parseInt("3"), "Film", Boolean.parseBoolean("1"), "Someone");
        DomParser.serialize(videoStore, Constants.PATH_TO_XML);
    }
}