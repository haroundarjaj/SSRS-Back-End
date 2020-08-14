package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.Formula;
import com.haroun.ssrs.service.FormulaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/formula")
public class FormulaController {

    FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    @GetMapping("/getAll")
    public List<Formula> getAllFormulas (Authentication authentication) {
        return formulaService.getAllFormulas(authentication.getName());
    }

    @PostMapping("/insert")
    public String insertFormula (@RequestBody Formula formula, Authentication authentication) {
        return formulaService.insertFormula(formula, authentication.getName());
    }

    @PostMapping("/solve")
    public List<Object> solveFormula (@RequestBody List<Object> elements) {
        return formulaService.solveFormula(elements);
    }

    @PostMapping("/delete")
    public void deleteFormula (@RequestBody Formula formula) { formulaService.deleteFormula(formula); }
}
