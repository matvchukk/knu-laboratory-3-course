package org.example.utils;

import java.io.File;

public final class Utils {
    private Utils() {}

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
            String.join(File.separator, "xml", "src", "main", "resources", "video_store.xml");

    public static final String PATH_TO_XSD =
            String.join(File.separator, "xml", "src", "main", "resources", "video_store.xsd");

}
