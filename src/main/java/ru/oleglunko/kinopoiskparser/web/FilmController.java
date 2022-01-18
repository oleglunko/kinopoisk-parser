package ru.oleglunko.kinopoiskparser.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.oleglunko.kinopoiskparser.repository.FilmRepository;

import java.time.LocalDate;

@Controller
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private FilmRepository repository;

    public FilmController(FilmRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/top")
    public String getTop10(@RequestParam(name = "date", required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd")
                                            LocalDate date, Model model) {
        log.info("get top 10 films");

        model.addAttribute("films", repository.findFirst10ByActualDateOrderByPosition(date));
        model.addAttribute("date", date == null ? LocalDate.now() : date);
        return "index";
    }
}
