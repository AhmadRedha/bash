package ir.ds.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RegisteredRepository extends JpaRepository<Registered, Long> {

    // Optional custom query methods based on your requirements
    // For example, to find registered users by username:
    Registered findByUsername(String username);

    // Or to find users registered after a specific date:
    List<Registered> findByRegisterDateAfter(Date date);

    Registered findByClientId(String clientId);
}