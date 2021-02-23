package com.example.demo;

import javax.transaction.Transactional;

@Transactional
public interface TraineeRepository extends UserBaseRepository<Trainee> { }

