package org.example.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.example.model.Film;
import org.example.model.Genre;
import org.example.model.VideoStore;
import org.example.utils.Utils;

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
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public static VideoStore parse(String pathToXml)
            throws ParserConfigurationException, IOException, SAXException {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        Schema schema = schemaFactory.newSchema(new File(Utils.PATH_TO_XSD));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setSchema(schema);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ParsingErrorHandler());
        Document document = builder.parse(new File(pathToXml));
        document.getDocumentElement().normalize();

        List<Genre> genres = new ArrayList<>();
        List<Film> films = new ArrayList<>();
        NodeList genreNodes = document.getElementsByTagName(Utils.GENRE);
        NodeList filmNodes = document.getElementsByTagName(Utils.FILM);

        for (int i = 0; i < genreNodes.getLength(); i++) {
            Element node = (Element) genreNodes.item(i);

            Genre genre = new Genre();
            genre.setId(Integer.parseInt(node.getAttribute(Utils.ID)));
            genre.setName(node.getAttribute(Utils.NAME));

            genres.add(genre);
        }

        for (int i = 0; i < filmNodes.getLength(); i++) {
            Element node = (Element) filmNodes.item(i);

            Film film = new Film();
            film.setId(Integer.parseInt(node.getAttribute(Utils.ID)));
            film.setFilmGenreId(Integer.parseInt(node.getAttribute(Utils.GENRE_ID)));
            film.setName(node.getAttribute(Utils.NAME));
            film.setFilmDirector(node.getAttribute(Utils.FILM_DIRECTOR));
            film.setTopStatus(Boolean.parseBoolean(node.getAttribute(Utils.IS_IN_TOP)));

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
        Element root = document.createElement(Utils.VIDEO_STORE);
        document.appendChild(root);

        List<Genre> genres = videoStore.getListOfGenres();
        for (Genre genre : genres) {
            Element genreNode = document.createElement(Utils.GENRE);
            genreNode.setAttribute(Utils.ID, genre.getId().toString());
            genreNode.setAttribute(Utils.NAME, genre.getName());
            root.appendChild(genreNode);

            Integer genreId = genre.getId();
            List<Film> filmsOfGenre = videoStore.getListOfFilmsOfGenre(genreId);
            for (Film film : filmsOfGenre) {
                Element filmNode = document.createElement(Utils.FILM);
                filmNode.setAttribute(Utils.ID, film.getId().toString());
                filmNode.setAttribute(Utils.GENRE_ID, film.getFilmGenreId().toString());
                filmNode.setAttribute(Utils.NAME, film.getName());
                filmNode.setAttribute(Utils.IS_IN_TOP, film.getTopStatus().toString());
                filmNode.setAttribute(Utils.FILM_DIRECTOR, film.getFilmDirector());
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
