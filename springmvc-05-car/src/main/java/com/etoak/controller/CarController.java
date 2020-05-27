package com.etoak.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.etoak.bean.Car;
import com.etoak.bean.CarVo;
import com.etoak.bean.PageVo;
import com.etoak.exception.ParamException;
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
	@RequestMapping("toList")
	public String toList() {
		return "car/list";
	}
	@RequestMapping("/add")
	/* 多个bean必须一个bean挨着一个bindingresult */
	public String add(MultipartFile file,@Valid Car car,BindingResult bindingResult,HttpServletRequest request) throws IllegalStateException, IOException {
		String originalFilename = file.getOriginalFilename();
		log.info("文件名{}",originalFilename);
		log.info("param car-{}",car);
		
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		if(!org.springframework.util.CollectionUtils.isEmpty(allErrors)) {
			StringBuffer errorBuf = new StringBuffer();
			for(ObjectError error:allErrors) {
				String errorMsg = error.getDefaultMessage();
				errorBuf.append(errorMsg).append(";");
			}
			/*
			 * request.setAttribute("paramError", errorBuf.toString()); return
			 * "forward:/car/toAdd";
			 */
			throw new ParamException(errorBuf.toString());
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		String newFilename = uuid+"_"+originalFilename;
		File destFile = new File("d:/upload",newFilename);
		file.transferTo(destFile);
		car.setPic("/pic/"+newFilename);
		carService.addCar(car);
		return "redirect:/car/toAdd";
	}
	
	@GetMapping("/list")
	@ResponseBody
	public PageVo<CarVo> queryList(@RequestParam(required=false,defaultValue = "1" )int pageNum,
			@RequestParam(required = false,defaultValue = "8" )int pageSize,CarVo carVo,String[] priceList){
			System.out.println(priceList);
		return carService.queryList(pageNum, pageSize, carVo,priceList);
	}
	
	@GetMapping("/getBrand")
	@ResponseBody
	public List<String> getBrand(){
		return carService.getAllBrand();
	}
	@GetMapping("/getSeries")
	@ResponseBody
	public List<String> getSeries(String brand){
		System.out.println(brand);
		return carService.getSeriesByBrand(brand);
	}
}
