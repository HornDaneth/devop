package com.example.backoffice.backofficeapi.restservices.repository;

import com.example.backoffice.backofficeapi.restservices.Greeting;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends PagingAndSortingRepository<Greeting, Long> {

}