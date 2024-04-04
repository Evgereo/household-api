package world.evgereo.household.householdapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.evgereo.household.householdapi.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
