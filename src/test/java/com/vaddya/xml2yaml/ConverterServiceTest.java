package com.vaddya.xml2yaml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.vaddya.xml2yaml.service.ConverterService;

public class ConverterServiceTest {

    private ConverterService converterService = new ConverterService();

    private Map<String, Object> note;

    @Before
    public void init() {
        note = new LinkedHashMap<>();
        var info = new LinkedHashMap<String, Object>();
        info.put("to", "Tove");
        info.put("from", "Jani");
        info.put("heading", "Reminder");
        info.put("body", "Don't forget me this weekend!");
        note.put("note", info);
    }

    @Test
    public void testXmlParse() throws IOException, XMLStreamException {
        var xmlFile = getClass().getClassLoader().getResourceAsStream("note.xml");
        var xml = IOUtils.toString(xmlFile, StandardCharsets.UTF_8);
        var actual = converterService.parseXml(xml);
        Assert.assertEquals("XML parse error", note, actual);
    }

    @Test
    public void testYamlFormat() throws IOException {
        var yamlFile = getClass().getClassLoader().getResourceAsStream("note.yaml");
        var yaml = IOUtils.toString(yamlFile, StandardCharsets.UTF_8);
        var actual = converterService.formatYaml(note);
        Assert.assertEquals("YAML format error", yaml, actual);
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidXmlParse() throws IOException, XMLStreamException {
        converterService.parseXml("{\"xml\": \"isDead\"}");
    }
}
