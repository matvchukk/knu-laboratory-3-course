package org.example;


import org.example.model.VideoStore;
import org.example.parser.DomParser;
import org.example.utils.Utils;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        VideoStore videoStore = DomParser.parse(Utils.PATH_TO_XML);
        videoStore.addFilmToGenre(Integer.parseInt("3"), "Film5", Boolean.parseBoolean("1"), "Someone5");
        DomParser.serialize(videoStore, Utils.PATH_TO_XML);
    }
}