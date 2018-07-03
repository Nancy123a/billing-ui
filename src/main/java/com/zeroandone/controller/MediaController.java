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
        // get count of numbers between from and to
        BigInteger _from=new BigInteger(mRange.get_From());
        BigInteger difference=_to.subtract(_from);
        int diff=difference.intValue()+1;
        // get the count and set it
        mRange.setCount(diff);
        // save range
        Range mRange1=mRangeRepository.save(mRange);

        // its random
        if(mRange.getCarrierId()==null) {
            long from=Long.parseLong(mRange.get_From());
            // loop from _from to _to
            for (BigInteger bi = BigInteger.valueOf(from); bi.compareTo(_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                // get the current date
                LocalDate localDateTime = LocalDate.now();
                // number is the bi
                String number=String.valueOf(bi);
                // create new random and set values to it
                Random random=new Random();
                random.setRangeId(mRange1.getRangeId());
                random.setNumber(number);
                random.setCarrierId(mRange1.getCarrierId());
                random.setAssignmentId(mRange1.getAssignmentId());
                random.setLastUsed(localDateTime);
                // save the random
                randomRepository.save(random);
            }
        }
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
        List<Random> distinct_randoms=new ArrayList<>();
        List<Random> Random_List=new ArrayList<>();
        List<Random> _randoms=new ArrayList<>();
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

            distinct_randoms = getRandomDistinctRangeId(randoms, Random_List);
            if(distinct_randoms.size()==1){

                // 9613000000 9613000010
                Random random = distinct_randoms.get(0);
                Range range = mRangeRepository.findOne(random.getRangeId());
                mRangeRepository.delete(range);

                // 9613000005
                Range range1=new Range(assignment1.getCarrierId(),"Carrier",distinct_randoms.get(0).getNumber(),distinct_randoms.get(0).getNumber(),assignment1.getAssignmentId(),1);
                mRangeRepository.save(range1);

                Random random1=Random_List.get(0);
                random1.setRangeId(range1.getRangeId());
                randomRepository.save(random1);

                BigInteger _From_range1=new BigInteger(range1.get_From()); //9613000005
                BigInteger _To_range1=new BigInteger(range1.get_To()); //9613000005

                BigInteger _From_range=new BigInteger(range.get_From()); //9613000000
                BigInteger _To_range=new BigInteger(range.get_To()); //9613000010

                if(!range1.get_From().equalsIgnoreCase(range.get_From()) && _From_range1.compareTo(_From_range)>0){

                    BigInteger _nb=_From_range1.subtract(BigInteger.ONE);
                    BigInteger count = _nb.subtract(_From_range);
                    int diff = count.intValue() + 1;

                    Range range2=new Range(null,"Random",_nb.toString(),_From_range.toString(),0,diff);
                    mRangeRepository.save(range2);

                    for (BigInteger bi =_From_range; bi.compareTo(_nb) <= 0; bi = bi.add(BigInteger.ONE)) {
                        Random random2=randomRepository.findByNumberEquals(bi.toString());
                        random2.setRangeId(range2.getRangeId());
                        randomRepository.save(random2);
                    }

                }
                if(!range1.get_To().equalsIgnoreCase(range.get_To()) && _To_range.compareTo(_To_range1)>0){

                    BigInteger _nb1=_To_range1.add(BigInteger.ONE);
                    BigInteger _nb2=_To_range.subtract(_nb1);
                    int diff=_nb2.intValue()+1;
                    Range range2=new Range(null,"Random",_To_range.toString(),_nb1.toString(),0,diff);
                    mRangeRepository.save(range2);

                    for (BigInteger bi =_nb1; bi.compareTo(_To_range) <= 0; bi = bi.add(BigInteger.ONE)) {
                        Random random2=randomRepository.findByNumberEquals(bi.toString());
                        random2.setRangeId(range2.getRangeId());
                        randomRepository.save(random2);
                    }

                }
            }
            else {
             List<Random>  Distinct_Randoms=getDistinctRandoms(distinct_randoms);
             for(Random random:Distinct_Randoms){
                 Range range=mRangeRepository.findOne(random.getRangeId());
                 _randoms= getRandoms(range.getRangeId(),Random_List);
                 BigInteger _from=new BigInteger(range.get_From());
                 BigInteger _to=new BigInteger(range.get_To());
                 BigInteger big2=null;
                      if(_randoms.size()>0) {
                         big2=new BigInteger(_randoms.get(_randoms.size() - 1).getNumber());
                      }
                      else{
                          big2=new BigInteger(_randoms.get(0).getNumber());
                      }

                 if(!_randoms.get(0).getNumber().equalsIgnoreCase(range.get_To()) && _to.compareTo(big2)>0){

                     BigInteger count = big2.subtract(_from);
                     int diff = count.intValue() + 1;

                     Range range2=new Range(assignment1.getCarrierId(),"Carrier",big2.toString(),_from.toString(),assignment1.getAssignmentId(),diff);
                     Range _range=mRangeRepository.save(range2);

                     for(Random random1:_randoms){
                         random1.setRangeId(_range.getRangeId());
                         randomRepository.save(random1);
                     }

                     range.setAssignmentId(0);
                     range.setCarrierId(null);
                     range.setRangeType("Random");
                     mRangeRepository.save(range);

                 }
                 else{
                     range.setRangeType("Carrier");
                     range.setCarrierId(assignment1.getCarrierId());
                     range.setAssignmentId(assignment1.getAssignmentId());
                     mRangeRepository.save(range);
                 }
             }
            }
        }
        return Random_List;
    }

    public List<Random> getRandoms(int range_id,List<Random> randoms){
        List<Random> sub_randoms=new ArrayList<>();
        for(Random random:randoms){
            if(random.getRangeId()==range_id) {
                sub_randoms.add(random);
            }

        }
        return sub_randoms;
    }

    public List<Random> getDistinctRandoms(List<Random> randoms){
        List<Random> distinctRandoms=new ArrayList<>();
        for(int i=0;i<randoms.size();i++){
            if(i==0){
                distinctRandoms.add(randoms.get(0));
            }
            else{
                if(randoms.get(i).getRangeId()!=randoms.get(i-1).getRangeId()){
                    distinctRandoms.add(randoms.get(i));
                }
            }
        }
        return distinctRandoms;
    }


    public List<Random> getRandomDistinctRangeId(List<Random> randoms,List<Random> randomList){
        List<Random> randoms1=new ArrayList<>();
        for(int i=0;i<randoms.size();i++){
            if(i==0){
                randoms1.add(randoms.get(0));
            }
            else{
                if(randoms.get(i-1).getRangeId()!=randoms.get(i).getRangeId()){
                    randoms1.add(randoms.get(i));
                }
            }
        }
        return randoms1;
    }

    // deleting single random by clicking delete button
    @DeleteMapping(value = "/randoms/deleteRandoms")
    public Assignment DeleteRandom(@RequestBody Random random){



        Assignment assignment=assignmentRepository.findOne(random.getAssignmentId());
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

        Range range=mRangeRepository.findOne(random.getRangeId());
        BigInteger range_From=new BigInteger(range.get_From());
        BigInteger range_to=new BigInteger(range.get_To());
        BigInteger _random_range_id=new BigInteger(random.getNumber());
        if(_random_range_id.compareTo(range_From)>0 && _random_range_id.compareTo(range_to)<0){

            BigInteger nb1=_random_range_id.subtract(BigInteger.ONE);
            BigInteger nb2=_random_range_id.add(BigInteger.ONE);

            BigInteger count = nb1.subtract(range_From);
            int diff = count.intValue() + 1;

            range.set_From(range_From.toString());
            range.set_To(nb1.toString());
            range.setRangeType("Carrier");
            range.setCount(diff);
            mRangeRepository.save(range);

            BigInteger count2 = range_to.subtract(nb2);
            int diff2 = count2.intValue() + 1;

            Range range3=new Range(random.getCarrierId(),"Carrier",range_to.toString(),nb2.toString(),random.getAssignmentId(),diff2);
            Range _raa=mRangeRepository.save(range3);

            for (BigInteger bi = nb2; bi.compareTo(range_to) <= 0; bi = bi.add(BigInteger.ONE)) {
                Random random1=randomRepository.findByNumberEquals(bi.toString());
                random1.setRangeId(_raa.getRangeId());
                randomRepository.save(random1);
            }

            Range range2=new Range(null,"Random",random.getNumber(),random.getNumber(),0,1);
            Range _ra=mRangeRepository.save(range2);

            random.setCarrierId(null);
            random.setAssignmentId(0);
            random.setRangeId(_ra.getRangeId());
            randomRepository.save(random);
        }
        else if(_random_range_id.compareTo(range_From)==0){

            BigInteger nb= range_From.add(BigInteger.ONE);
            range.set_From(String.valueOf(nb));
            range.setRangeType("Carrier");
            mRangeRepository.save(range);

            Range range2=new Range(null,"Random",random.getNumber(),random.getNumber(),0,1);
            Range _ra=mRangeRepository.save(range2);

            random.setCarrierId(null);
            random.setAssignmentId(0);
            random.setRangeId(_ra.getRangeId());
            randomRepository.save(random);

        }

        else if(_random_range_id.compareTo(range_to)==0){
            BigInteger nb= range_to.subtract(BigInteger.ONE);
            range.set_To(String.valueOf(nb));
            range.setRangeType("Carrier");
            mRangeRepository.save(range);

            Range range2=new Range(null,"Random",random.getNumber(),random.getNumber(),0,1);
            Range _ra=mRangeRepository.save(range2);

            random.setCarrierId(null);
            random.setAssignmentId(0);
            random.setRangeId(_ra.getRangeId());
            randomRepository.save(random);
        }
        return assignment;
    }

}

