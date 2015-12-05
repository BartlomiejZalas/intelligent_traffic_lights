package com.zalas.traffic.io.report;

import org.rendersnake.HtmlCanvas;

import java.io.IOException;
import java.util.List;

public class HtmlTableGenerator {

    public String generateTable(List<List<String>> content, List<String> headers) throws IOException {

        HtmlCanvas html = new HtmlCanvas();
        html.table();

        createHeader(html, headers);

        for(List<String> row : content) {
            createTableRow(html, row);
        }

        html._table();

        return html.toHtml();
    }

    private void createTableRow(HtmlCanvas html, List<String> row) throws IOException {
        html.tr();
        for (String cell : row) {
            html.td().content(cell, false);
        }
        html._tr();
    }

    private void createHeader(HtmlCanvas html, List<String> headers) throws IOException {
        html.tr();
        for(String header : headers) {
            html.th().content(header, false);
        }
        html._tr();
    }

}
