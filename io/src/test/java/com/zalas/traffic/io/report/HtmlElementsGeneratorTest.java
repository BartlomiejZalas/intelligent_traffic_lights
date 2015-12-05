package com.zalas.traffic.io.report;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class HtmlElementsGeneratorTest {

    private HtmlElementsGenerator htmlElementsGenerator;

    @Before
    public void setUp() throws Exception {
        htmlElementsGenerator = new HtmlElementsGenerator();
    }

    @Test
    public void generateCanvas_shouldReturnStringWithHtml() throws Exception {
        String id = "intersection5";

        String html = htmlElementsGenerator.generateCanvas(id);

        assertEquals("<canvas id=\""+id+"\"></canvas>", html);
    }

    @Test
    public void generateImage_shouldReturnStringWithHtml() throws Exception {
        String file = "traffic_cycles/5.png";

        String html = htmlElementsGenerator.generateImage(file);

        assertEquals("<img src=\""+file+"\"/>", html);
    }

    @Test
    public void generateScriptDrawIntersection_shouldReturnStringWithHtml() throws Exception {
        String elementId = "intersection5";
        List<Integer> traffic = newArrayList(1, 2, 3, 4);

        String html = htmlElementsGenerator.generateScriptDrawIntersection(elementId, traffic);

        assertEquals(
                "<script type=\"text/javascript\">" +
                "drawIntersection(document.getElementById(\"" + elementId + "\"), [1, 2, 3, 4]);" +
                "</script>", html);
    }

    @Test
    public void generateH1_shouldReturnStringWithHtml() throws Exception {
        String content = "Header";

        String html = htmlElementsGenerator.generateH1(content);

        assertEquals("<h1>"+content+"</h1>",html);
    }
}