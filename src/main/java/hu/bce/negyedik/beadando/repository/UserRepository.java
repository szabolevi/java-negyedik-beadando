package hu.bce.negyedik.beadando.repository;

import hu.bce.negyedik.beadando.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}