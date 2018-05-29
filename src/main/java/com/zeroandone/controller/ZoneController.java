package com.zeroandone.controller;

import com.zeroandone.domain.Zone;
import com.zeroandone.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ZoneController {

    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping(value = "/zones")
    public List<Zone> getAllZones(){
        return zoneRepository.findAll();
    }

    @PostMapping(value = "/zones")
    public void insertZone(@RequestBody Zone zone){
        zoneRepository.saveAndFlush(zone);
    }


    @RequestMapping(value = "/zones/{zoneId}", method = RequestMethod.DELETE)
    public void deleteZone(@PathVariable("zoneId") String zoneId) {
        Zone zone=zoneRepository.findOne(Integer.valueOf(zoneId));
        zoneRepository.delete(zone);
    }


    @PutMapping(value = "/zones")
    public void editZone(@RequestBody Zone zone){
        zoneRepository.delete(zoneRepository.findOne(zone.getZoneId()));
        zoneRepository.save(zone);
    }

    @GetMapping(value="/zones/findByZoneName")
    public List<Zone> findByZoneName(@RequestParam String zoneName){
        List<Zone> zones=zoneRepository.findAll();
        List<Zone> zone_array=new ArrayList<>();
        for(Zone zone:zones){
            if(zone.getZoneName().contains(zoneName)){
                zone_array.add(zone);
            }
        }
        return zone_array;
    }


}
