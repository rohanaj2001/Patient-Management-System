import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientManagementSystem extends JFrame {
private PatientDAO patientDAO;
private JTable patientTable;
private DefaultTableModel patientTableModel;

public PatientManagementSystem(PatientDAO patientDAO) {
    this.patientDAO = patientDAO;

    setTitle("Patient Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);

    JPanel mainPanel = new JPanel(new BorderLayout());

    // Create a panel for adding new patients
    JPanel addPanel = new JPanel(new FlowLayout(4));
    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField addressField = new JTextField();
    JTextField phoneField = new JTextField();
    addPanel.add(new JLabel("ID: "));
    addPanel.add(idField);
    addPanel.add(new JLabel("Name: "));
    addPanel.add(nameField);
    addPanel.add(new JLabel("Address: "));
    addPanel.add(addressField);
    addPanel.add(new JLabel("Phone: "));
    addPanel.add(phoneField);
    JButton addButton = new JButton("Add");
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            Patient patient = new Patient(id, name, address, phone);
            boolean success = patientDAO.addPatient(patient);
            if (success) {
                updatePatientTable();
                JOptionPane.showMessageDialog(PatientManagementSystem.this, "Patient added successfully.");
            } else {
                JOptionPane.showMessageDialog(PatientManagementSystem.this, "Error adding patient.");
            }
        }
    });
    addPanel.add(addButton);

    // Create a panel for searching for patients by ID
    JPanel searchPanel = new JPanel(new FlowLayout());
    JTextField searchField = new JTextField(20);
    searchPanel.add(new JLabel("Search by ID: "));
    searchPanel.add(searchField);
    JButton searchButton = new JButton("Search");
    searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(searchField.getText());
            Patient patient = patientDAO.getPatientById(id);
            if (patient != null) {
                JOptionPane.showMessageDialog(PatientManagementSystem.this, patient.toString());
            } else {
                JOptionPane.showMessageDialog(PatientManagementSystem.this, "No patient found with ID " + id);
            }
        }
    });
    searchPanel.add(searchButton);

    // Create a panel for updating existing patients
    JPanel updatePanel = new JPanel(new GridLayout(5, 2));
    JTextField updateIdField = new JTextField();
    JTextField updateNameField = new JTextField();
    JTextField updateAddressField = new JTextField();
    JTextField updatePhoneField = new JTextField();
    updatePanel.add(new JLabel("ID: "));
    updatePanel.add(updateIdField);
    updatePanel.add(new JLabel("Name: "));
    updatePanel.add(updateNameField);
    updatePanel.add(new JLabel("Address: "));
    updatePanel.add(updateAddressField);
    updatePanel.add(new JLabel("Phone: "));
    updatePanel.add(updatePhoneField);
    JButton updateButton = new JButton("Update");
    updateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
    int id = Integer.parseInt(updateIdField.getText());
    String name = updateNameField.getText();
    String address = updateAddressField.getText();
    String phone = updatePhoneField.getText();
    Patient patient = new Patient(id, name, address, phone);
    boolean success = patientDAO.updatePatient(patient);
    if (success) {
    updatePatientTable();
    JOptionPane.showMessageDialog(PatientManagementSystem.this, "Patient updated successfully.");
    } else {
    JOptionPane.showMessageDialog(PatientManagementSystem.this, "Error updating patient.");
    }
    }
    });
    updatePanel.add(updateButton);
    // Create a panel for deleting patients
    JPanel deletePanel = new JPanel(new FlowLayout(100));
    JTextField deleteField = new JTextField(20);
    deletePanel.add(new JLabel("Delete by ID: "));
    deletePanel.add(deleteField);
    JButton deleteButton = new JButton("Delete");
    deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(deleteField.getText());
            boolean success = patientDAO.deletePatientById(id);
            if (success) {
                updatePatientTable();
                JOptionPane.showMessageDialog(PatientManagementSystem.this, "Patient deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(PatientManagementSystem.this, "Error deleting patient.");
            }
        }
    });
    deletePanel.add(deleteButton);

    // Create a table for displaying patients
    patientTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Address", "Phone"}, 0);
    patientTable = new JTable(patientTableModel);
    JScrollPane patientScrollPane = new JScrollPane(patientTable);

    mainPanel.add(addPanel, BorderLayout.NORTH);
    mainPanel.add(searchPanel, BorderLayout.WEST);
    mainPanel.add(updatePanel, BorderLayout.CENTER);
    mainPanel.add(deletePanel, BorderLayout.EAST);
    mainPanel.add(patientScrollPane, BorderLayout.SOUTH);

    setContentPane(mainPanel);
    setVisible(true);

    updatePatientTable();
}

/**
 * Update the patient table by retrieving all patients from the database.
 */
private void updatePatientTable() {
    patientTableModel.setRowCount(0);
    List<Patient> patients = patientDAO.getAllPatients();
    for (Patient patient : patients) {
        patientTableModel.addRow(new Object[]{patient.getId(), patient.getName(), patient.getAddress(), patient.getPhone()});
    }
}

public static void main(String[] args) {
    // Create a new PatientDAO and pass it to the PatientManagementSystem constructor
    PatientDAO patientDAO = new PatientDAO();
    new PatientManagementSystem(patientDAO);
}
}
