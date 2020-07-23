package com.haroun.ssrs.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Formula;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.FormulaRepository;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormulaServiceImpl implements FormulaService {

    @Autowired
    FormulaRepository formulaRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public List<Formula> getAllFormulas(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<Formula> formulas = new ArrayList<>(formulaRepository.findAllByUser(user));
        Collections.reverse(formulas);
        return formulas;
    }

    @Override
    public String insertFormula(Formula formula, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        formula.setUser(user);
        formula.setFormulaId(sequenceGenerator.generateSequence(formula.SEQUENCE_NAME));
        formulaRepository.save(formula);
        return "done";
    }

    @Override
    public void deleteFormula(Formula formula) { formulaRepository.delete(formula); }

    @Override
    public List<Object> solveFormula(List<Object> elements) {
        List<Object> data = (List<Object>) elements.get(3);
        List<Object> inputs = (List<Object>) elements.get(1);
        List<Object> result = new ArrayList<>();
        String equation = (String) elements.get(0);
        String columnName = (String) elements.get(2);
        AtomicInteger iter = new AtomicInteger();
        return data.parallelStream().map((obj) -> {
            Gson gson = new Gson();
            iter.getAndIncrement();
            System.out.println(iter);
            // JsonObject dataObj = parseToJsonObject(obj);
            String dt = gson.toJson(obj);
            JsonObject dataObj = gson.fromJson(dt, JsonObject.class);
            Argument[] arguments = new Argument[inputs.size()];
            inputs.forEach((input) -> {
                JsonObject inputObj = parseToJsonObject(input);
                String variable = inputObj.get("variable").getAsString();
                int id = inputObj.get("id").getAsInt();
                float value = dataObj.get(inputObj.get("column").getAsString()).getAsFloat();
                arguments[id] = new Argument(variable, value);

            });
            Expression e = new Expression(equation, arguments);
            dataObj.addProperty(columnName, e.calculate());
            // Object finalObj = new Gson().fromJson(dataObj.toString(), Object.class);
            // result.add(finalObj);
            return gson.fromJson(dataObj, Object.class);
        }).collect(Collectors.toList());

    }

    public JsonObject parseToJsonObject(Object obj) {
        String objString = new Gson().toJson(obj);
        JsonObject jsonObj = new Gson().fromJson(objString, JsonObject.class);;
        return jsonObj;
    }
}
