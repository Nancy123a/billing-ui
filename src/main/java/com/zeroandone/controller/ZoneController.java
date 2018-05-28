package com.zeroandone.controller;

import com.zeroandone.domain.Zone;
import com.zeroandone.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/api/zones")
public class ZoneController {

    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping(value="/all")
    public List<Zone> getAllZones(){
        return zoneRepository.findAll();
    }

    @PostMapping(value="/insertZone")
    public List<Zone> insertZone(@RequestBody Zone zone){
        zoneRepository.saveAndFlush(zone);
        return zoneRepository.findAll();

    }

    @DeleteMapping(value="/deleteZone")
    public List<Zone> deleteZone(@RequestBody Zone zone){
        zoneRepository.delete(zone);
        return zoneRepository.findAll();
    }

    @PostMapping(value="/editZone")
    public List<Zone> editZone(@RequestBody Zone zone){
        zoneRepository.delete(zoneRepository.getOne(zone.getZoneId()));
        zoneRepository.save(zone);
        return zoneRepository.findAll();
    }

    @GetMapping(value="/findByZoneName")
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
