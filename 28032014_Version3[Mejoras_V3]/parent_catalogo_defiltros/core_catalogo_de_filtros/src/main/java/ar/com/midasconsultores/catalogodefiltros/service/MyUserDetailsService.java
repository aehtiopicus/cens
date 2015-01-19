package ar.com.midasconsultores.catalogodefiltros.service;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;

public class MyUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDetails loadUserByUsername(String username) {

        // assuming that you have a User class that implements UserDetails
        return entityManager.createQuery("from Users where username = :username", Users.class)
                .setParameter("username", username)
                .getSingleResult();


    }
}