package com.haroun.ssrs.service;


import com.haroun.ssrs.model.Algorithms;
import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.repository.AlgorithmRepository;
import com.haroun.ssrs.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AlgorithmServiceImpl implements AlgorithmService {
    private AppUserRepository appUserRepository;
    private AlgorithmRepository algorithmRepository;
    public AlgorithmServiceImpl(AlgorithmRepository algorithmRepository, AppUserRepository appUserRepository){
        this.algorithmRepository = algorithmRepository;
        this.appUserRepository = appUserRepository;
    }
    @Override
    public List<Algorithms> getAllAlgorithm() {
        return this.algorithmRepository.findAll();
    }

    @Override
    public Algorithms saveAlgorithm(Algorithms algorithms, String owner) {
        AppUser user = appUserRepository.findByEmail(owner);
        if (user != null) {
            Algorithms algo = new Algorithms();
            algo.setAlgoDescription(algorithms.getAlgoDescription());
            algo.setAlgoName(algorithms.getAlgoName());
            algo.setAlgoType(algorithms.getAlgoType());
            algo.setCreated_at(new Date());
            algo.setAlgoFormula(algorithms.getAlgoFormula());
            algo.setUser(user);
            return algorithmRepository.save(algo);
        } else {
            throw new ExceptionMessage("Cannot find the user and save algorithm");
        }
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

    @Override
    public List<Algorithms> getAlgorithmByUser(String owner) {
        AppUser user = appUserRepository.findByEmail(owner);
        if (user != null) {
            return algorithmRepository.findAllByUser(user);
        } else {
            throw new ExceptionMessage("Cannot find the user and get his algorithms");
        }
    }
}
