package br.com.auth.webauthexample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.auth.webauthexample.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    
    Optional<UserModel> findByEmail(@Param("email") String email);

    Boolean existsByEmail(@Param("email") String email);

}