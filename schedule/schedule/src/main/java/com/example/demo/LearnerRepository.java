package com.example.demo;

import javax.transaction.Transactional;

@Transactional
public interface LearnerRepository extends UserBaseRepository<Learner> { }

