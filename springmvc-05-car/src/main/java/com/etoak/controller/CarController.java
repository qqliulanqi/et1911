package com.etoak.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.etoak.bean.Car;
import com.etoak.service.CarService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {
	@Autowired
	CarService carService;
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "car/add";
	}
	@RequestMapping("/add")
	public String add(MultipartFile file,Car car) throws IllegalStateException, IOException {
		String originalFilename = file.getOriginalFilename();
		log.info("文件名{}",originalFilename);
		log.info("param car-{}",car);
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		String newFilename = uuid+"_"+originalFilename;
		File destFile = new File("d:/upload",newFilename);
		file.transferTo(destFile);
		car.setPic("/pic/"+newFilename);
		carService.addCar(car);
		return "redirect:/car/toAdd";
	}
}
