package com.jianglibo.nutchbuilder.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jianglibo.nutchbuilder.config.KatharsisProcessor;

//@Controller
public class KatharsisController {

	@Autowired
	private KatharsisProcessor processor;
	
	@RequestMapping(value="/jsonapi/**")
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			processor.process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw(e);
		}
	}
}
