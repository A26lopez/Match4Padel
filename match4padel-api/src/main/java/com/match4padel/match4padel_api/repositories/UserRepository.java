package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.accountInfo.isActive = true")
    List<User> findActive();

    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.accountInfo.username) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.nif) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(u.contactInfo.email) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<User> search(@Param("q") String q);

    Optional<User> findByAccountInfoUsername(String username);

    Optional<User> findByContactInfoEmail(String email);

    boolean existsByAccountInfoUsername(String username);

    boolean existsByContactInfoEmail(String email);

    boolean existsByContactInfoPhoneNumber(String phoneNumber);

    boolean existsByContactInfoNif(String nif);

}
