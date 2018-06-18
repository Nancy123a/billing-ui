package com.zeroandone.controller;

import com.zeroandone.domain.Carrier;
import com.zeroandone.domain.MRange;
import com.zeroandone.domain.Random;
import com.zeroandone.repository.CarrierRepository;
import com.zeroandone.repository.RandomRepository;
import com.zeroandone.repository.RangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class MediaController {

    private final RangeRepository mRangeRepository;
    private final RandomRepository randomRepository;

    public MediaController(RangeRepository mRangeRepository, CarrierRepository carrierRepository, RandomRepository randomRepository) {
        this.mRangeRepository = mRangeRepository;
        this.randomRepository=randomRepository;
    }

    @GetMapping(value="/ranges")
    public List<MRange> getAllZones(){
        return mRangeRepository.findAll();
    }

    @PostMapping(value="/ranges")
    public List<MRange> saveRange(@RequestBody MRange mRange){
        mRangeRepository.save(mRange);
        if(mRange.getCarrierId()!=null) {
                long _from=Long.parseLong(mRange.get_From());
                BigInteger _to=new BigInteger(mRange.get_To());
                for (BigInteger bi = BigInteger.valueOf(_from); bi.compareTo(_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                        LocalDate localDateTime = LocalDate.now();
                        Random random = new Random(mRange.getRangeId(),bi, mRange.getCarrierId(), mRange.getAssignmentId(), localDateTime);
                        randomRepository.save(random);

                }

        }
        return mRangeRepository.findAll();
    }

    @GetMapping(value = "/random")
    public List<Random> getAllRandom(){
        return randomRepository.findAll();
    }


}
