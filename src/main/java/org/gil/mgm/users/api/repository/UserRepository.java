package org.gil.mgm.users.api.repository;

import org.gil.mgm.users.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByDateCreatedBetween(LocalDate start, LocalDate end);
    List<UserEntity> findByProfession(String profession);

}
