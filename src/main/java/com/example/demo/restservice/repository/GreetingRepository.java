package com.example.demo.restservice.repository;

import com.example.demo.restservice.Greeting;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends PagingAndSortingRepository<Greeting, Long> {

}
