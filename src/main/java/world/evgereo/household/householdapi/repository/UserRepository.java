package world.evgereo.household.householdapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import world.evgereo.household.householdapi.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    boolean existsByName(String name);

    @Modifying
    @Query(value = "delete from resident where resident_id=:userId", nativeQuery = true)
    void deleteAllPlacesOfResidence(Long userId);
}
