package com.haroun.ssrs.service;

import com.haroun.ssrs.repository.ChartRepository;
import com.haroun.ssrs.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ChartServiceImpl{

    @Resource
    MongoTemplate mongoTemplate;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    ChartRepository chartRepository;

}
