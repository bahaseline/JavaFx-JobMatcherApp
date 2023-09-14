package com.example.jobagency;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JobMatcherApp extends Application {

    // The currently logged in user
    private User currentUser = new User("admin", "admin", "admin@gmail.com");

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        // Read the jobs data from the CSV file
        List<Job> jobs = new ArrayList<>();
        File jobsFile = new File("src/jobs.csv");
        Scanner scanner = new Scanner(jobsFile);
        scanner.nextLine(); // Skip the header row
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            String title = fields[0];
            String type = fields[1];
            String location = fields[2];
            String education = fields[3];
            String experience = fields[4];
            String salary = fields[5];
            jobs.add(new Job(title, type, location, education, experience, salary));
        }
        scanner.close();


        // Set up the login form
        GridPane loginForm = new GridPane();
        loginForm.setHgap(10);
        loginForm.setVgap(10);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(20));

        // Set up the login form controls
        Label titleLabel = new Label("Welcome, Login and find your match job");
        //add padding to the title
        titleLabel.setPadding(new Insets(15));
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        // Set up the login form controls
        Label usernameLabel = new Label("");
        GridPane.setConstraints(usernameLabel, 0, 1);

        // Set up the login form controls
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 1);
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("");
        GridPane.setConstraints(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 2);
        passwordField.setPromptText("Enter your password");

        Label emailLabel = new Label("");
        GridPane.setConstraints(emailLabel, 0, 3);

        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 3);
        emailField.setPromptText("Enter your email");

        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 4);

        Label loginErrorLabel = new Label();
        loginErrorLabel.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(loginErrorLabel, 0, 5, 2, 1);

        //image 1 for profile
        Image image1 = new Image("file:src/photo78.png");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(40);
        imageView1.setFitHeight(40);
        GridPane.setConstraints(imageView1, 0, 1);
        loginForm.getChildren().add(imageView1);

        //image 2 for password
        Image image2 = new Image("file:src/photo79.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(40);
        imageView2.setFitHeight(40);
        GridPane.setConstraints(imageView2, 0, 2);
        loginForm.getChildren().add(imageView2);

        //image 3 for email
        Image image3 = new Image("file:src/photo80.png");
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(40);
        imageView3.setFitHeight(40);
        imageView3.setClip(new Circle(20, 20, 20));
        GridPane.setConstraints(imageView3, 0, 3);
        loginForm.getChildren().add(imageView3);

        // Set up the login form
        loginForm.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, loginErrorLabel, emailLabel, emailField);
        loginForm.getStyleClass().add("login-form");

        // Set up the job matcher form
        GridPane jobMatcherForm = new GridPane();
        jobMatcherForm.getStyleClass().add("job-matcher-form");
        jobMatcherForm.setHgap(10);
        jobMatcherForm.setVgap(10);
        jobMatcherForm.setAlignment(Pos.CENTER);
        jobMatcherForm.setPadding(new Insets(30));
        Label welcomeLabel = new Label();
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        GridPane.setConstraints(welcomeLabel, 0, 0, 2, 1);

        // Set up the job matcher form controls
        Label titleLabel2 = new Label("Job Matcher App");
        titleLabel2.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        GridPane.setConstraints(titleLabel2, 0, 0, 2, 1);

        Button logoutButton = new Button("Log Out");
        GridPane.setConstraints(logoutButton, 1, 7);

        //creating the jobTable view where we are going to take the data from the csv file
        TableView<Job> jobsTable = new TableView<>();
        jobsTable.setEditable(false);

        //creating the columns for the table view
        TableColumn<Job, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Job, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Job, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Job, String> educationColumn = new TableColumn<>("Education");
        educationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));

        TableColumn<Job, String> experienceColumn = new TableColumn<>("Experience");
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

        TableColumn<Job, Integer> SalaryColumn = new TableColumn<>("Salary");
        SalaryColumn.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        //adding the columns to the table view
        titleColumn.setPrefWidth(150);
        typeColumn.setPrefWidth(150);
        locationColumn.setPrefWidth(150);
        educationColumn.setPrefWidth(150);
        experienceColumn.setPrefWidth(150);
        SalaryColumn.setPrefWidth(150);

        // Set up the table rows mouse click event handler
        jobsTable.setRowFactory(tv -> {
            TableRow<Job> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Job selectedJob = row.getItem();
                    if (selectedJob.getEducation().equals(selectedJob.getEducation())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Job Match");
                        alert.setHeaderText(null);
                        alert.setContentText(" Hey " + currentUser.getUsername() + " this job is a match for you! ");
                        alert.showAndWait();
                    }
                }
            });
            return row;
        });

        jobsTable.getColumns().addAll(titleColumn, typeColumn, locationColumn, educationColumn, experienceColumn, SalaryColumn);

        // Create a table column to save jobs
        ObservableList<Job> savedJobs = FXCollections.observableArrayList();

        TableColumn<Job, Void> saveColumn = new TableColumn<>("Save");
        saveColumn.setCellFactory(param -> new TableCell<Job, Void>() {
            // Create a save button
            Button saveButton = new Button("Save");

            {
                saveButton.setOnAction(event -> {
                    Job savedJob = getTableRow().getItem();
                    if (savedJob != null) {
                        savedJobs.add(savedJob);

                        // Show an alert to indicate job saved
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Job Saved");
                        alert.setHeaderText(null);
                        alert.setContentText("The job has been saved.");
                        alert.showAndWait();

                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                // Show the save button only for non-empty rows
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(saveButton);
                }
            }
        });

        // Add the save column to the table
        jobsTable.getColumns().add(saveColumn);
        jobsTable.setPrefWidth(1100);
        jobsTable.setPrefHeight(1000);

        // Set up the main menu
        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("menu-bar");
        Menu jobMenu = new Menu("Jobs");
        MenuItem viewJobsItem = new MenuItem("View Jobs");
        viewJobsItem.setOnAction(e -> {

            TableView<Job> jobTableView1 = new TableView<>();

            Label jobLabel = new Label("See The Available Jobs And Make Your Match");
            jobLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            jobLabel.getStyleClass().add("job-label");

            jobTableView1.setPlaceholder(new Label("No jobs found"));
            //creating the columns for the table view

            TableColumn<Job, String> jobTitleColumn = new TableColumn<>("Job Title");
            jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Job, String> jobTypeColumn = new TableColumn<>("Job Type");
            jobTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Job, String> jobLocationColumn = new TableColumn<>("Job Location");
            jobLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

            TableColumn<Job, String> jobEducationColumn = new TableColumn<>("Education");
            jobEducationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));

            TableColumn<Job, String> jobExperienceColumn = new TableColumn<>("Experience");
            jobExperienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

            TableColumn<Job, String> jobSalaryColumn = new TableColumn<>("Salary");
            jobSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("Salary"));

            jobTableView1.getColumns().addAll(jobTitleColumn, jobTypeColumn, jobLocationColumn, jobEducationColumn, jobExperienceColumn, jobSalaryColumn);

            // Create an observable list of jobs
            ObservableList<Job> availableJobs = FXCollections.observableArrayList();

            // Add some jobs to the list
            availableJobs.add(new Job("Software Developer", "Full-time", "San Francisco", "Bachelor's degree", "3+ years", "$100,000"));
            availableJobs.add(new Job("Marketing Manager", "Part-time", "New York", "Master's degree", "5+ years", "$80,000"));
            availableJobs.add(new Job("Data Analyst", "Full-time", "Chicago", "Bachelor's degree", "1+ years", "$70,000"));
            availableJobs.add(new Job("HR Coordinator", "Full-Time", "Los Angeles", "Bachelor's degree", "3", "$550,000"));
            availableJobs.add(new Job("Accountant", "Full-Time", "Houston", "Bachelor's degree", "2", "$650,000"));
            availableJobs.add(new Job("Graphic Designer", "Contract", "Boston", "Bachelor's degree", "2", "$40,000"));
            availableJobs.add(new Job("Project Manager", "Full-Time", "Seattle", "Bachelor's degree", "4", "$85,000"));
            availableJobs.add(new Job("Sales Representative", "Full-Time", "Miami", "Bachelor's degree", "1", "$45,000"));
            availableJobs.add(new Job("Customer Service Representative", "Part-Time", "Dallas", "High School Diploma", "1", "$25,000"));
            availableJobs.add(new Job("IT Support Specialist", "Full-Time", "Denver", "Associate's degree", "3", "$60,000"));
            availableJobs.add(new Job("Financial Analyst", "Full-Time", "Minneapolis", "Bachelor's degree", "2", "$70,000"));
            availableJobs.add(new Job("Executive Assistant", "Full-Time", "San Diego", "Associate's degree", "5", "$50,000"));
            availableJobs.add(new Job("Operations Manager", "Full-Time", "Portland", "Bachelor's degree", "3", "$90,000"));
            availableJobs.add(new Job("Nurse Practitioner", "Full-Time", "Philadelphia", "Master's degree", "3", "$110,000"));
            availableJobs.add(new Job("Physical Therapist", "Full-Time", "Baltimore", "Doctorate degree", "2", "$100,000"));
            availableJobs.add(new Job("Social Media Manager", "Full-Time", "Austin", "Bachelor's degree", "3", "$55,000"));
            availableJobs.add(new Job("Web Developer", "Contract", "Atlanta", "Bachelor's degree", "4", "$75,000"));
            availableJobs.add(new Job("Marketing Coordinator", "Full-Time", "Charlotte", "Bachelor's degree", "2", "$45,000"));
            availableJobs.add(new Job("Event Planner", "Contract", "San Jose", "Bachelor's degree", "4", "$60,000"));
            availableJobs.add(new Job("Mechanical Engineer", "Full-Time", "Detroit", "Bachelor's degree", "5", "$95,000"));
            availableJobs.add(new Job("Software Engineer", "Full-Time", "Phoenix", "Bachelor's degree", "3", "$100,000"));
            availableJobs.add(new Job("Technical Writer", "Part-Time", "Washington", "Bachelor's degree", "2", "$35,000"));
            availableJobs.add(new Job("Human Resources Manager", "Full-Time", "Nashville", "Bachelor's degree", "5", "$80,000"));
            availableJobs.add(new Job("Customer Success Manager", "Full-Time", "Tampa", "Bachelor's degree", "3", "$70,000"));
            availableJobs.add(new Job("Business Development Manager", "Full-Time", "Cleveland", "Bachelor's degree", "4", "$85,000"));
            availableJobs.add(new Job("Data Scientist", "Full-Time", "Pittsburgh", "Master's degree", "2", "$110,000"));
            availableJobs.add(new Job("UX Designer", "Full-Time", "Orlando", "Bachelor's degree", "3", "$650,000"));
            availableJobs.add(new Job("Account Manager", "Full-Time", "Raleigh", "Bachelor's degree", "4", "$80,000"));
            availableJobs.add(new Job("Quality Control Inspector", "Full-Time", "Memphis", "High School Diploma", "2", "$30,000"));
            availableJobs.add(new Job("Sales Manager", "Full-Time", "St. Louis", "Bachelor's degree", "5", "$100,000"));
            availableJobs.add(new Job("Content Writer", "Part-Time", "Milwaukee", "Bachelor's degree", "1", "$20,000"));
            availableJobs.add(new Job("Supply Chain Analyst", "Full-Time", "Oklahoma City", "Bachelor's degree", "3", "$65,000"));
            availableJobs.add(new Job("Customer Support Specialist", "Full-Time", "Indianapolis", "Associate's degree", "1", "$30,000"));

            // Set the items of the jobTableView1 table to the available jobs list
            jobTableView1.setItems(availableJobs);
            Stage JobsStage = new Stage();
            JobsStage.setTitle("View Jobs");
            Scene savedJobsScene = new Scene(jobTableView1, 900, 800);
            // set the inline style for the button
            jobTableView1.getStylesheets().add("file:src/style.css");
            JobsStage.setScene(savedJobsScene);
            JobsStage.show();
            System.out.println("View Jobs");
        });
        jobMenu.getItems().add(viewJobsItem);

        //saved job menu
        // Define the jobTableContextMenu context menu
        ContextMenu jobTableContextMenu = new ContextMenu();
        MenuItem saveJobMenuItem = new MenuItem("Saved Job");

        // Add the MenuItem objects to the jobTableContextMenu context menu
        saveJobMenuItem.setOnAction(e -> {
            Job selectedJob = jobsTable.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                savedJobs.add(selectedJob);
                writeSavedJobsToCSV(savedJobs, "src/saved_jobs.csv");

                // Show an alert to indicate job saved
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Job Saved");
                alert.setHeaderText(null);
                alert.setContentText("The job has been saved.");
                alert.showAndWait();
            }
        });
        MenuItem savedJobsItem = new MenuItem("Saved Jobs");
        savedJobsItem.setOnAction(e -> {
            TableView<Job> savedJobsTable = new TableView<>();
            savedJobsTable.setEditable(false);

            // Create TableColumn objects for different attributes of the Job class
            TableColumn<Job, String> savedTitleColumn = new TableColumn<>("Title");
            savedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Job, String> savedTypeColumn = new TableColumn<>("Type");
            savedTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Job, String> savedLocationColumn = new TableColumn<>("Location");
            savedLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

            TableColumn<Job, String> savedEducationColumn = new TableColumn<>("Education");
            savedEducationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));

            TableColumn<Job, String> savedExperienceColumn = new TableColumn<>("Experience");
            savedExperienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

            TableColumn<Job, Integer> savedSalaryColumn = new TableColumn<>("Salary");
            savedSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

            savedJobsTable.getColumns().addAll(savedTitleColumn, savedTypeColumn, savedLocationColumn, savedEducationColumn, savedExperienceColumn, savedSalaryColumn);
            savedJobsTable.setItems(savedJobs);

            // Saved jobs context menu
            ContextMenu savedJobsContextMenu = new ContextMenu();
            MenuItem removeSavedJobMenuItem = new MenuItem("Remove from Saved Jobs");
            removeSavedJobMenuItem.setOnAction(event -> {
                Job selectedJob = savedJobsTable.getSelectionModel().getSelectedItem();
                if (selectedJob != null) {
                    savedJobs.remove(selectedJob);
                    writeSavedJobsToCSV(savedJobs, "src/saved_jobs.csv");
                }
            });
            savedJobsContextMenu.getItems().add(removeSavedJobMenuItem);
            savedJobsTable.setContextMenu(savedJobsContextMenu);

            // Create a column for the remove button
            TableColumn<Job, Void> removeColumn = new TableColumn<>("Remove");
            removeColumn.setCellFactory(param -> new TableCell<Job, Void>() {
                Button removeButton = new Button("Remove");

                {
                    removeButton.setOnAction(event -> {
                        Job jobToRemove = getTableRow().getItem();
                        if (jobToRemove != null) {
                            savedJobs.remove(jobToRemove);
                            removeButton.setStyle("-fx-background-color: #660033; -fx-border-radius: 10px; -fx-text-fill: white;");
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(removeButton);
                    }
                }
            });

            savedJobsTable.getColumns().add(removeColumn);

            // Display the saved jobs table in a separate window or dialog
            Stage savedJobsStage = new Stage();
            savedJobsStage.setTitle("Saved Jobs");
            savedJobsStage.setScene(new Scene(savedJobsTable, 1100, 700));
            savedJobsTable.getStylesheets().add("file:src/style.css");
            savedJobsStage.show();
        });

        // Create a button to save a job
        Button saveJobButton = new Button("Save Job");
        saveJobButton.setOnAction(e -> {
            Job selectedJob = jobsTable.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                savedJobs.add(selectedJob);

                // Show an alert to indicate job saved
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Job Saved");
                alert.setHeaderText(null);
                alert.setContentText("The job has been saved.");
                alert.showAndWait();
            }
        });


        // Adding saveJobMenuItem, separator, and savedJobsItem to the jobTableContextMenu
        jobTableContextMenu.getItems().add(saveJobMenuItem);
        jobTableContextMenu.getItems().add(new SeparatorMenuItem());
        jobTableContextMenu.getItems().add(savedJobsItem);

        // Showing the jobTableContextMenu when the context menu is requested (usually right-click)
        jobsTable.setOnContextMenuRequested(e -> jobTableContextMenu.show(jobsTable, e.getScreenX(), e.getScreenY()));

        // Creating a "Save Job" button
        Button saveJobButton1 = new Button("Save Job");
        saveJobButton1.setOnAction(e -> {
            // Retrieving the selected job from the jobsTable
            Job selectedJob = jobsTable.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                // Adding the selected job to the savedJobs list
                savedJobs.add(selectedJob);

                // Show an alert to indicate job saved
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Job Saved");
                alert.setHeaderText(null);
                alert.setContentText("The job has been saved.");
                alert.showAndWait();
            }
        });

        // Adding saveJobButton to the menu
        saveJobMenuItem.setGraphic(saveJobButton);

        // Adding savedJobsItem to the jobMenu
        jobMenu.getItems().add(savedJobsItem);

        //apply for a job position
        MenuItem applyJobItem = new MenuItem("Apply for Job");

        applyJobItem.setOnAction(e -> {
            // create the UI elements for the job application form
            Label nameLabel = new Label("Name:");
            TextField nameField = new TextField();
            Label jobtypeLabel = new Label("Name:");
            TextField jobtypelabelField = new TextField();
            Label emailLabell = new Label("Email:");
            TextField emailFieldd = new TextField();
            Label resumeLabel = new Label("Resume:");
            Button uploadButton = new Button("Upload");
            uploadButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose a file to upload");
                File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
                if (selectedFile != null) {
                    // Handle the selected file
                    String fileName = selectedFile.getName();
                    // Display an alert with the selected file name
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selected file: " + fileName, ButtonType.OK);
                    alert.showAndWait();
                } else {
                    // Handle the case when no file is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No file selected.", ButtonType.OK);
                    alert.showAndWait();
                }
            });

            // create a new window to show the job application form
            Stage jobApplicationWindow = new Stage();
            jobApplicationWindow.setTitle("Job Application Form");

            // create a VBox to hold the UI elements for the job application form
            VBox jobApplicationForm = new VBox();
            jobApplicationForm.getChildren().addAll(
                    nameLabel, nameField,
                    emailLabell, emailFieldd,
                    resumeLabel, uploadButton, jobtypeLabel, jobtypelabelField
            );
            // set the spacing and padding of the VBox
            jobApplicationForm.setSpacing(10);
            jobApplicationForm.setPadding(new Insets(10));

            // create a button to submit the job application
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(event -> {
                // create a file named "apply.txt" to store the job application information
                File file = new File("src/apply.txt");
                try (PrintWriter output = new PrintWriter(file)) {
                    // write the job application information to the file
                    output.println("Name: " + nameField.getText());
                    output.println("Job Type: " + jobtypeLabel.getText());
                    output.println("Email: " + emailFieldd.getText());

                    System.out.println("Job application information written to apply.txt.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // show confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Job Application");
                alert.setHeaderText(null);
                alert.setContentText("Your application has been submitted successfully!");
                alert.showAndWait();
                jobApplicationWindow.close();
            });
            // add the submit button to the job application form
            jobApplicationForm.getChildren().add(submitButton);

            // create a new scene with the job application form and show it in the new window
            Scene jobApplicationScene = new Scene(jobApplicationForm, 500, 300);
            jobApplicationWindow.setScene(jobApplicationScene);
            jobApplicationWindow.show();
            jobApplicationForm.setStyle("-fx-padding: 20;"); // Add padding to the dialog layout
            jobApplicationForm.setStyle("-fx-background-color: #ffb380; -fx-padding: 10px;");
            jobApplicationForm.getStylesheets().add("file:src/style.css");
            submitButton.setStyle("-fx-background-color: #660033; -fx-border-radius: 10px; -fx-text-fill: white;");
            uploadButton.setStyle("-fx-background-color: #660033; -fx-border-radius: 10px; -fx-text-fill: white;");
        });
        jobMenu.getItems().add(applyJobItem);
        menuBar.getMenus().add(jobMenu);

        //user menu item
        Menu userMenu = new Menu("User");

        MenuItem viewProfileItem = new MenuItem("View Profile");
        viewProfileItem.setOnAction(e -> {
            // Create labels for profile information
            Label nameLabel = new Label("Name: John Doe");
            nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label emailLabell = new Label("Email: john.doe@example.com");
            emailLabell.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label ageLabel = new Label("Age: 30");
            ageLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label educationLabel = new Label("Education: Bachelor's Degree");
            educationLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label experienceLabel = new Label("Experience: 5 years");
            experienceLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label skillsLabel = new Label("Skills: Java, Python, SQL");
            skillsLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

            // Write the profile information to a text file
            String profileText = nameLabel.getText() + "\n" +
                    emailLabell.getText() + "\n" +
                    ageLabel.getText() + "\n" +
                    educationLabel.getText() + "\n" +
                    experienceLabel.getText() + "\n" +
                    skillsLabel.getText() + "\n";

            // Write the profile information to a text file
            try {
                FileWriter writer = new FileWriter("src/profile.txt");
                writer.write(profileText);
                writer.close();
                System.out.println("Profile information written to profile.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Create a VBox container for the profile information
            VBox profileBox = new VBox();
            profileBox.getChildren().addAll(nameLabel, emailLabell, ageLabel, educationLabel, experienceLabel, skillsLabel);
            profileBox.setSpacing(10);
            profileBox.setPadding(new Insets(10));
            profileBox.getStyleClass().add("user-profile"); // Apply CSS class

            // Create a Scene with the profileBox as the root node
            Scene profileScene = new Scene(profileBox, 500, 500);
            profileScene.getStylesheets().add("file:src/style.css"); // Apply external CSS

            // Create a Stage for displaying the profileScene
            Stage profileStage = new Stage();
            profileStage.setTitle("View Profile");
            profileStage.setScene(profileScene);
            profileStage.show();

        });

        //create a menu item for edit profile
        MenuItem editProfileItem = new MenuItem("Edit Profile");
        editProfileItem.setOnAction(e -> {
            // create the UI elements for the Edit Profile form
            Label nameLabel = new Label("Name:");
            TextField nameField = new TextField();
            Label emailLabell = new Label("Email:");
            TextField emailFieldd = new TextField();
            Label passwordLabell = new Label("Password:");
            PasswordField passwordFieldd = new PasswordField();

            // create a new window to show the Edit Profile form
            Stage editProfileWindow = new Stage();
            editProfileWindow.setTitle("Edit Profile");

            // create a VBox to hold the UI elements for the Edit Profile form
            VBox editProfileForm = new VBox();
            editProfileForm.getChildren().addAll(
                    nameLabel, nameField,
                    emailLabell, emailFieldd,
                    passwordLabell, passwordFieldd
            );
            // add spacing and padding to the VBox
            editProfileForm.setSpacing(10);
            editProfileForm.setPadding(new Insets(10));
            editProfileWindow.setWidth(500);
            editProfileWindow.setHeight(400);

            // create a button to submit the updated profile information
            Button submitButton = new Button("Submit");
            submitButton.setStyle("-fx-background-color: #660033; -fx-border-radius: 10px; -fx-text-fill: white;");
            submitButton.setOnAction(event -> {
                // Validate user input
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                // Display an error message if any field is empty
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    // Display an error message if any field is empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                    // Display an error message if the email is invalid
                } else if (!name.matches("^[a-zA-Z]+$")) {
                    // Display an error message if the name contains non-alphabetic characters
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid name (letters only).");
                    alert.showAndWait();
                } else {
                    // Update the user's profile with the new information
                    currentUser.setUsername(name);
                    currentUser.setPassword(password);
                    currentUser.setEmail(email);

                    // Close the edit profile window
                    editProfileWindow.close();

                    // Show confirmation message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Edit Profile");
                    alert.setHeaderText(null);
                    alert.setContentText("Your profile has been updated successfully!");
                    alert.showAndWait();
                }
            });

            // Add the submit button to the VBox
            editProfileForm.getChildren().add(submitButton);
            Scene editProfileScene = new Scene(editProfileForm);
            editProfileForm.setStyle("-fx-background-color: #ffb380; -fx-padding: 10px;");
            editProfileForm.getStylesheets().add("file:src/style.css");
            editProfileWindow.setScene(editProfileScene);
            editProfileWindow.show();
        });

        // Create a menu item for viewing the user's profile
        userMenu.getItems().addAll(viewProfileItem, editProfileItem);
        menuBar.getMenus().add(userMenu);

        // Set up the main scene
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(menuBar);
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        Scene mainScene = new Scene(root, 640, 480);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // Set up the upload menu item
        Menu fileMenu = new Menu("Upload");
        MenuItem uploadItem = new MenuItem("Upload Job");
        fileMenu.getItems().add(uploadItem);
        menuBar.getMenus().add(fileMenu);

        // Set up the layout
        VBox vbox = new VBox();
        vbox.getChildren().add(menuBar);
        Scene scene = new Scene(vbox, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        uploadItem.setOnAction(event -> {
            // Create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Make the dialog window modal

            // Create text fields for job information
            TextField titleField = new TextField();
            TextField typeField = new TextField();
            TextField locationField = new TextField();
            TextField educationField = new TextField();
            TextField experienceField = new TextField();
            TextField salaryField = new TextField();

            // Create a submit button
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(submitEvent -> {
                // Get the entered job information
                String title = titleField.getText();
                String type = typeField.getText();
                String location = locationField.getText();
                String education = educationField.getText();
                String experience = experienceField.getText();
                String salary = salaryField.getText();

                // Check if any field is empty
                if (title.isEmpty() || type.isEmpty() || location.isEmpty() || education.isEmpty() || experience.isEmpty() || salary.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.", ButtonType.OK);
                    alert.showAndWait();
                    return; // Abort submission
                }

                jobs.add(new Job(title, type, location, education, experience, salary));

                // Write the job information to the jobs.csv file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/jobs.csv", true))) {
                    writer.write(title + "," + type + "," + location + "," + education + "," + experience + "," + salary);
                    writer.newLine();
                    writer.flush();

                    // Display an alert to indicate successful addition of job information
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Job uploaded: " + title + ", " + type + ", " + location + ", " + education + ", " + experience + ", " + salary, ButtonType.OK);
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while writing to the file.", ButtonType.OK);
                    alert.showAndWait();
                }

                // Close the dialog stage after submitting
                dialogStage.close();
            });

            // Create a layout for the dialog
            VBox dialogVbox = new VBox();
            dialogVbox.setSpacing(10); // Add spacing between elements
            dialogVbox.setPrefWidth(600); // Set preferred width
            dialogVbox.setPrefHeight(700); // Set preferred height
            dialogVbox.setStyle("-fx-padding: 20;"); // Add padding to the dialog layout
            dialogVbox.setStyle("-fx-background-color: #ffb380; -fx-padding: 10px;");
            dialogVbox.getStylesheets().add("file:src/style.css");
            submitButton.setStyle("-fx-background-color: #660033; -fx-border-radius: 10px; -fx-text-fill: white;");

            // Add elements to the dialog layout
            dialogVbox.getChildren().addAll(
                    new Label("Title:"), titleField,
                    new Label("Type:"), typeField,
                    new Label("Location:"), locationField,
                    new Label("Education:"), educationField,
                    new Label("Experience:"), experienceField,
                    new Label("Salary:"), salaryField,
                    submitButton);

            // Set the scene of the dialog stage
            dialogStage.setScene(new Scene(dialogVbox));

            // Show the dialog stage
            dialogStage.showAndWait();
        });

        // Creating a TextField for searching jobs
        TextField searchField = new TextField();
        // Setting a prompt text for the searchField
        searchField.setPromptText("Search jobs by title, location, education, experience, salary, or job type");
        // Adding a listener to the textProperty of the searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {// Filtering the jobs based on the search criteria and updating the jobsTable
            ObservableList<Job> filteredJobs = filterJobs(jobs, newValue, newValue, newValue, newValue, newValue);
            jobsTable.setItems(filteredJobs);
        });
        // Setting the position of the searchField in the GridPane
        GridPane.setConstraints(searchField, 0, 6);
        // Creating a searchButton
        Button searchButton = new Button("Search");
        // Setting an action for the searchButton when it is clicked
        searchButton.setOnAction(event -> {// Filtering the jobs based on the search criteria and updating the jobsTable
            ObservableList<Job> filteredJobs = filterJobs(jobs, searchField.getText(), searchField.getText(), searchField.getText(), searchField.getText(), searchField.getText());
            jobsTable.setItems(filteredJobs);
        });
        // Setting the position of the searchButton in the GridPane
        GridPane.setConstraints(searchButton, 1, 6);
        // Creating a Label for displaying any job matcher errors
        Label jobMatcherErrorLabel = new Label();
        // Setting the position of the jobMatcherErrorLabel in the GridPane
        GridPane.setConstraints(jobMatcherErrorLabel, 0, 2, 2, 1);
        // Adding the necessary nodes to the jobMatcherForm GridPane
        jobMatcherForm.getChildren().addAll(titleLabel2, logoutButton, searchField, searchButton, jobMatcherErrorLabel);
        jobMatcherForm.add(menuBar, 0, 0);
        jobMatcherForm.add(jobsTable, 0, 3, 3, 1);

        // Set up the main scene + css
        Scene loginScene = new Scene(loginForm, 900, 600);
        Scene jobMatcherScene = new Scene(jobMatcherForm, 1100, 700);
        jobMatcherForm.getStylesheets().add("file:src/style.css");
        loginForm.getStylesheets().add("file:src/style.css");

        // Set up the stage
        primaryStage.setTitle("Job Matcher App");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Set up the event handlers
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            if (authenticate(username, password, email)) {
                currentUser = new User(username, password, "admin@gmail.com");
                welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
                primaryStage.setScene(jobMatcherScene);
            } else {
                loginErrorLabel.setText("Invalid username, password, or email.");
            }

        });

        //logout
        logoutButton.setOnAction(event -> {
            currentUser = null;
            usernameField.clear();
            passwordField.clear();
            emailField.clear();
            searchField.clear();
            jobsTable.setItems(FXCollections.observableArrayList());
            primaryStage.setScene(loginScene);
        });
    }

    // Authenticate the user
    public boolean authenticate(String username, String password, String email) {
        // Check if the username, password, and email match a user in the system
        if (username.equals("admin") && password.equals("admin") && email.equals("admin@gmail.com")) {
            return true;
        } else if (username.equals("user") && password.equals("password") && email.equals("user@gmail.com")) {
            return true;
        } else {
            return false;
        }
    }

    // Filter the jobs based on the search criteria
    private ObservableList<Job> filterJobs(List<Job> jobs, String searchTerm, String educationFilter, String experienceFilter, String salaryFilter, String jobTypeFilter) {
        // Filter the jobs by title, location, education, experience, salary, and job type
        return FXCollections.observableArrayList(jobs.stream()
                .filter(job -> job.getTitle().toLowerCase().contains(searchTerm.toLowerCase())
                        || job.getLocation().toLowerCase().contains(searchTerm.toLowerCase())
                        || job.getEducation().toLowerCase().contains(educationFilter.toLowerCase())
                        || job.getExperience().toLowerCase().contains(experienceFilter.toLowerCase())
                        || job.getSalary().toLowerCase().contains(salaryFilter.toLowerCase())
                        || job.getType().toLowerCase().contains(jobTypeFilter.toLowerCase()))
                .collect(Collectors.toList()));
    }

    private void writeSavedJobsToCSV(List<Job> savedJobs, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the CSV header
            writer.write("Title,Type,Location,Education,Experience,Salary\n");

            // Write each job as a CSV row
            for (Job job : savedJobs) {
                writer.write(String.format("%s,%s,%s,%s,%s,%d\n",
                        job.getTitle(),
                        job.getType(),
                        job.getLocation(),
                        job.getEducation(),
                        job.getExperience(),
                        job.getSalary()));
            }

            System.out.println("Saved jobs written to savedjobs.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}