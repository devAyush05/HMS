import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Define Patient, Doctor, and Appointment classes
class Patient {
    String name;
    int age;
    String gender;
    String ailment;

    public Patient(String name, int age, String gender, String ailment) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.ailment = ailment;
    }
}

class Doctor {
    String name;
    String specialization;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }
}

class Appointment {
    Patient patient;
    Doctor doctor;

    public Appointment(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }
}

public class HospitalManagementSystemGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();

    // Components for Patient and Doctor forms
    private JTextField patientNameField, patientAgeField, patientGenderField, patientAilmentField;
    private JTextField doctorNameField, doctorSpecializationField;

    // Components for Login and Signup
    private JTextField loginUsernameField, signupUsernameField;
    private JPasswordField loginPasswordField, signupPasswordField;
    private String savedUsername = "user";
    private String savedPassword = "password";

    public HospitalManagementSystemGUI() {
        setTitle("Hospital Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel (card layout)
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createSignupPanel(), "Signup");
        mainPanel.add(createMainManagementPanel(), "Management");

        cardLayout.show(mainPanel, "Login");
        add(mainPanel);
    }

    // Login Panel
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton goToSignupButton = new JButton("Go to Signup");

        panel.add(new JLabel("Username:"));
        panel.add(loginUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);

        panel.add(loginButton);
        panel.add(goToSignupButton);

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());

            if (username.equals(savedUsername) && password.equals(savedPassword)) {
                cardLayout.show(mainPanel, "Management");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        goToSignupButton.addActionListener(e -> cardLayout.show(mainPanel, "Signup"));

        return panel;
    }

    // Signup Panel
    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        signupUsernameField = new JTextField();
        signupPasswordField = new JPasswordField();
        JButton signupButton = new JButton("Signup");
        JButton goToLoginButton = new JButton("Go to Login");

        panel.add(new JLabel("Username:"));
        panel.add(signupUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(signupPasswordField);

        panel.add(signupButton);
        panel.add(goToLoginButton);

        signupButton.addActionListener(e -> {
            savedUsername = signupUsernameField.getText();
            savedPassword = new String(signupPasswordField.getPassword());
            JOptionPane.showMessageDialog(this, "Signup successful! Please log in.");
            cardLayout.show(mainPanel, "Login");
        });

        goToLoginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        return panel;
    }

    // Main Management Panel with tabs
    private JPanel createMainManagementPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Patient", createAddPatientPanel());
        tabbedPane.addTab("Add Doctor", createAddDoctorPanel());
        tabbedPane.addTab("Create Appointment", createAppointmentPanel());
        tabbedPane.addTab("View Appointments", createViewAppointmentsPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    // Panel to add a new patient
    private JPanel createAddPatientPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Patient details
        panel.add(new JLabel("Name:"));
        patientNameField = new JTextField();
        panel.add(patientNameField);

        panel.add(new JLabel("Age:"));
        patientAgeField = new JTextField();
        panel.add(patientAgeField);

        panel.add(new JLabel("Gender:"));
        patientGenderField = new JTextField();
        panel.add(patientGenderField);

        panel.add(new JLabel("Ailment:"));
        patientAilmentField = new JTextField();
        panel.add(patientAilmentField);

        JButton addButton = new JButton("Add Patient");
        addButton.addActionListener(new AddPatientListener());
        panel.add(addButton);

        return panel;
    }

    // Panel to add a new doctor
    private JPanel createAddDoctorPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        doctorNameField = new JTextField();
        panel.add(doctorNameField);

        panel.add(new JLabel("Specialization:"));
        doctorSpecializationField = new JTextField();
        panel.add(doctorSpecializationField);

        JButton addButton = new JButton("Add Doctor");
        addButton.addActionListener(new AddDoctorListener());
        panel.add(addButton);

        return panel;
    }

    // Panel to create an appointment
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JComboBox<String> patientComboBox = new JComboBox<>();
        JComboBox<String> doctorComboBox = new JComboBox<>();

        panel.add(new JLabel("Select Patient:"));
        panel.add(patientComboBox);
        panel.add(new JLabel("Select Doctor:"));
        panel.add(doctorComboBox);

        JButton createAppointmentButton = new JButton("Create Appointment");
        createAppointmentButton.addActionListener(e -> {
            int patientIndex = patientComboBox.getSelectedIndex();
            int doctorIndex = doctorComboBox.getSelectedIndex();
            if (patientIndex != -1 && doctorIndex != -1) {
                appointments.add(new Appointment(patients.get(patientIndex), doctors.get(doctorIndex)));
                JOptionPane.showMessageDialog(this, "Appointment created successfully!");
            }
        });
        panel.add(createAppointmentButton);
        return panel;
    }

    private JPanel createViewAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea appointmentListArea = new JTextArea();
        appointmentListArea.setEditable(false);
        JButton viewAppointmentsButton = new JButton("View Appointments");

        viewAppointmentsButton.addActionListener(e -> {
            appointmentListArea.setText("");
            for (Appointment appointment : appointments) {
                appointmentListArea.append(
                        "Patient: " + appointment.patient.name + " - Doctor: " + appointment.doctor.name + "\n");
            }
        });

        panel.add(new JScrollPane(appointmentListArea), BorderLayout.CENTER);
        panel.add(viewAppointmentsButton, BorderLayout.SOUTH);

        return panel;
    }

    private class AddPatientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = patientNameField.getText();
            int age = Integer.parseInt(patientAgeField.getText());
            String gender = patientGenderField.getText();
            String ailment = patientAilmentField.getText();

            patients.add(new Patient(name, age, gender, ailment));
            JOptionPane.showMessageDialog(HospitalManagementSystemGUI.this, "Patient added successfully!");
        }
    }

    private class AddDoctorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = doctorNameField.getText();
            String specialization = doctorSpecializationField.getText();

            doctors.add(new Doctor(name, specialization));
            JOptionPane.showMessageDialog(HospitalManagementSystemGUI.this, "Doctor added successfully!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HospitalManagementSystemGUI().setVisible(true);
        });
    }
}
