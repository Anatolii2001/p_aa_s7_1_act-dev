package app.services;

import app.entities.Search;
import app.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Class SearchServiceImpl with CRUD methods for Search
 * @author Komarov Rostislav
 */

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    private final SearchRepository repository;

    @Autowired
    public SearchServiceImpl(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createSearch(Search search) {
        repository.saveAndFlush(search);
    }

    @Override
    public Search deleteSearch(Long id) {
        Search target = repository.getById(id);
        repository.delete(target);
        return target;
    }

    @Override
    public Search updateSearch(Search search) {
        repository.saveAndFlush(search);
        return search;
    }

    @Override
    public Optional<Search> getSearch(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Search> getAllSearches() {
        return repository.findAll();
    }
}
