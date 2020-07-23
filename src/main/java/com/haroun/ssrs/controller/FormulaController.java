package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.Formula;
import com.haroun.ssrs.service.FormulaService;
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

    @GetMapping("/getAll&{userEmail}")
    public List<Formula> getAllFormulas (@PathVariable("userEmail") String userEmail) {
        return formulaService.getAllFormulas(userEmail);
    }

    @PostMapping("/insert&{userEmail}")
    public String insertFormula (@RequestBody Formula formula, @PathVariable("userEmail") String userEmail) {
        return formulaService.insertFormula(formula, userEmail);
    }

    @PostMapping("/solve")
    public List<Object> solveFormula (@RequestBody List<Object> elements) {
        return formulaService.solveFormula(elements);
    }

    @PostMapping("/delete")
    public void deleteFormula (@RequestBody Formula formula) { formulaService.deleteFormula(formula); }
}
