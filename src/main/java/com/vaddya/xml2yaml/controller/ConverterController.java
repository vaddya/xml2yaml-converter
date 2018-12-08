package com.vaddya.xml2yaml.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vaddya.xml2yaml.service.ConverterService;

@RestController
public class ConverterController {

    @Autowired
    private ConverterService converterService;

    @PostMapping(value = "/convert", consumes = "application/xml", produces = "application/yaml")
    public ResponseEntity<String> convert(@RequestBody String xml) {
        try {
            Map<String, Object> data = converterService.parseXml(xml);
            String yaml = converterService.formatYaml(data);
            return new ResponseEntity<>(yaml, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
