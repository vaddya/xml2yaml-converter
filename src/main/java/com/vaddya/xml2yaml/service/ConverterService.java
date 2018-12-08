package com.vaddya.xml2yaml.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

@Service
public class ConverterService {

    private XmlMapper xmlMapper;

    private YAMLMapper yamlMapper;

    public ConverterService() {
        xmlMapper = new XmlMapper();
        yamlMapper = new YAMLMapper();
    }

    /**
     * Parse map from given XML
     *
     * @param xml string containing XML
     * @return parsed map
     * @throws IOException if a problem occurred
     */
    public Map<String, Object> parseXml(String xml) throws IOException {
        Map<String, Object> data = xmlMapper.readValue(xml, Map.class);
        for (var entry : data.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return data;
    }

    /**
     * Format given data to YAML format
     *
     * @param data map to be converted
     * @return string containing formatted YAML
     * @throws IOException if a problem occurred
     */
    public String formatYaml(Map<String, Object> data) throws IOException {
        StringWriter stringWriter = new StringWriter();
        yamlMapper.writeValue(stringWriter, data);
        return stringWriter.toString();
    }

}
