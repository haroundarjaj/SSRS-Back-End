package com.haroun.ssrs.service;



import com.haroun.ssrs.model.Algorithms;

import java.util.List;

public interface AlgorithmService {
    List<Algorithms> getAllAlgorithm();
    Algorithms saveAlgorithm(Algorithms algorithms);
    Boolean deleteAlgorithm(String algoId);
    Algorithms updateAlgorithms(String algoId, Algorithms algorithms);
}
