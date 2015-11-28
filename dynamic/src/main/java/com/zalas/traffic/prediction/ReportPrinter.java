package com.zalas.traffic.prediction;

import com.zalas.traffic.prediction.data.DataSet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class ReportPrinter {

    private final DataSet dataSet;
    private final double[] testInputs;
    private final double result;

    public ReportPrinter(DataSet dataSet, double[] testInputs, double result) {

        this.dataSet = dataSet;
        this.testInputs = testInputs;
        this.result = result;
    }

    public void createReport() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("/home/bartek/Pulpit/report.html");

        out.println("<html><body>");
        out.println("<h1>Data Set</h1>");
        out.println("<script> var size = 300;");
        out.println("function drawIntersection(c, traffic) {");
        out.println("    ");
        out.println("    c.setAttribute(\"width\", size);");
        out.println("    c.setAttribute(\"height\", size);");
        out.println("    var ctx = c.getContext(\"2d\");");
        out.println("    ctx.fillStyle = \"#666666\";");
        out.println("    ctx.fillRect(size / 3, 0, size / 3, size);");
        out.println("    ctx.fillRect(0, size / 3, size, size / 3);");
        out.println("    ctx.fillStyle = \"#FFFFFF\";");
        out.println("    ctx.font = \"30px Verdana\";");
        out.println("    var padding = 50;");
        out.println("    ctx.fillText(traffic[0], size / 2, 0 + padding);");
        out.println("    ctx.fillText(traffic[1], size - padding, size / 2);");
        out.println("    ctx.fillText(traffic[2], size / 2, size - padding);");
        out.println("    ctx.fillText(traffic[3], padding, size / 2);");
        out.println("}");
        out.println("</script>");

        out.println("<table border=1>");
        out.println("<tr><td align=center>Traffic</td><td align=center>Excepted Lights Configuration</td></tr>");
        for (int i = 0; i < dataSet.getDataRows().size(); i++) {
            out.println("<tr><td><canvas id=\"intersection" + i + "\"></canvas></td><td>");
            out.println("<img src='traffic_cycles/" + (int) (dataSet.getDataRows().get(i).getOutput() * 10) + ".png' />");
            out.println("<script>drawIntersection(document.getElementById(\"intersection" + i + "\"), " + Arrays.toString(dataSet.getDataRows().get(i).getInputs()) + ");</script>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h1>Test</h1>");
        out.println("<canvas id=\"test\"></canvas>");
        out.println("<img src='traffic_cycles/" + (int) (result * 10) + ".png' />");
        out.println("<script>drawIntersection(document.getElementById(\"test\"), " + Arrays.toString(testInputs) + ");</script>");
        out.println("</body></html>");

        out.close();
    }
}
