package com.haroun.ssrs.service;

import com.haroun.ssrs.model.Formula;

import java.util.List;

public interface FormulaService {

    List<Formula> getAllFormulas (String userEmail);

    String insertFormula(Formula formula, String userEmail);

    Boolean deleteFormula (long id);

    List<Object> solveFormula(List<Object> elements);
}
