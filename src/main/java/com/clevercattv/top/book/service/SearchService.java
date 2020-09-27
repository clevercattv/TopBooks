package com.clevercattv.top.book.service;

import com.clevercattv.top.book.client.BasicClient;
import com.clevercattv.top.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final Map<String, BasicClient> clients;

    public List<Book> findBooks(String query, List<String> clientEndpoints) {
        return clients.entrySet().parallelStream()
                .filter(entry -> clientEndpoints.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(client -> client.<List<Book>>call(query))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(new ArrayList<>(), this::joinLists);
    }

    private List<Book> joinLists(List<Book> mainList, List<Book> secondaryList) {
        mainList.addAll(secondaryList);
        return mainList;
    }

}
