package data;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class UserDatabase {

    private final String filePath;

    public UserDatabase(String filePath) {
        this.filePath = filePath;
    }
    // Read all users from the file
    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/LoginInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String userID = parts[0];
//                    String salt = parts[1];
                    String password=parts[1];
//                    String hashedPassword = parts[2];
                    users.put(userID,password);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        return users;
    }

    // Write a new user to the file
    public void writeUser(String userID, String hashPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/LoginInfo.txt", true))) {
            writer.write(userID + "|"+  hashPassword);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    public void replaceUserLine(String userID, String newSalt, String newHashedPassword) {
        File file = new File("DataBase/LoginInfo.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Construct the new line for the user
        String newUserData = userID + "|" + newSalt + "|" + newHashedPassword;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(userID + "|")) { // Check if the line matches the user ID
                    lines.add(newUserData); // Replace the line with the new user data
                    found = true;
                } else {
                    lines.add(line); // Keep the line as-is
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the password file: " + e.getMessage(), e);
        }

        if (!found) {
            throw new IllegalArgumentException("User with ID " + userID + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the password file: " + e.getMessage(), e);
        }
    }

    public String patientToString(Patient patient) {
        return String.join("|",
                patient.getHospitalId(),
                patient.getName(),
                patient.getGender(),
                String.valueOf(patient.getAge()),
                patient.getMedicalRecord().getDateOfBirth(),
                patient.getMedicalRecord().getContactInformation(),
                patient.getMedicalRecord().getBloodType()
        );
    }

    public void replacePatient(Patient patient) {
        File file = new File("DataBase/Patient.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Convert the Patient object to a string representation
        String newPatientData = patientToString(patient);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(patient.getHospitalId() + "|")) { // Check if the line matches the patient ID
                    lines.add(newPatientData); // Replace the line with the new patient data
                    found = true;
                } else {
                    lines.add(line); // Keep the line as-is
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the patient file: " + e.getMessage(), e);
        }

        if (!found) {
            throw new IllegalArgumentException("Patient with ID " + patient.getHospitalId() + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the patient file: " + e.getMessage(), e);
        }
    }


    public void writePatient(Patient patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Patient.txt", true))) {
            String patientData = String.join("|",
                    patient.getHospitalId(),
                    patient.getName(),
                    patient.getGender(),
                    String.valueOf(patient.getAge()),
                    patient.getMedicalRecord().getDateOfBirth(),
                    patient.getMedicalRecord().getContactInformation(),
                    patient.getMedicalRecord().getBloodType()
            );
            writer.write(patientData);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing patient to file: " + e.getMessage(), e);
        }
    }

    public Patient getPatientById(String patientId, AuthenticationManager authenticationManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Patient.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7 && parts[0].equals(patientId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String dateOfBirth = parts[4];
                    String contactInformation = parts[5];
                    String bloodType = parts[6];
                    return new Patient(patientId, name, gender, age, dateOfBirth, contactInformation, bloodType, authenticationManager);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading patients from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }

    public Pharmacist getPharmacistById(String hospitalId, AuthenticationManager authenticationManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Pharmacists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[0].equals(hospitalId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    Inventory inventory = this.createInventoryFromFile();
                    ReplenishRequestRecord rr_records = this.createReplenishRequestRecordFromFile();

                    return new Pharmacist(hospitalId, name, gender, age, inventory, rr_records, authenticationManager);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading pharmacists from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching pharmacist is found
    }

    public void writePharmacist(Pharmacist pharmacist) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Pharmacists.txt", true))) {
            String pharmacistData = String.join("|",
                    pharmacist.getHospitalId(),
                    pharmacist.getName(),
                    pharmacist.getGender(),
                    String.valueOf(pharmacist.getAge())//,
                    //pharmacist.getInventory().serialize(), // Serialize Inventory
                    //pharmacist.getReplenishRequestRecord().serialize() // Serialize ReplenishRequestRecord
            );
            writer.write(pharmacistData);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing pharmacist to file: " + e.getMessage(), e);
        }
    }

    public Doctor getDoctorById(String hospitalId, AuthenticationManager authenticationManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Doctors.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[0].equals(hospitalId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);

                    return new Doctor(hospitalId, name, gender, age, authenticationManager);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading doctors from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching doctor is found
    }

    public void writeDoctor(Doctor doctor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Doctors.txt", true))) {
            String doctorData = String.join("|",
                    doctor.getHospitalId(),
                    doctor.getName(),
                    doctor.getGender(),
                    String.valueOf(doctor.getAge())
            );
            writer.write(doctorData);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing doctor to file: " + e.getMessage(), e);
        }
    }

    public Administrator getAdministratorById(String hospitalId, AuthenticationManager authenticationManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Administrators.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[0].equals(hospitalId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);

                    // Deserialize Inventory and StaffRecord
                    Inventory inventory = this.createInventoryFromFile();
                    StaffRecord staff = this.readStaffRecordFromFile(authenticationManager);
                    ReplenishRequestRecord rr_Record = this.createReplenishRequestRecordFromFile();
                    return new Administrator(hospitalId, name, gender, age, inventory, staff, rr_Record, authenticationManager);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading administrators from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching administrator is found
    }

    public User getUserById(String hospitalId, AuthenticationManager authenticationManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Staff.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[0].equals(hospitalId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);

                    return new User(hospitalId, "temp", name, gender, age);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading administrators from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching administrator is found
    }

    public StaffRecord readStaffRecordFromFile(AuthenticationManager authenticationManager) {
        StaffRecord staffRecord = new StaffRecord();

        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Staff.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split the line by '|'
                if (parts.length != 4) {
                    System.out.println("Invalid record format: " + line);
                    continue; // Skip invalid lines
                }

                // Extract fields from the line
                String id = parts[0].trim();
                String name = parts[1].trim();
                String gender = parts[2].trim();
                int age;

                try {
                    age = Integer.parseInt(parts[3].trim()); // Parse the age
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format for line: " + line);
                    continue; // Skip invalid lines
                }

                // Determine role based on the ID prefix
                String role;
                if (id.startsWith("D")) {
                    role = "Doctor";
                } else if (id.startsWith("P")) {
                    role = "Pharmacist";
                } else {
                    role = "Administrator";
                }

                // Create a new User object and add it to the staff record if eligible
                User user = new User(id, role, name, gender, age, authenticationManager);
                staffRecord.addStaffInit(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the staff file: " + e.getMessage(), e);
        }

        return staffRecord;
    }

    public void writeStaff(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Staff.txt", true))) {
            String doctorData = String.join("|",
                    user.getHospitalId(),
                    user.getName(),
                    user.getGender(),
                    String.valueOf(user.getAge())
            );
            writer.write(doctorData);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing Staff to file: " + e.getMessage(), e);
        }
    }

    public String userToString(User user) {
        if (user != null) {
            return String.join("|",
                    user.getHospitalId(),
                    user.getName(),
                    user.getGender(),
                    String.valueOf(user.getAge())
            );
        }
        return null;
    }

    public Inventory createInventoryFromFile() {
        Inventory inventory = new Inventory(); // Create an empty Inventory object

        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) { // Ensure the format is correct
                    String name = parts[0];
                    int stockLevel = Integer.parseInt(parts[1]);
                    int lowThreshold = Integer.parseInt(parts[2]);

                    // Create a Medicine object and add it to the Inventory
                    Medicine medicine = new Medicine(name, stockLevel, lowThreshold);
                    inventory.getMedications().add(medicine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading inventory from file: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format in file: " + e.getMessage(), e);
        }

        return inventory;
    }

    public String MedicineToString(Medicine medicine) {
        if (medicine != null) {
            return String.join("|",
                    medicine.getName(),
                    String.valueOf(medicine.getStockLevel()),
                    String.valueOf(medicine.getLowthreshold())
            );
        }
        return null;
    }

    public void appendMedicine(Medicine medicine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Inventory.txt", true))) {
            writer.write(this.MedicineToString(medicine));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error appending appointment to file: " + e.getMessage(), e);
        }
    }

    public static void writeMedicalRecord(MedicalRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/MedicalRecords.txt"))) {
            writer.write(record.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public MedicalRecord getMedicalRecordById(String patientId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/MedicalRecords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9 && parts[0].equals(patientId)) { // Check if ID matches
                    String name = parts[1];
                    String gender = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String dateOfBirth = parts[4];
                    String contactInformation = parts[5];
                    String bloodType = parts[6];
                    LinkedList<String> pastDiagnosis = MedicalRecord.stringToList(parts[7]);
                    LinkedList<String> pastTreatments = MedicalRecord.stringToList(parts[8]);
                    return new MedicalRecord(patientId, name, age, dateOfBirth, gender, contactInformation, bloodType, pastDiagnosis, pastTreatments);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading patients from file: " + e.getMessage(), e);
        }
        return null; // Return null if no matching patient is found
    }

    public String medicalRecordToString(MedicalRecord medicalRecord) {
        return String.join("|",
                medicalRecord.getPatientID(),
                medicalRecord.getName(),
                medicalRecord.getGender(),
                String.valueOf(medicalRecord.getAge()),
                medicalRecord.getDateOfBirth(),
                medicalRecord.getContactInformation(),
                medicalRecord.getBloodType(),
                MedicalRecord.listToString(medicalRecord.getPastDiagnosis()),
                MedicalRecord.listToString(medicalRecord.getPastTreatments())
        );
    }

    public AppointmentOutcomeRecord createAppointmentRecordsFromFile() {
        Appointment appointment;
        AppointmentOutcomeRecord appointments = new AppointmentOutcomeRecord();
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9) { // Ensure correct number of fields
                    LocalDateTime dateTime = LocalDateTime.parse(parts[0]); // Parse date and time
                    String patientID = parts[1];
                    String doctorID = parts[2];

                    // Parse prescription and treatmentType as LinkedLists
                    LinkedList<String> prescription = new LinkedList<>(Arrays.asList(parts[3].split(",")));
                    LinkedList<String> treatmentType = new LinkedList<>(Arrays.asList(parts[4].split(",")));

                    String consultationNotes = parts[5];
                    String appointmentOutcomes = parts[6];
                    String prescribedMedicationStatus = parts[7];
                    String appointmentStatus = parts[8];

                    // Create Appointment object and add it to AppointmentRecord
                    appointment = new Appointment(
                            dateTime,
                            patientID,
                            doctorID,
                            prescription,
                            treatmentType,
                            consultationNotes,
                            appointmentOutcomes,
                            prescribedMedicationStatus,
                            appointmentStatus
                    );
                    appointments.addAppointment(appointment);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading appointments from file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing appointment data: " + e.getMessage(), e);
        }
        return appointments;
    }

    public AppointmentOutcomeRecord createAppointmentsForDoctor(String doctorIDInp) {
        Appointment appointment;
        AppointmentOutcomeRecord appointments = new AppointmentOutcomeRecord();
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/Appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9 && Objects.equals(parts[2], doctorIDInp)) {
                    LocalDateTime dateTime = LocalDateTime.parse(parts[0]); // Parse date and time
                    String patientID = parts[1];
                    String doctorID = parts[2];

                    // Parse prescription and treatmentType as LinkedLists
                    LinkedList<String> prescription = new LinkedList<>(Arrays.asList(parts[3].split(",")));
                    LinkedList<String> treatmentType = new LinkedList<>(Arrays.asList(parts[4].split(",")));

                    String consultationNotes = parts[5];
                    String appointmentOutcomes = parts[6];
                    String prescribedMedicationStatus = parts[7];
                    String appointmentStatus = parts[8];

                    // Create Appointment object and add it to AppointmentRecord
                    appointment = new Appointment(
                            dateTime,
                            patientID,
                            doctorID,
                            prescription,
                            treatmentType,
                            consultationNotes,
                            appointmentOutcomes,
                            prescribedMedicationStatus,
                            appointmentStatus
                    );
                    appointments.addAppointment(appointment);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading appointments from file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing appointment data: " + e.getMessage(), e);
        }

        return appointments;
    }

    public String appointmentToString(Appointment appointment) {
        if (appointment != null) {
            return String.join("|",
                    appointment.getDateTime().toString(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    String.join(",", appointment.getPrescription()), // Join prescription list with commas
                    String.join(",", appointment.getTreatmentType()), // Join treatment types with commas
                    appointment.getConsultationNotes(),
                    appointment.getAppointmentOutcomes(),
                    appointment.getPrescribedMedicationStatus(),
                    appointment.getAppointmentStatus()
            );
        }
        return null;
    }

    public void appendAppointment(Appointment appointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/Appointments.txt", true))) {
            writer.write(appointmentToString(appointment));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error appending appointment to file: " + e.getMessage(), e);
        }
    }

    public ReplenishRequestRecord createReplenishRequestRecordFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataBase/ReplenishmentRequestRecords.txt"))) {
            String line;
            ReplenishRequestRecord record = new ReplenishRequestRecord();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String name = parts[0];
                    int amount = Integer.parseInt(parts[1]);
                    record.addReplenishmentRequestInit(name, amount);
                }
            }
            return record;
        } catch (IOException e) {
            throw new RuntimeException("Error reading ReplenishRequestRecord from file: " + e.getMessage(), e);
        }
    }

    public String ReplenishRequestToString(ReplenishmentRequest replenishmentRequest) {
        if (replenishmentRequest != null) {
            return String.join("|",
                    replenishmentRequest.getMedicineToReplenish(),
                    String.valueOf(replenishmentRequest.getValueToReplenish())
            );
        }
        return null;
    }

    public void appendReplenishmentRecord(ReplenishmentRequest replenishmentRequest) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/ReplenishmentRequestRecords.txt", true))) {
            writer.write(ReplenishRequestToString(replenishmentRequest));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error appending appointment to file: " + e.getMessage(), e);
        }
    }

    public void removeLineFromFile(String filePath, String lineToRemove) {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(lineToRemove)) {
                    found = true; // Mark the line as found but skip adding it to the list
                } else {
                    lines.add(line); // Keep all other lines
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file: " + e.getMessage(), e);
        }

        if (!found) {
            throw new IllegalArgumentException("The line to remove was not found in the file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file: " + e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        try {
            String filePath = "DataBase/LoginInfo.txt"; // Path to the .txt file
            UserDatabase userDb = new UserDatabase(filePath);
            PasswordHasher passwordHasher = new PasswordHasher();

            // Example: Register a new user
            String userId = "A001";
            String salt = passwordHasher.generateSalt(userId);
            String hashedPassword = passwordHasher.hashPassword("password", salt);

            userDb.writeUser(userId, salt, hashedPassword);
            System.out.println("New user added!");

            // Example: Read all users
            Map<String, String[]> users = userDb.readUsers();
            System.out.println("Current users in the file:");
            for (Map.Entry<String, String[]> entry : users.entrySet()) {
                System.out.println("UserID: " + entry.getKey() + ", Salt: " + entry.getValue()[0] + ", Hashed Password: " + entry.getValue()[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
