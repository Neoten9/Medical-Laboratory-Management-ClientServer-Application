Medical Laboratory Management Client/Server Application :

This is a client-server application designed for the management of a medical laboratory. It uses JavaFX for the client interface and a MySQL database for storing data. The following instructions will guide you on how to set up and run the project using NetBeans.

Prerequisites :

Before running the application, make sure you have the following:

NetBeans IDE – Install the latest version of NetBeans (preferably 12.x or higher).

Java Development Kit (JDK) – You need JDK 8 or higher. In this project, we are using Zulu OpenJDK 22 (which includes JavaFX).

XAMPP or MySQL – This will be used to run your MySQL database locally.

Setup Instructions : 

1. Set up JavaFX
To use JavaFX with NetBeans, you need to configure the JavaFX SDK.

Navigate to the zulu22.30.13-ca-fx-jdk22.0.1-win_x64 folder in your project directory.

In NetBeans:

Go to Tools → Libraries.

Click New Library and name it something like JavaFX.

Select the Classpath tab, and add all the JAR files from the zulu22.30.13-ca-fx-jdk22.0.1-win_x64\lib folder.

2. Add the Required JAR Files
The project requires several external libraries which are listed in the jarfiles folder.

Navigate to the jarfiles folder in your project directory.

In NetBeans:

Right-click your project in the Projects window and select Properties.

Under the Libraries section, click Add JAR/Folder.

Select all the .jar files from the jarfiles folder.

3. Set up the Database
You need a MySQL database running on your local machine to use this application.

Install XAMPP (or any other MySQL service you prefer).

Open phpMyAdmin (available through XAMPP).

Create a new database called "labo".

4. Run the Project : 

Once everything is set up, you can start running the application.

Step 1: Start the Server

Open the Server.java file located in the src/Server package.

Right-click on the file and select Run File to start the server. The server will listen for client connections.

Step 2: Run the JavaFX Client

Open the JavaFXApplication2.java file located in the JavaFXApplication2 package.

Right-click on the file and select Run File to start the client interface.

The client will connect to the server and allow interaction with the database.

Troubleshooting :

JavaFX Error: If you encounter issues with JavaFX not being recognized, make sure that the JavaFX SDK is correctly configured in your project properties.

Database Connection Issues: Ensure that your database is running and that the credentials in the project are correct.

Conclusion
With this setup, you should be able to run the client-server application on your local machine. If you encounter any issues or need additional help, feel free to open an issue on the project’s GitHub page.