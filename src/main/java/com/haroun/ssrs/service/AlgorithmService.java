package com.haroun.ssrs.service;



import com.haroun.ssrs.model.Algorithms;

import java.util.List;

public interface AlgorithmService {
    List<Algorithms> getAllAlgorithm();
    Algorithms saveAlgorithm(Algorithms algorithms, String owner);
    Boolean deleteAlgorithm(String algoId);
    Algorithms updateAlgorithms(String algoId, Algorithms algorithms);
    List<Algorithms> getAlgorithmByUser(String owner);
}
