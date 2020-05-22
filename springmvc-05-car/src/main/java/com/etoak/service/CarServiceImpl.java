package com.etoak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoak.bean.Car;
import com.etoak.mapper.CarMapper;
@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	CarMapper carMapper;
	@Override
	public int addCar(Car car) {
		// TODO Auto-generated method stub
		return carMapper.addCar(car);
	}

}
