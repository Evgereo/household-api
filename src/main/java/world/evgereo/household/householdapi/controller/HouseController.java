package world.evgereo.household.householdapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import world.evgereo.household.householdapi.entity.House;
import world.evgereo.household.householdapi.entity.dto.HouseDto;
import world.evgereo.household.householdapi.service.HouseService;

import java.util.List;

@RestController
@RequestMapping(value = "/house", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @GetMapping()
    public ResponseEntity<List<House>> getHouses() {
        return ResponseEntity.ok(houseService.getHouses());
    }

    @GetMapping ("/{houseId:\\d+}")
    public ResponseEntity<House> getHouse(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.getHouse(houseId));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<House> createHouse(@RequestBody @Valid HouseDto houseDto) {
        House house = houseService.createHouse(houseDto);
        return ResponseEntity
                .created(UriComponentsBuilder.fromPath("/house/" + house.getId()).build().toUri())
                .body(house);
    }

    @PatchMapping(value = "/{houseId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<House> updateHouse(@PathVariable Long houseId,
                                           @RequestBody @Valid HouseDto houseDto) {
        return ResponseEntity.ok(houseService.updateHouse(houseId, houseDto));
    }

    @PatchMapping(value = "/{houseId:\\d+}/residents", params = {"residentId"})
    public ResponseEntity<House> addResident(@PathVariable Long houseId,
                                             @RequestParam Long residentId) {
        return ResponseEntity.ok(houseService.addResident(houseId, residentId));
    }

    @DeleteMapping(value = "/{houseId:\\d+}/residents", params = {"residentId"})
    public ResponseEntity<House> removeResident(@PathVariable Long houseId,
                                             @RequestParam Long residentId) {
        return ResponseEntity.ok(houseService.removeResident(houseId, residentId));
    }

    @DeleteMapping("/{houseId:\\d+}/residents")
    public ResponseEntity<House> removeAllResidents(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.removeAllResidents(houseId));
    }

    @DeleteMapping("/{houseId:\\d+}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long houseId) {
        houseService.deleteHouse(houseId);
        return ResponseEntity.noContent().build();
    }
}
