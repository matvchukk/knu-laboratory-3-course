package xml.parser;

import xml.model.Film;
import xml.model.Genre;
import xml.model.VideoStore;
import xml.utils.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DomParser {
    public static VideoStore parse(String pathToXml)
            throws ParserConfigurationException, IOException, SAXException {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        Schema schema = schemaFactory.newSchema(new File(Constants.PATH_TO_XSD));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setSchema(schema);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ParsingErrorHandler());
        Document document = builder.parse(new File(pathToXml));
        document.getDocumentElement().normalize();

        List<Genre> genres = new ArrayList<>();
        List<Film> films = new ArrayList<>();
        NodeList genreNodes = document.getElementsByTagName(Constants.GENRE);
        NodeList filmNodes = document.getElementsByTagName(Constants.FILM);

        for (int i = 0; i < genreNodes.getLength(); i++) {
            Element node = (Element) genreNodes.item(i);

            Genre genre = new Genre();
            genre.setId(Integer.parseInt(node.getAttribute(Constants.ID)));
            genre.setName(node.getAttribute(Constants.NAME));

            genres.add(genre);
        }

        for (int i = 0; i < filmNodes.getLength(); i++) {
            Element node = (Element) filmNodes.item(i);

            Film film = new Film();
            film.setId(Integer.parseInt(node.getAttribute(Constants.ID)));
            film.setFilmGenreId(Integer.parseInt(node.getAttribute(Constants.GENRE_ID)));
            film.setName(node.getAttribute(Constants.NAME));
            film.setFilmDirector(node.getAttribute(Constants.FILM_DIRECTOR));
            film.setTopStatus(Boolean.parseBoolean(node.getAttribute(Constants.IS_IN_TOP)));

            films.add(film);
        }

        return new VideoStore(genres, films);
    }

    public static void serialize(VideoStore videoStore, String pathToXml)
            throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement(Constants.VIDEO_STORE);
        document.appendChild(root);

        List<Genre> genres = videoStore.getListOfGenres();
        for (Genre genre : genres) {
            Element genreNode = document.createElement(Constants.GENRE);
            genreNode.setAttribute(Constants.ID, genre.getId().toString());
            genreNode.setAttribute(Constants.NAME, genre.getName());
            root.appendChild(genreNode);

            Integer genreId = genre.getId();
            List<Film> filmsOfGenre = videoStore.getListOfFilmsOfGenre(genreId);
            for (Film film : filmsOfGenre) {
                Element filmNode = document.createElement(Constants.FILM);
                filmNode.setAttribute(Constants.ID, film.getId().toString());
                filmNode.setAttribute(Constants.GENRE_ID, film.getFilmGenreId().toString());
                filmNode.setAttribute(Constants.NAME, film.getName());
                filmNode.setAttribute(Constants.IS_IN_TOP, film.getTopStatus().toString());
                filmNode.setAttribute(Constants.FILM_DIRECTOR, film.getFilmDirector());
                genreNode.appendChild(filmNode);
            }
        }

        Source domSource = new DOMSource(document);
        Result result = new StreamResult(new File(pathToXml));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, result);
    }
}
