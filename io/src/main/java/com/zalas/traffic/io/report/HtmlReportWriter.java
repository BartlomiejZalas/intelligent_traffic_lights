package com.zalas.traffic.io.report;


import com.zalas.traffic.io.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HtmlReportWriter {

    private final double[][] inputs;
    private final double[][] outputs;
    private final double[] testInputs;
    private final double result;

    public HtmlReportWriter(double[][] inputs, double[][] outputs, double[] testInputs, double result) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.testInputs = testInputs;
        this.result = result;
    }

    public void createReport() throws IOException, URISyntaxException {
        String body = "";
        body += "<table>";
        body += "<tr><td align=center>Traffic</td><td align=center>Excepted Lights Configuration</td></tr>";
        for (int i = 0; i < inputs.length; i++) {
            body += "<tr><td><canvas id=\"intersection" + i + "\"></canvas></td><td>";
            body += "<img src='traffic_cycles/" + (int) (outputs[i][0]) + ".png' />";
            body += "<script>drawIntersection(document.getElementById(\"intersection" + i + "\"), " + Arrays.toString(inputs[i]) + ");</script>";
            body += "</tr>";
        }
        body += "</table>";

        body += "<h1>Test</h1>";
        body += "<canvas id=\"test\"></canvas>";
        body += "<img src='traffic_cycles/" + (int) (result * 15) + ".png' />";
        body += "<script>drawIntersection(document.getElementById(\"test\"), " + Arrays.toString(testInputs) + ");</script>";


        File htmlTemplateFile = new File(getClass().getResource("/report-template.html").toURI());
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        String title = "Report";
        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("$body", body);
        File newHtmlFile = new File(Utils.getDynamicOutputDirectory() + "dynamic-report.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }
}
