package com.haroun.ssrs.controller;


import com.haroun.ssrs.model.Algorithms;
import com.haroun.ssrs.service.AlgorithmApplyService;
import com.haroun.ssrs.service.AlgorithmService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin("*")
public class AlgorithmsController {
    private AlgorithmService algorithmService;
    private AlgorithmApplyService algorithmApplyService;
    public AlgorithmsController(AlgorithmService algorithmService, AlgorithmApplyService algorithmApplyService){
        this.algorithmService = algorithmService;
        this.algorithmApplyService = algorithmApplyService;
    }
    @RequestMapping(path = "algorithms",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Algorithms> getAllAlgorithms(){
        return this.algorithmService.getAllAlgorithm();
    }
    @RequestMapping(path = "algorithm",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Algorithms saveAlgorithms(@RequestBody Algorithms algorithm){
        return this.algorithmService.saveAlgorithm(algorithm);
    }

    @PostMapping("/import/database/test/{result}/{algoId}")
    public List<Object> Test(@RequestBody List<Object> allData, @PathVariable(value = "result") String result,
                       @PathVariable(value = "algoId") String algoId,
                       @PathParam(value = "variables") String variables){
        String[] var = variables.split("-");
        return this.algorithmApplyService.applyAlgorithm(allData, result, algoId, var);
    }

    @DeleteMapping("/algorithm/delete/{algoId}")
    public Boolean deleteAlgorithm(@PathVariable(value = "algoId") String algoId){
        return this.algorithmService.deleteAlgorithm(algoId);
    }
}
