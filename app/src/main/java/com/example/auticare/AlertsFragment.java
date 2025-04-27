package com.example.auticare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AlertsFragment extends Fragment {

    private LineChart heartRateChart, spo2Chart, temperatureChart, respirationChart;
    private Button filterButton, generatePdfButton;

    public AlertsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        heartRateChart = view.findViewById(R.id.heartRateChart);
        spo2Chart = view.findViewById(R.id.spo2Chart);
        temperatureChart = view.findViewById(R.id.temperatureChart);
        respirationChart = view.findViewById(R.id.respirationChart);
        filterButton = view.findViewById(R.id.filterButton);
        generatePdfButton = view.findViewById(R.id.generatePdfButton);

        setupChart(heartRateChart, "Heart Rate", "#FF6F61");
        setupChart(spo2Chart, "SpO2", "#00C853");
        setupChart(temperatureChart, "Temperature", "#FFA726");
        setupChart(respirationChart, "Respiration Rate", "#42A5F5");

        filterButton.setOnClickListener(v -> showFilterDialog());

        generatePdfButton.setOnClickListener(v -> generatePDF());

        return view;
    }

    private void setupChart(LineChart chart, String label, String colorCode) {
        ArrayList<Entry> entries = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i <= 10; i++) {
            entries.add(new Entry(i, random.nextInt(100) + 50));
        }

        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(Color.parseColor(colorCode));
        dataSet.setLineWidth(2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor(colorCode));
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        Description description = new Description();
        description.setText(label);
        chart.setDescription(description);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getLegend().setEnabled(false);

        chart.animateX(1500);
        chart.invalidate();
    }

    private void showFilterDialog() {
        String[] options = {"Today", "Yesterday", "Last 7 Days", "Select Date Range"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Select Date Range");
        builder.setItems(options, (dialog, which) -> {
            // In future, apply filters here
        });
        builder.show();
    }

    private void generatePDF() {
        try {
            // Path to store PDF inside /Android/data/your app/files/Documents/AuticareReports
            File dir = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "AuticareReports");
            if (!dir.exists()) {
                dir.mkdirs(); // Create folder if not exist
            }

            String fileName = "Auticare_Report_" + System.currentTimeMillis() + ".pdf";
            File file = new File(dir, fileName);

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Health Report")
                    .setBold()
                    .setFontSize(24)
                    .setMarginBottom(10));
            document.add(new Paragraph("Generated on: " + new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))
                    .setFontSize(12)
                    .setMarginBottom(20));

            document.add(new Paragraph("Name: Ashwin")
                    .setFontSize(14)
                    .setMarginBottom(5));
            document.add(new Paragraph("User ID: user123")
                    .setFontSize(14)
                    .setMarginBottom(20));

            document.add(new Paragraph("Heart Rate: Normal range 70-100 bpm observed.").setFontSize(13).setMarginBottom(5));
            document.add(new Paragraph("SpO2 Level: Healthy oxygen saturation ~98%.").setFontSize(13).setMarginBottom(5));
            document.add(new Paragraph("Temperature: Body temperature stable at 36.5°C.").setFontSize(13).setMarginBottom(5));
            document.add(new Paragraph("Respiration Rate: Within normal limits (28/min).").setFontSize(13).setMarginBottom(20));

            document.add(new Paragraph("Thank you for using Auticare App!").setItalic().setFontSize(10).setMarginTop(50));
            document.add(new Paragraph("Powered by CareTech Solutions.").setFontSize(10));

            document.close();

            Toast.makeText(getContext(), "PDF Saved Successfully!", Toast.LENGTH_SHORT).show();

            openGeneratedPDF(file);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Failed to Generate PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openGeneratedPDF(File file) {
        try {
            Uri uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getContext(), "No PDF Viewer Found", Toast.LENGTH_SHORT).show();
        }
    }
}
