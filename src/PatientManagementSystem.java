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
        setSize(1600, 1200);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        

        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(layout);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setPreferredSize(new Dimension(400, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.WHITE);
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        middlePanel.setPreferredSize(new Dimension(400, 60));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        middlePanel.setBackground(Color.WHITE);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(400, 60));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.setBackground(Color.WHITE);

        // Create a panel for adding new patients
        JPanel addPanel = new JPanel(new FlowLayout(50));
        addPanel.setBackground(Color.WHITE);
        // Create the text fields and set their preferred size
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        JTextField AddressField = new JTextField(20);
        JTextField phoneField = new JTextField(10);
        idField.setPreferredSize(new Dimension(idField.getPreferredSize().width, 30));
        nameField.setPreferredSize(new Dimension(nameField.getPreferredSize().width, 30));
        AddressField.setPreferredSize(new Dimension(AddressField.getPreferredSize().width, 30));
        phoneField.setPreferredSize(new Dimension(phoneField.getPreferredSize().width, 30));

        // Add the components to the addPanel
        addPanel.add(new JLabel("ID: "));
        addPanel.add(idField);
        addPanel.add(new JLabel("Name: "));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Address: "));
        addPanel.add(AddressField);
        addPanel.add(new JLabel("Phone: "));
        addPanel.add(phoneField);
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(80, 30));
        addButton.setForeground(Color.BLACK); // Set the text color to white
        addButton.setBackground(Color.WHITE);
    

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String address = AddressField.getText();
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
        // adding end

        // Create a panel for updating existing patients
        JPanel updatePanel = new JPanel(new FlowLayout(50));
        updatePanel.setBackground(Color.WHITE);
        JTextField updateIdField = new JTextField(10);
        JTextField updateNameField = new JTextField(20);
        JTextField updateAddressField = new JTextField(20);
        JTextField updatePhoneField = new JTextField(10);
        updatePanel.add(new JLabel("ID: "));
        updatePanel.add(updateIdField);
        updatePanel.add(new JLabel("Name: "));
        updatePanel.add(updateNameField);
        updatePanel.add(new JLabel("Address: "));
        updatePanel.add(updateAddressField);
        updatePanel.add(new JLabel("Phone: "));
        updatePanel.add(updatePhoneField);
        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(80, 30));
        updateButton.setForeground(Color.BLACK); // Set the text color to white
        updateButton.setBackground(Color.WHITE);
        updateIdField.setPreferredSize(new Dimension(updateIdField.getPreferredSize().width, 30));
        updateNameField.setPreferredSize(new Dimension(updateNameField.getPreferredSize().width, 30));
        updateAddressField.setPreferredSize(new Dimension(updateAddressField.getPreferredSize().width, 30));
        updatePhoneField.setPreferredSize(new Dimension(updatePhoneField.getPreferredSize().width, 30));
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

        // Create a panel for searching for patients by ID
        JPanel searchPanel = new JPanel(new FlowLayout(50));
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new JTextField(10);
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 30));
        searchPanel.add(new JLabel("Search by ID: "));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30));
        searchButton.setForeground(Color.BLACK); // Set the text color to white
        searchButton.setBackground(Color.WHITE);
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

        // Create a panel for deleting patients
        JPanel deletePanel = new JPanel(new FlowLayout(50));
        deletePanel.setBackground(Color.WHITE);
        JTextField deleteField = new JTextField(10);
        deleteField.setPreferredSize(new Dimension(deleteField.getPreferredSize().width, 30));
        deletePanel.add(new JLabel("Delete by ID: "));
        deletePanel.add(deleteField);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setForeground(Color.BLACK); // Set the text color to white
        deleteButton.setBackground(Color.WHITE);
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
        // updating end

        // Create a table for displaying patients
        patientTableModel = new DefaultTableModel(new Object[] { "ID", "Name", "Address", "Phone" }, 0);
        patientTable = new JTable(patientTableModel);
        JScrollPane patientScrollPane = new JScrollPane(patientTable);

        topPanel.add(addPanel, BorderLayout.CENTER);
        middlePanel.add(searchPanel, BorderLayout.LINE_START);
        middlePanel.add(deletePanel, BorderLayout.LINE_END);
        bottomPanel.add(updatePanel, BorderLayout.CENTER);

        getContentPane().add(Box.createVerticalGlue());
        getContentPane().add(topPanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(middlePanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(bottomPanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(patientScrollPane);
        getContentPane().add(Box.createVerticalGlue());

        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        patientScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
            patientTableModel.addRow(
                    new Object[] { patient.getId(), patient.getName(), patient.getAddress(), patient.getPhone() });
        }
    }

    public static void main(String[] args) {
        // Create a new PatientDAO and pass it to the PatientManagementSystem
        // constructor
        PatientDAO patientDAO = new PatientDAO();
        new PatientManagementSystem(patientDAO);
    }
}
