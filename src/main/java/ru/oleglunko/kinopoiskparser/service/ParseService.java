package ru.oleglunko.kinopoiskparser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.oleglunko.kinopoiskparser.model.Film;
import ru.oleglunko.kinopoiskparser.repository.FilmRepository;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ParseService {

    private static final Logger log = LoggerFactory.getLogger(ParseService.class);
    private FilmRepository repository;

    public ParseService(FilmRepository repository) {
       this.repository = repository;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void parse() {
        final String URL = "http://www.kinopoisk.ru/top/";
        final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Mobile Safari/537.36";

        log.info("parse URL: {}", URL);

        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent(USER_AGENT)
                    .referrer("https://www.google.com")
                    .get();

            Elements elements = doc.getElementsByClass("desktop-rating-selection-film-item");
            for (Element el : elements) {
                Film film = buildFilm(el);
                repository.save(film);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Film buildFilm(Element el) {
        int position = getPosition(el);
        double rating = getRating(el);
        int votesCount = getVotesCount(el);
        String nameAndReleaseYear = el.getElementsByClass("selection-film-item-meta__original-name").text();
        String originalName = getOriginalName(nameAndReleaseYear, el);
        int releaseYear = getReleaseYear(nameAndReleaseYear);

        return new Film(originalName, position, releaseYear, rating, votesCount, LocalDate.now());
    }

    private int getPosition(Element el) {
        return Integer.parseInt(el.getElementsByClass("film-item-rating-position__position").text());
    }

    private double getRating(Element el) {
        return Double.parseDouble(el.getElementsByClass("rating__value rating__value_positive").text());
    }

    private int getVotesCount(Element el) {
        return Integer.parseInt(el.getElementsByClass("rating__count").text().replaceAll("\\s",""));
    }

    private String getOriginalName(String nameAndReleaseYear, Element el) {
        //check original name
        if (nameAndReleaseYear.matches("\\D*\\d{4}")) {
            return nameAndReleaseYear.substring(0, nameAndReleaseYear.lastIndexOf(','));
        } else {
            return el.getElementsByClass("selection-film-item-meta__name").text();
        }
    }

    private int getReleaseYear(String nameAndReleaseYear) {
        if (nameAndReleaseYear.matches("\\D*\\d{4}")) {
            return Integer.parseInt(nameAndReleaseYear.substring(nameAndReleaseYear.lastIndexOf(',') + 1).trim());
        } else {
            return Integer.parseInt(nameAndReleaseYear);
        }
    }
}
