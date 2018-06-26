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
        BigInteger _to=new BigInteger(mRange.get_To());
        if(mRange.getCarrierId()==null) {
            long _from=Long.parseLong(mRange.get_From());
            for (BigInteger bi = BigInteger.valueOf(_from); bi.compareTo(_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                LocalDate localDateTime = LocalDate.now();
                String number=String.valueOf(bi);
                Random random=new Random();
                random.setRangeId(mRange.getRangeId());
                random.setNumber(number);
                random.setCarrierId(mRange.getCarrierId());
                random.setAssignmentId(mRange.getAssignmentId());
                random.setLastUsed(localDateTime);
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
            randoms=randomRepository.findAllByNumberStartingWithAndCarrierIdIsNull(number);
        }
        return randoms;
    }

    @PostMapping(value="/assignments/saveAssignment")
    public List<Random> saveAndgetRandoms(@RequestBody Assignment assignment){
        List<Random> Random_List=new ArrayList<>();
        if(assignment.getPrefix().length()>4){
            assignment.setPrefix(assignment.getPrefix().substring(0,4));
        }
        Assignment assignment1=assignmentRepository.save(assignment);
        if(assignment.getPrefix()!=null && !assignment.getPrefix().equalsIgnoreCase("")){
            List<Random>randoms=randomRepository.findAllByNumberStartingWithAndCarrierIdIsNull(assignment.getPrefix());
            Random_List=randoms.subList(0,assignment.getCount() );
            for(Random random:Random_List){
                random.setCarrierId(assignment1.getCarrierId());
                random.setAssignmentId(assignment1.getAssignmentId());
                randomRepository.save(random);
            }
        }
        return Random_List;
    }




    @DeleteMapping(value = "/randoms/deleteRandoms")
    public Random DeleteRandom(@RequestBody Random _random){
        Random random=randomRepository.findOne(_random.getRandomId());
        random.setCarrierId(null);
        random.setAssignmentId(0);
        randomRepository.save(random);
        Assignment assignment=assignmentRepository.findOne(_random.getAssignmentId());
        if(assignment.getCount()>1){
            assignment.setCount(assignment.getCount()-1);
            assignmentRepository.save(assignment);
        }
        else{
            assignmentRepository.delete(assignment);
        }
        return random;
    }

}

