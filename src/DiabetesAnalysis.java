import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiabetesAnalysis {

    static class Patient {
        int pregnancies;
        int glucose;
        double bloodPressure;
        double skinThickness;
        int insulin;
        double bmi;
        double diabetesPedigreeFunction;
        int age;
        int diabetic;

        Patient(int pregnancies, int glucose, double bloodPressure, double skinThickness, int insulin, double bmi,
                double diabetesPedigreeFunction, int age, int diabetic) {
            this.pregnancies = pregnancies;
            this.glucose = glucose;
            this.bloodPressure = bloodPressure;
            this.skinThickness = skinThickness;
            this.insulin = insulin;
            this.bmi = bmi;
            this.diabetesPedigreeFunction = diabetesPedigreeFunction;
            this.age = age;
            this.diabetic = diabetic;
        }
    }

    public static void main(String[] args) {
        String csvFile = "patient-record.csv";
        String line;
        String cvsSplitBy = ",";

        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                int pregnancies = Integer.parseInt(data[0]);
                int glucose = Integer.parseInt(data[1]);
                double bloodPressure = Double.parseDouble(data[2]);
                double skinThickness = Double.parseDouble(data[3]);
                int insulin = Integer.parseInt(data[4]);
                double bmi = Double.parseDouble(data[5]);
                double diabetesPedigreeFunction = Double.parseDouble(data[6]);
                int age = Integer.parseInt(data[7]);
                int diabetic = Integer.parseInt(data[8]);

                patients.add(new Patient(pregnancies, glucose, bloodPressure, skinThickness, insulin, bmi,
                        diabetesPedigreeFunction, age, diabetic));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> comments = new ArrayList<>();
        for (Patient patient : patients) {
            StringBuilder comment = new StringBuilder();

            // Check for abnormal vitals
            if (patient.glucose < 70 || patient.glucose > 140) {
                comment.append("Glucose is out of normal range | ");
            }
            if (patient.bloodPressure < 60 || patient.bloodPressure > 100) {
                comment.append("Blood Pressure is out of normal range | ");
            }
            if (patient.bmi < 18.5 || patient.bmi > 24.9) {
                comment.append("BMI is out of normal range. ");
            }

            // Add comments to the list if vitals are normal/healthy
            if (comment.length() == 0) {
                comments.add("All Patient Vitals Are in Healthy/Normal Range");
            } else {
                comments.add(comment.toString());
            }
        }

        // Write the output CSV file
        try (FileWriter writer = new FileWriter("patient-output.csv")) {
            writer.write("Pregnancies,Glucose,BloodPressure,SkinThickness,Insulin,BMI,DiabetesPedigreeFunction,Age,diabetic/non-diabetic,Comments\n");
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                writer.write(patient.pregnancies + "," + patient.glucose + "," + patient.bloodPressure + ","
                        + patient.skinThickness + "," + patient.insulin + "," + patient.bmi + ","
                        + patient.diabetesPedigreeFunction + "," + patient.age + "," + patient.diabetic + ","
                        + comments.get(i) + "\n");
            }
            System.out.println("Patient output csv file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
