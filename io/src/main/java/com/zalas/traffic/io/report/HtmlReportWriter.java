package com.zalas.traffic.io.report;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;

public class HtmlReportWriter {

    private final ReportData learningReportData;
    private final ReportData testReportData;

    private final HtmlTableGenerator tableGenerator = new HtmlTableGenerator();
    private final HtmlElementsGenerator htmlElementsGenerator = new HtmlElementsGenerator();

    public HtmlReportWriter(ReportData learningReportData, ReportData testReportData) {

        this.learningReportData = learningReportData;
        this.testReportData = testReportData;
    }

    public void createReport(File outputFile) throws IOException, URISyntaxException {

        String body = "";
        body += createLearningHeader();
        body += createLearningTable(learningReportData, "intersection");

        body += createTestHeader();
        body += createLearningTable(testReportData, "test");

        writeToFile(outputFile, body);
    }

    private String createTestHeader() throws IOException {
        return htmlElementsGenerator.generateH1("Test");
    }

    private String createLearningHeader() throws IOException {
        return htmlElementsGenerator.generateH1("Learning Data");
    }

    private String createLearningTable(ReportData learningReportData, String prefix) throws IOException {
        List<List<String>> learningTableContent = newArrayList();
        for (int i = 0; i < learningReportData.getTrafficStatuses().size(); i++) {
            String htmlCanvas = htmlElementsGenerator.generateCanvas(prefix + i);
            String htmlImg = htmlElementsGenerator.generateImage("traffic_cycles/" + learningReportData.getLightCycles().get(i) + ".png");
            String htmlScript = htmlElementsGenerator.generateScriptDrawIntersection(prefix + i, learningReportData.getTrafficStatuses().get(i));
            String summaryColumn = learningReportData.summaryColumn(i);
            learningTableContent.add(newArrayList(htmlCanvas, htmlImg + htmlScript, summaryColumn));
        }
        ArrayList<String> learningTableHeaders = newArrayList("Traffic", "Expected Lights Configuration");
        return tableGenerator.generateTable(learningTableContent, learningTableHeaders);
    }

    private void writeToFile(File outputFile, String body) throws URISyntaxException, IOException {
        File htmlTemplateFile = new File(getClass().getResource("/report-template.html").toURI());
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        String title = "Report";
        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("$body", body);
        FileUtils.writeStringToFile(outputFile, htmlString);
    }
}
