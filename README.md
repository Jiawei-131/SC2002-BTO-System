# SC2002 BTO Management System

## The primary goal of this assignment is to:

- Apply Object-Oriented Design Programming (OODP) concepts in a real-world application.
- Design and develop an OODP application using Java.
- Collaboratively work as a team to achieve a common objective.
- Gain practical experience with Java as an object-oriented programming language.
- Gain expereince working with Unified Modeling Language (UML) class and sequence diagrams

## Overview
This Java-based CLI system simulates a Building and Town Ownership (BTO) application process. It allows Applicants to view and apply for BTO projects, while Officers and Managers handle project registration, application management, flat booking, and enquiry replies. The system implements role-based access control and file-based databases for persistent data storage.

## Features 
- User Registration & Authentication:
  - Hashed passwords for secure login.
  - Role-based access control (Applicant, Officer, Manager).
- Project Management:
  - Applicants: View and apply for BTO projects.
  - Officers: Manage projects, applications, and bookings.
  - Managers: Full access to all features.
-Flat Booking: Officers can book flats for Applicants.
- File-Based Databases: Persistent storage for userdata, logininfo, projects details and applications in txt files.

##	Prerequisites:
- JDK 17+
- Java-compatible IDE
- Git or ZIP download to access project.
  
##	Project Structure:
- **BTOSystem/src**: Contains the main source code.
  - **Controllers**: Manages the application's flow and logic.
  - **Data**: Handles data storage and retrieval (file-based databases).
  - **Entities**: Contains the core classes representing users, projects, applications, etc.
  - **Handlers**: Manages actions related to applications and projects.
  - **Util**: Utility classes (e.g., DateTimeController).
  - **View**: Manages user interface and input/output.
  - **BTOSystem (main)**: The main class that runs the application.
### Documentation References
- **UML Class Diagrams**: Refer to the UML class diagrams for an overview of the class relationships.
- **UML Sequence Diagrams**: Check the sequence diagrams for the execution flow and interaction between components.
- **JavaDoc**: Use the JavaDoc for detailed logic and method descriptions in the code.

## Acknowledgements
### Contributors
- **[Wei Hao](https://github.com/WeiHaoChin)**
- **[Georgina](https://github.com/georginacyc)**
- **[Jia Wei](https://github.com/Jiawei-131)**
- **[Qie Min](https://github.com/qmkoh)**
- **[Joel](https://github.com/Joelnwh)**

