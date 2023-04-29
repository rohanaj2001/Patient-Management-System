import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection conn;

    public PatientDAO(Connection conn) {
        this.conn = conn;	
    }

    /**
     * Create a new patient record in the database.
     *
     * @param patient the patient to add
     * @return true if the operation was successful, false otherwise
     */
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (id, name, address, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getAddress());
            pstmt.setString(4, patient.getPhone());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve a patient record from the database by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient with the given ID, or null if no such patient exists
     */
    public Patient getPatientById(String id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                return new Patient(id, name, address, phone);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting patient by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve all patient records from the database.
     *
     * @return a list of all patients in the database
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Patient patient = new Patient(id, name, address, phone);
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all patients: " + e.getMessage());
        }
        return patients;
    }

    /**
     * Update an existing patient record in the database.
     *
     * @param patient the patient to update
     * @return true if the operation was successful, false otherwise
     */
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getAddress());
            pstmt.setString(3, patient.getPhone());
            pstmt.setString(4, patient.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error updating patient:" + e.getMessage() );
            return false;
        }
    }

    /**
     * Delete a patient record from the database by ID.
     *
     * @param id the ID of the patient to delete
     * @return true if the operation was successful, false otherwise
     */
    public boolean deletePatientById(String id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting patient by ID: " + e.getMessage());
            return false;
        }
    }
}
