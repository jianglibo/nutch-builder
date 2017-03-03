package com.jianglibo.nutchbuilder.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UriPatternController {
    
    @RequestMapping(value = {"/llapp/kkk/**/{noext:[^.]*}" }, method = RequestMethod.GET)
    public ResponseEntity<String> llapp(@PathVariable String noext) {
        return ResponseEntity.ok(noext);
    }
    
}
