package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAccountInfoIsActive(boolean isActive);
    
    
    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.accountInfo.username) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.dni) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.email) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<User> search(@Param("q") String q);
    
    boolean existsByAccountInfoUsername(String username);
   
    boolean existsByContactInfoDni(String dni);
    
    boolean existsByContactInfoEmail(String email);

    boolean existsByContactInfoPhoneNumber(String phoneNumber);
    
}
