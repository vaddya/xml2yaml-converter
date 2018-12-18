package com.vaddya.xml2yaml.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.xmldeser.ArrayInferringUntypedObjectDeserializer;
import com.google.xmldeser.RootSniffingXMLStreamReader;

@Service
public class ConverterService {

    private XmlMapper xmlMapper;

    private YAMLMapper yamlMapper;

    public ConverterService() {
        xmlMapper = new XmlMapper();
        Module module = new SimpleModule().addDeserializer(
                Object.class, new ArrayInferringUntypedObjectDeserializer()
        ); // jackson xml array support
        xmlMapper.registerModule(module);
        yamlMapper = new YAMLMapper();
    }

    /**
     * Parse map from given XML
     *
     * @param xml string containing XML
     * @return parsed map
     * @throws IOException if a problem occurred
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> parseXml(String xml) throws IOException, XMLStreamException {
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        RootSniffingXMLStreamReader reader = new RootSniffingXMLStreamReader(
                XMLInputFactory.newFactory().createXMLStreamReader(stream)
        ); // save xml root name
        Map<String, Object> data = xmlMapper.readValue(reader, Map.class);
        Map<String, Object> dataWithRoot = new HashMap<>();
        dataWithRoot.put(reader.getLocalNameForRootElement(), data);
        return dataWithRoot;
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
        String yaml = stringWriter.toString();
        yaml = yaml.substring(yaml.indexOf("---") + 4); // remove three leading dashes
        return yaml;
    }

}
