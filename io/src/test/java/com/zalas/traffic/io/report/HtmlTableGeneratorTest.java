package com.zalas.traffic.io.report;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class HtmlTableGeneratorTest {

    private static final String HEADER_1 = "Header1";
    public static final String HEADER_2 = "Header2";
    public static final String CONTENT_1_1 = "Content 1.1";
    public static final String CONTENT_1_2 = "Content 1.2";
    public static final String CONTENT_2_1 = "Content 2.1";
    public static final String CONTENT_2_2 = "Content 2.2";
    private HtmlTableGenerator htmlTableGenerator;

    @Before
    public void setUp() throws Exception {
        htmlTableGenerator = new HtmlTableGenerator();
    }

    @Test
    public void generateTable_shouldCreateHtmlString() throws Exception {
        List<String> header = newArrayList(HEADER_1, HEADER_2);
        List<List<String>> content = newArrayList(
                newArrayList(CONTENT_1_1, CONTENT_1_2),
                newArrayList(CONTENT_2_1, CONTENT_2_2));


        String html = htmlTableGenerator.generateTable(content, header);

        assertEquals(
                "<table>" +
                "<tr><th>" + HEADER_1    + "</th><th>" + HEADER_2    + "</th></tr>" +
                "<tr><td>" + CONTENT_1_1 + "</td><td>" + CONTENT_1_2 + "</td></tr>" +
                "<tr><td>" + CONTENT_2_1 + "</td><td>" + CONTENT_2_2 + "</td></tr>" +
                "</table>", html);
    }
}