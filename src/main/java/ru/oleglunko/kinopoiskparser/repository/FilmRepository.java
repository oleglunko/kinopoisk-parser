package ru.oleglunko.kinopoiskparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oleglunko.kinopoiskparser.model.Film;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    public List<Film> findFirst10ByActualDateOrderByPosition(LocalDate date);
}
