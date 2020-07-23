package com.haroun.ssrs.service;

import com.haroun.ssrs.model.Formula;

import java.util.List;

public interface FormulaService {

    List<Formula> getAllFormulas (String userEmail);

    String insertFormula(Formula formula, String userEmail);

    void deleteFormula (Formula formula);

    List<Object> solveFormula(List<Object> elements);
}
