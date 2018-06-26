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
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        // from= 9613000000 // to=9613000010
        BigInteger _to=new BigInteger(mRange.get_To());
        // its random
        if(mRange.getCarrierId()==null) {
            long _from=Long.parseLong(mRange.get_From());
            // loop from _from to _to
            for (BigInteger bi = BigInteger.valueOf(_from); bi.compareTo(_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                // get the current date
                LocalDate localDateTime = LocalDate.now();
                // number is the bi
                String number=String.valueOf(bi);
                // create new random and set values to it
                Random random=new Random();
                random.setRangeId(mRange.getRangeId());
                random.setNumber(number);
                random.setCarrierId(mRange.getCarrierId());
                random.setAssignmentId(mRange.getAssignmentId());
                random.setLastUsed(localDateTime);
                // save the random
                randomRepository.save(random);
            }
        }
        // if it skip if statement then its carrier
        // get count of numbers between from and to
        BigInteger _from=new BigInteger(mRange.get_From());
        BigInteger difference=_to.subtract(_from);
        int diff=difference.intValue()+1;
        // get the count and set it
        mRange.setCount(diff);
        // save range
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

    @GetMapping(value="/randoms/findByPrefix")
    public List<Random> getAllRandomsByPrefix(@RequestParam("number") String number){
        List<Random> randoms=new ArrayList<>();
        if(number!=null && !number.equalsIgnoreCase("")){
            // get all free random number according to prefix that are not assigned to carrier
            randoms=randomRepository.findAllByNumberStartingWithAndCarrierIdIsNull(number);
        }
        return randoms;
    }


    

    @PostMapping(value="/assignments/saveAssignment")
    public List<Random> saveAndgetRandoms(@RequestBody Assignment assignment){
        List<Random> Random_List=new ArrayList<>();
        if(assignment.getPrefix()!=null && !assignment.getPrefix().equalsIgnoreCase("")){

            // get free array list of randoms that start with certain prefix and have carrier id null ( not assigned to carrier )
            List<Random>randoms=randomRepository.findAllByNumberStartingWithAndCarrierIdIsNull(assignment.getPrefix());

            // get only first 4 chars from the string because upon assigning single random we send number 9613000000 for example and prefix is 9613
            if(assignment.getPrefix().length()>4){
                // set the prefix in assignment
                assignment.setPrefix(assignment.getPrefix().substring(0,4));
            }

            // save the assignment
            Assignment assignment1=assignmentRepository.save(assignment);

            // get the subset of array list according to count
            Random_List=randoms.subList(0,assignment.getCount() );
            // loop throught the random list
            for(Random random:Random_List){
                // update carrier id and assignment id
                random.setCarrierId(assignment1.getCarrierId());
                random.setAssignmentId(assignment1.getAssignmentId());
                // save the random
                randomRepository.save(random);
            }
        }
        return Random_List;
    }



    // deleting single random by clicking delete button
    @DeleteMapping(value = "/randoms/deleteRandoms")
    public Random DeleteRandom(@RequestBody Random _random){
        _random.setCarrierId(null);
        _random.setAssignmentId(0);
        randomRepository.save(_random);
        Assignment assignment=assignmentRepository.findOne(_random.getAssignmentId());
        // if the count is greater than 1 then update count by decreasing it by 1 else delete the assignment
        if(assignment.getCount()>1){
            // set the count by decreasing it by 1
            assignment.setCount(assignment.getCount()-1);
            // save the assignment
            assignmentRepository.save(assignment);
        }
        else{
            // delete assignment
            assignmentRepository.delete(assignment);
        }
        return _random;
    }

}

