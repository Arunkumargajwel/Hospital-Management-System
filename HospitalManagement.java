import java.sql.*;
import java.util.Scanner;

public class HospitalManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Hospital Management System");
            while (true) {
                System.out.println("1. Register Patient\n2. Schedule Appointment\n3. View Electronic Health Records\n4. Billing and Invoicing\n5. Inventory Management\n6. Staff Management\n7. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        registerPatient(conn, scanner);
                        break;
                    case 2:
                        scheduleAppointment(conn, scanner);
                        break;
                    case 3:
                        viewEHR(conn, scanner);
                        break;
                    case 4:
                        billingAndInvoicing(conn, scanner);
                        break;
                    case 5:
                        inventoryManagement(conn, scanner);
                        break;
                    case 6:
                        staffManagement(conn, scanner);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerPatient(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter disease: ");
        String disease = scanner.nextLine();

        String query = "INSERT INTO patients (name, age, disease) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, disease);
            stmt.executeUpdate();
            System.out.println("Patient registered successfully.");
        }
    }

    private static void scheduleAppointment(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter doctor name: ");
        String doctor = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String query = "INSERT INTO appointments (patient_id, doctor, date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, doctor);
            stmt.setString(3, date);
            stmt.executeUpdate();
            System.out.println("Appointment scheduled successfully.");
        }
    }

    private static void viewEHR(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        String query = "SELECT * FROM ehr WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {  // Check if there are any results
                    System.out.println("No Electronic Health Records found for Patient ID: " + patientId);
                    return;
                }

                while (rs.next()) {
                    System.out.printf("EHR ID: %d, Details: %s\n", rs.getInt("id"), rs.getString("details"));
                }
            }
        }
    }

    private static void billingAndInvoicing(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        String query = "INSERT INTO billing (patient_id, amount) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
            System.out.println("Billing recorded successfully.");
        }
    }

    private static void inventoryManagement(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter item name: ");
        String item = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter supplier name: ");
        String supplier = scanner.nextLine();
        
        String query = "INSERT INTO inventory (item_name, quantity, supplier) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item);
            stmt.setInt(2, quantity);
            stmt.setString(3, supplier);
            stmt.executeUpdate();
            System.out.println("Inventory updated successfully.");
        }
    }

    private static void staffManagement(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        String query = "INSERT INTO staff (name, role) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, role);
            stmt.executeUpdate();
            System.out.println("Staff added successfully.");
        }
    }
}
