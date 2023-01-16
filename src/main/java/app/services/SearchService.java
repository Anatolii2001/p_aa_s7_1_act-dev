package app.services;

import app.entities.Search;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    void createSearch(Search search);
    Search deleteSearch(Long id);
    Search updateSearch(Search search);
    Optional<Search> getSearch(Long id);
    List<Search> getAllSearches();
}
