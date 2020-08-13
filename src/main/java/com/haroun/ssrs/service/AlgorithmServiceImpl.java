package com.haroun.ssrs.service;


import com.haroun.ssrs.model.Algorithms;
import com.haroun.ssrs.repository.AlgorithmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AlgorithmServiceImpl implements AlgorithmService {

    private AlgorithmRepository algorithmRepository;
    public AlgorithmServiceImpl(AlgorithmRepository algorithmRepository){
        this.algorithmRepository = algorithmRepository;
    }
    @Override
    public List<Algorithms> getAllAlgorithm() {
        return this.algorithmRepository.findAll();
    }

    @Override
    public Algorithms saveAlgorithm(Algorithms algorithms) {
        Algorithms algo = new Algorithms();
        algo.setAlgoDescription(algorithms.getAlgoDescription());
        algo.setAlgoName(algorithms.getAlgoName());
        algo.setAlgoType(algorithms.getAlgoType());
        algo.setCreated_at(new Date());
        algo.setAlgoFormula(algorithms.getAlgoFormula());
        return algorithmRepository.save(algo);
    }

    @Override
    public Boolean deleteAlgorithm(String algoId) {
        return algorithmRepository.findById(algoId).map(al ->{
            algorithmRepository.delete(al);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete Algorithm"));
    }

    @Override
    public Algorithms updateAlgorithms(String algoId, Algorithms algorithms) {
        return algorithmRepository.findById(algoId).map(al ->{
            al.setAlgoDescription(algorithms.getAlgoDescription());
            al.setAlgoName(algorithms.getAlgoName());
            al.setAlgoType(algorithms.getAlgoType());
            al.setCreated_at(new Date());
            al.setAlgoFormula(algorithms.getAlgoFormula());
            return algorithmRepository.save(al);
        }).orElseThrow(()-> new ExceptionMessage("Impossible to update Algorithm"));
    }
}
