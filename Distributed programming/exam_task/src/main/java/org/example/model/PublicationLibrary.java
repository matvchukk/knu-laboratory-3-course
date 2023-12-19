package org.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationLibrary {

    private static final List<Publication> PUBLICATIONS = new ArrayList<>();

    static {
        PUBLICATIONS.addAll(
                Arrays.asList(
                        new Publication(1, "Some", new String[]{"a", "b"}, 2007, new String[]{"r, l"})
                )
        );
    }

    public static List<Publication> findPublicationsByAuthor(String author) {
        return PUBLICATIONS.stream()
                .filter(b -> Arrays.asList(b.getAuthors()).contains(author))
                .collect(Collectors.toList());
    }

    public static List<Publication> findPublicationsAfterYear(int year) {
        return PUBLICATIONS.stream()
                .filter(b -> b.getYearOfPublication() > year)
                .collect(Collectors.toList());
    }

    public static List<Publication> findPublicationsByKeywords(String keywords) {
        return PUBLICATIONS.stream()
                .filter(b -> Arrays.asList(b.getKeywords()).containsAll(Arrays.asList(keywords)))
                .collect(Collectors.toList());
    }

    public static List<Publication> findAndSortPublications(String title, String authors, int year, String keywords) {
        List<Publication> filteredPublications = PUBLICATIONS.stream()
                .filter(b -> (title == null || b.getTitle().toLowerCase().contains(title.toLowerCase()))
                        && (authors == null || Arrays.asList(b.getAuthors()).containsAll(Arrays.asList(authors)))
                        && (year == 0 || b.getYearOfPublication() == year)
                        && (keywords == null || Arrays.asList(b.getKeywords()).containsAll(Arrays.asList(keywords))))
                .collect(Collectors.toList());

        filteredPublications.sort((b1, b2) -> calculateRelevance(b2, title, authors, year, keywords)
                - calculateRelevance(b1, title, authors, year, keywords));

        return filteredPublications;
    }

    private static int calculateRelevance(Publication publication, String title, String authors, int year, String keywords) {
        int relevance = 0;

        // Add points for each attribute that matches the query
        if (title != null && publication.getTitle().toLowerCase().contains(title.toLowerCase())) {
            relevance += 3; // More points for the title
        }

        if (authors != null && Arrays.asList(publication.getAuthors()).containsAll(Arrays.asList(authors))) {
            relevance += 2; // Fewer points than title but more than other attributes for authors
        }

        if (year != 0 && publication.getYearOfPublication() == year) {
            relevance += 1; // Fewer points than authors but more than keywords for year
        }

        if (keywords != null && Arrays.asList(publication.getKeywords()).containsAll(Arrays.asList(keywords))) {
            relevance += 1; // Fewer points than authors and year, but still important for keywords
        }

        return relevance;
    }



}
