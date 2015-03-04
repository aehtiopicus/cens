package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.repository.cens.ContactoCensRepository;

@Service
public class ContactoCensServiceImpl implements ContactoCensService{

	@Autowired
	private ContactoCensRepository repository;
}
