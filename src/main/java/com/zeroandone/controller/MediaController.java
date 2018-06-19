package com.zeroandone.controller;

import com.zeroandone.domain.Random;
import com.zeroandone.domain.Range;
import com.zeroandone.repository.CarrierRepository;
import com.zeroandone.repository.RandomRepository;
import com.zeroandone.repository.RangeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.time.LocalDate;
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

    @GetMapping(value="/ranges/findBy_From")
    public Page<Range> getAllRanges(@RequestParam("_From") String _From,Pageable pageRequest){
        Page<Range> ranges = null;
        if(_From==null || _From.equalsIgnoreCase("")) {
            ranges=mRangeRepository.findAll(pageRequest);
        }
        else{
            ranges=mRangeRepository.findBy_FromContainingIgnoreCase(_From, pageRequest);
        }
        return ranges;
    }


    @PostMapping(value="/ranges")
    public List<Range> saveRange(@RequestBody Range mRange){
        mRangeRepository.save(mRange);
        if(mRange.getCarrierId()==null) {
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
