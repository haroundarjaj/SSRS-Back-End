package com.haroun.ssrs.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.haroun.ssrs.model.Algorithms;
import com.haroun.ssrs.repository.AlgorithmRepository;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlgorithmApplyServiceImpl implements AlgorithmApplyService {
    private AlgorithmRepository algorithmRepository;

    public AlgorithmApplyServiceImpl(AlgorithmRepository algorithmRepository){
        this.algorithmRepository = algorithmRepository;
    }
    @Override
    public List<Object> applyAlgorithm(List<Object> data, String result,long algoId, String[] variables){
        Algorithms algo = this.algorithmRepository.findById(algoId).orElseThrow(()-> new ExceptionMessage("Impossible to find Algorithm"));
        Gson gson = new Gson();
        if (algo.getAlgoType().equals("integrate")) {
            return data.parallelStream().map(d -> {
                String dt = gson.toJson(d);
                JsonObject obj = gson.fromJson(dt, JsonObject.class);
                Expression e = new Expression(algo.getAlgoFormula());
                String[] args = e.getMissingUserDefinedArguments();
                Arrays.sort(args);
                System.out.println("les variable"+ Arrays.toString(args));
                Expression expression = new Expression("int("+algo.getAlgoFormula()+" ," + args[0] + "," + obj.get(variables[0]).getAsDouble() + "," + obj.get(variables[1]).getAsDouble() + ")" );
                obj.addProperty(result, expression.calculate());
                return gson.fromJson(obj, Object.class);
            }).collect(Collectors.toList());
        } else {
            return data.parallelStream().map(d -> {
                String dt = gson.toJson(d);
                JsonObject obj = gson.fromJson(dt, JsonObject.class);
                Expression expression = new Expression(algo.getAlgoFormula());
                String[] args = expression.getMissingUserDefinedArguments();
                Arrays.sort(args);
                for (int i=0; i< variables.length; i++){
                    Argument arg = new Argument(args[i], obj.get(variables[i]).getAsDouble());
                    expression.addArguments(arg);
                }
                obj.addProperty(result, expression.calculate());
                return gson.fromJson(obj, Object.class);
            }).collect(Collectors.toList());
        }
    }
}
