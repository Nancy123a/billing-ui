package com.zeroandone.controller;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Random;
import com.zeroandone.domain.Range;
import com.zeroandone.repository.AssignmentRepository;
import com.zeroandone.repository.CarrierRepository;
import com.zeroandone.repository.RandomRepository;
import com.zeroandone.repository.RangeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping(value="/api")
public class MediaController {

    private final RangeRepository mRangeRepository;
    private final RandomRepository randomRepository;
    private final AssignmentRepository assignmentRepository;

    public MediaController(RangeRepository mRangeRepository, CarrierRepository carrierRepository, RandomRepository randomRepository,AssignmentRepository assignmentRepository) {
        this.mRangeRepository = mRangeRepository;
        this.randomRepository=randomRepository;
        this.assignmentRepository=assignmentRepository;
    }

    @GetMapping(value="/ranges/findBy_From")
    public Page<Range> getAllRanges(@RequestParam("_From") String _From,Pageable pageRequest){
        Page<Range> ranges=null;
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
        BigInteger _to=new BigInteger(mRange.get_To());
        if(mRange.getCarrierId()==null) {
                long _from=Long.parseLong(mRange.get_From());
                for (BigInteger bi = BigInteger.valueOf(_from); bi.compareTo(_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                        LocalDate localDateTime = LocalDate.now();
                        String number=String.valueOf(bi);
                        Random random = new Random(mRange.getRangeId(),number, mRange.getCarrierId(), mRange.getAssignmentId(), localDateTime);
                        randomRepository.save(random);
                }

        }
        BigInteger _from=new BigInteger(mRange.get_From());
        BigInteger difference=_to.subtract(_from);
        int diff=difference.intValue()+1;
        mRange.setCount(diff);
        mRangeRepository.save(mRange);
        return mRangeRepository.findAll();
    }




    @GetMapping(value="/random/findByNumber")
    public Page<Random> getAllRandom(@RequestParam("number") String number,Pageable pageRequest){
        Page<Random> randoms = null;
        if(number==null || number.equalsIgnoreCase("")) {
            randoms=randomRepository.findAll(pageRequest);
        }
        else{
            randoms=randomRepository.findByNumberContainingIgnoreCase(number, pageRequest);
        }
        return randoms;
    }

    @GetMapping(value="/random/findByPrefix")
    public List<Random> getAllList(@RequestParam("prefix") String prefix){
        List<Random> randoms=new ArrayList<>();
        if(prefix!=null && !prefix.equalsIgnoreCase("")){
            randoms=randomRepository.findAllByNumberStartingWith(prefix);
        }
        return randoms;
    }

//http://localhost:8080/api/random/findByNumber?number=9613000000&size=10&page=0&sort=randomId%2Casc


    @GetMapping(value="/assignments/findByPrefix")
    public Page<Assignment> getAllAssignments(@RequestParam("prefix") String prefix,Pageable pageable){
        Page<Assignment> assignments=null;
        if(prefix==null || prefix.equalsIgnoreCase("") ){
            assignments=assignmentRepository.findAll(pageable);
        }
        else{
            assignments=assignmentRepository.findByPrefixContainingIgnoreCase(prefix,pageable);
        }
        return  assignments;
    }
}
