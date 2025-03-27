package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
