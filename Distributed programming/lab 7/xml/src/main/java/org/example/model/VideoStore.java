package org.example.model;

import java.util.*;

public class VideoStore {

    private final List<Genre> genreList;
    private final List<Film> filmList;

    public VideoStore() {
        this.genreList = new ArrayList<>();
        this.filmList = new ArrayList<>();
    }

    public VideoStore(List<Genre> genreList, List<Film> filmList) {
        this.genreList = genreList;
        this.filmList = filmList;
    }

    public void addGenre(String name, String country) {
        Integer id = new Random().nextInt();
        Genre toAdd = new Genre(id, name);
        genreList.add(toAdd);
    }

    public void addFilmToGenre(Integer genreId, String name, Boolean isInTop, String filmDirector) {
        Optional<Genre> genre = findGenreById(genreId);
        Integer id = new Random().nextInt();
        Film toAdd = new Film(id, name, isInTop, genreId, filmDirector);
        filmList.add(toAdd);
    }

    public void deleteGenre(Integer genreId) {
        Optional<Genre> genre = findGenreById(genreId);

        if (genre.isEmpty()) {
            return;
        }

        genreList.remove(genre.get());
        long filmsToDelete = getFilmCountInGenre(genreId);
        deleteFilmsInGenre(genreId);
    }

    public void deleteFilm(Integer filmId) {
        boolean isDeleted = filmList.removeIf(Film -> Objects.equals(Film.getId(), filmId));

        if (isDeleted) {
            System.out.printf("Deleted Film: %s%n", filmId);
        }
    }

    public void updateGenre(Integer genreId, String name, String country) {
        Optional<Genre> genre = findGenreById(genreId);

        if (genre.isEmpty()) {
            return;
        }

        Genre toUpdate = genre.get();
        toUpdate.setName(name);
    }

    public void updateFilm(Integer filmId, Integer genreId, String name, String filmDirector, Boolean isInTop) {
        Optional<Film> Film = findFilmById(filmId);

        if (Film.isEmpty()) {
            System.out.printf("Unable to update Film: %s(no occurrence found)%n", filmId);
            return;
        }

        Film toUpdate = Film.get();
        toUpdate.setFilmGenreId(genreId);
        toUpdate.setName(name);
        toUpdate.setFilmDirector(filmDirector);
        toUpdate.setTopStatus(isInTop);
    }

    public void getGenreById(Integer genreId) {
        Optional<Genre> genre = findGenreById(genreId);

        if (genre.isEmpty()) {
            return;
        }
    }

    public void getFilmById(Integer filmId) {
        Optional<Film> Film = findFilmById(filmId);

        if (Film.isEmpty()) {
            return;
        }

        System.out.printf("Found Film with id: %s%n%s%n", filmId, Film.get());
    }

    public List<Genre> getListOfGenres() {
        return new ArrayList<>(genreList);
    }

    public void printAllGenres() {
        System.out.println("genres:");

        for (Genre genre : genreList) {
            System.out.println(genre);
            System.out.println("--------------------------");
        }
    }

    public List<Film> getListOfFilmsOfGenre(Integer genreId) {
        return filmList.stream()
                .filter(Film -> Objects.equals(Film.getFilmGenreId(), genreId))
                .toList();
    }

    public void printAllFilmsOfGenre(Integer genreId) {
        Optional<Genre> genre = findGenreById(genreId);

        if (genre.isEmpty()) {
            System.out.printf("Unable to get all filmList of genre: %s(no occurrence of genre found)%n", genreId);
        }

        List<Film> FilmsOfGenre = getListOfFilmsOfGenre(genreId);
        System.out.printf("Films of genre %s:%n", genreId);

        for (Film Film : FilmsOfGenre) {
            System.out.println(Film);
            System.out.println("--------------------------");
        }
    }

    public long getFilmCountInGenre(Integer genreId) {
        return filmList.stream()
                .filter(genre -> Objects.equals(genre.getFilmGenreId(), genreId))
                .count();
    }

    private Optional<Genre> findGenreById(Integer id) {
        return genreList.stream()
                .filter(genre -> Objects.equals(genre.getId(), id))
                .findFirst();
    }

    private Optional<Film> findFilmById(Integer id) {
        return filmList.stream()
                .filter(Film -> Objects.equals(Film.getId(), id))
                .findFirst();
    }

    private void deleteFilmsInGenre(Integer genreId) {
        filmList.removeIf(Film -> Objects.equals(Film.getFilmGenreId(), genreId));
    }
}
