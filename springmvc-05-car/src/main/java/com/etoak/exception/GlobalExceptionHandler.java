package com.etoak.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(Et1911LoginEcxeption.class)
	public ModelAndView handlLoginException(Et1911LoginEcxeption e) {
		String msg = e.getMessage();
		log.error(msg,e);
		ModelAndView mv = new ModelAndView();
		mv.addObject("error",msg);
		mv.setViewName("login");
		return mv;
	}
	@ExceptionHandler(ParamException.class)
	public ModelAndView handlepParamException(ParamException e) {
		String message = e.getMessage();
		log.info(message,e);
		ModelAndView mv = new ModelAndView();
		mv.addObject("paramError",message);
		mv.setViewName("car/add");
		return mv;
	}
}
