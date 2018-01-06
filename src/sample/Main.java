package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    Stage window;
    Scene scene;
    Button button;
    String reportSrcFile = "src/data/static_template.jrxml";
    String outputPath="C:\\Users\\kyros\\Desktop\\FieldoutReport\\";
    String companyName="Rohin pvt ltd";
    String address="2nd main road,Anna nagar west, Chennai 40.";
    String jobTypeName="Standard Job one";
    String scheduleTime="2018-01-06 11:30 AM";
    String scheduleDuration="02h00";
    String technicianName="Amalan";
    String jobCompleted="Completed";
    String completedDuration="03h00";
    String description="Description";
    String jobComplete="Job Completed";
    String notes="This is a note from the field out admin rohin.  Thank you";
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Jasper Report Tutorial");
        button = new Button("Show Receipt");
        button.setOnAction(e -> {
            try {
                //new PrintReport().showReport();
                printPDF();
            } catch (ClassNotFoundException | JRException | SQLException | FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(button);
        scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    private void printPDF()throws FileNotFoundException,ClassNotFoundException,JRException,SQLException{
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("company_address",address );
        parameters.put("company_name", companyName);
        parameters.put("domain_name", companyName);
        parameters.put("job_type_name", jobTypeName);
        parameters.put("schedule_time", scheduleTime);
        parameters.put("scheduled_duration", scheduleDuration);
        parameters.put("technician_name", technicianName);
        parameters.put("job_completed", jobCompleted);
        parameters.put("completed_duration", completedDuration);
        parameters.put("description_text", description);
        parameters.put("job_complete_text", jobComplete);
        parameters.put("notes_text", notes);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        exportPDF(print);
    }
    private void exportPDF(JasperPrint print)throws JRException{
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath+companyName+".pdf"));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        System.out.println("Document Exported Successfully!");
    }
}
