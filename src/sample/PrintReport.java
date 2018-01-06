package sample;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrintReport extends JFrame {

    private static final long serialVersionUID = 1L;

    public void showReport() throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {

        String reportSrcFile = "src/data/static_template.jrxml";
//        String reportSrcFile = "src/data/Blank_A4.jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for report
        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("company", "Arasu pvt ltd");
        parameters.put("receipt_no", "RE101");
        parameters.put("name", "Arasu");
        parameters.put("amount", "520");
        parameters.put("receipt_for", "EMI Payment");
        parameters.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        parameters.put("contact", "9003181070");

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);

        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Users\\kyros\\Desktop/sample_report.pdf"));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);

        exporter.exportReport();

        JRXlsExporter jrXlsExporter = new JRXlsExporter();
        jrXlsExporter.setExporterInput(new SimpleExporterInput(print));
        jrXlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Users\\kyros\\Desktop/sample_report_excel.xls"));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(true);
        xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setCollapseRowSpan(false);
        jrXlsExporter.setConfiguration(xlsReportConfiguration);

        jrXlsExporter.exportReport();


        System.out.print("Done!");

    }
}
