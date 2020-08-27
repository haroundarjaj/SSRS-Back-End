package com.haroun.ssrs.service;

import com.haroun.ssrs.model.WebServiceSource;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.WebServiceSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebServiceSourceServiceImpl implements WebServiceSourceService {

    @Autowired
    WebServiceSourceRepository webServiceSourceRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Boolean testConnection(WebServiceSource WSs) {
        return true;
    }
}
