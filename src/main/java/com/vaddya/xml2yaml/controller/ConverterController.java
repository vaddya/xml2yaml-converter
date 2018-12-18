package com.vaddya.xml2yaml.controller;

import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.vaddya.xml2yaml.service.ConverterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@Api
@RestController
public class ConverterController {

    @Autowired
    private ConverterService converterService;

    @ApiOperation(value = "XML to YAML Converter")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Converted XML to YAML"),
            @ApiResponse(code = 400, message = "Invalid XML"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(
            value = "/convert",
            consumes = "application/xml",
            produces = "application/x-yaml"
    )
    public ResponseEntity<String> convert(
            @ApiParam(
                    name = "xml",
                    value = "XML to convert",
                    required = true,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "<note><to>Tove</to><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>"
                            )
                    )
            )
            @RequestBody String xml) {
        try {
            Map<String, Object> data = converterService.parseXml(xml);
            String yaml = converterService.formatYaml(data);
            return new ResponseEntity<>(yaml, HttpStatus.OK);
        } catch (JsonParseException | XMLStreamException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
