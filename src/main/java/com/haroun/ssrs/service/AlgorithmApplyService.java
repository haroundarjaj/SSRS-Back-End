package com.haroun.ssrs.service;

import java.util.List;

public interface AlgorithmApplyService {
    List<Object> applyAlgorithm(List<Object> data, String result, String algoId, String[] variables);
}
