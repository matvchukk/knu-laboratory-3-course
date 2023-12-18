package xml.utils;

import java.io.File;

public final class Constants {
    private Constants() {}

    // XML tags
    public static final String VIDEO_STORE = "VideoStore";
    public static final String GENRE = "Genre";
    public static final String FILM = "Film";
    public static final String ID = "id";
    public static final String GENRE_ID = "filmGenreId";
    public static final String NAME = "name";
    public static final String IS_IN_TOP = "isInTop";
    public static final String FILM_DIRECTOR = "filmDirector";

    //Paths to files
    public static final String PATH_TO_XML =
            String.join(File.separator, ".", "src", "xml", "resources", "input.xml");

    public static final String PATH_TO_XSD =
            String.join(File.separator, ".", "src", "xml", "resources", "schema.xsd");
}
