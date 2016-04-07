package ar.com.midasconsultores.catalogodefiltros.repository;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<Users, String> {

    public Users findByUsername(String username);
  
    public List<Users> findAllByEmail(String email);
    
    @Query(value="select us from Users us where us.username not in (select u.username from Users u join u.authorities a where a.authority = 'admin')")
    public List<Users> findAllNoAdmin();
            
}