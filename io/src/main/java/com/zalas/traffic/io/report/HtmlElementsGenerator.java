package com.zalas.traffic.io.report;

import org.rendersnake.HtmlCanvas;

import java.io.IOException;
import java.util.List;

import static org.rendersnake.HtmlAttributesFactory.*;

public class HtmlElementsGenerator {


    public String generateCanvas(String id) throws IOException {
        HtmlCanvas html = new HtmlCanvas();

        html.canvas(id(id))._canvas();

        return html.toHtml();
    }

    public String generateImage(String file) throws IOException {
        HtmlCanvas html = new HtmlCanvas();

        html.img(src(file));

        return html.toHtml();
    }

    public String generateScriptDrawIntersection(String elementId, List<Integer> traffic) throws IOException {
        HtmlCanvas html = new HtmlCanvas();

        String arrayToJsFormat = traffic.toString();

        html
                .script(type("text/javascript"))
                .write("drawIntersection(document.getElementById(\"" + elementId + "\"), " + arrayToJsFormat + ");", false)
                ._script();

        return html.toHtml();
    }

    public String generateH1(String content) throws IOException {
        HtmlCanvas html = new HtmlCanvas();

        html.h1().content(content);

        return html.toHtml();
    }
}
