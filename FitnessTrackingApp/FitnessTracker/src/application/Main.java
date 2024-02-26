package application;
	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;



public class Main extends Application {
	int goal=0;
	int current=0;
	ProgressBar p;
	ProgressIndicator pi;
	ArrayList<Activity> exercises= new ArrayList<Activity>();
	
    List<String> exerciseOptions = List.of("Running 🏃‍♀️", "Swimming 🏊🏻‍♀️","Cycling 🚴🏼‍♀️","Yoga 🧘🏻‍♀️","Weightlifting 🏋", "Gym 💪🏻", "Other");

	public void showAlert(Alert.AlertType alert, String title, String content) {
		Alert alTy = new Alert(alert);
		alTy.setTitle(title);
		alTy.setHeaderText(null);
		alTy.setContentText(content);
		alTy.showAndWait();
	}
	public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);

        VBox navigationPanel = new VBox();
        navigationPanel.setPadding(new Insets(10));
        navigationPanel.setSpacing(10);
        BackgroundFill backgroundFill1 = new BackgroundFill(Color.LAVENDER,new CornerRadii(0),new Insets(0));
		Background background1 = new Background(backgroundFill1);
        navigationPanel.setBackground(background1);
        
        Button recordWorkoutButton = new Button("Record Workout");
        Button setGoalsButton = new Button("Set Goals");
        Button viewProgressButton = new Button("View Progress");
        Label goalsLabel = new Label("Enter your desired calories to burn");
        TextField goals = new TextField();
        Button goalsDone = new Button ("DONE");
        
        navigationPanel.getChildren().addAll( setGoalsButton,recordWorkoutButton, viewProgressButton);
        root.setLeft(navigationPanel);

        StackPane mainContent = new StackPane();
        root.setCenter(mainContent);
        recordWorkoutButton.setOnAction(event -> {
            mainContent.getChildren().clear();
            ComboBox<String> exercisesDropdown = new ComboBox<>();
            exercisesDropdown.getItems().addAll(exerciseOptions);
            exercisesDropdown.setPromptText("Select an exercise");
            TextField otherExerciseInput = new TextField();
            otherExerciseInput.setDisable(true);
            otherExerciseInput.setPromptText("Specify other exercise");
            TextField calorieInput = new TextField();
            calorieInput.setPromptText("Calories Burned");
            Button addExerciseButton = new Button("Add Exercise \u2795");
            VBox recordWorkoutContent = new VBox(new Label("Record Workout Page"),exercisesDropdown,otherExerciseInput,calorieInput,addExerciseButton);
            mainContent.getChildren().addAll(recordWorkoutContent);
            exercisesDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
                otherExerciseInput.setDisable(!"Other".equals(newVal));
                if (!"Other".equals(newVal)) {
                    otherExerciseInput.clear();
                }
            });
            addExerciseButton.setOnAction(e -> {
                String selectedExercise = exercisesDropdown.getValue();
                if ("Other".equals(selectedExercise)) {
                    selectedExercise = otherExerciseInput.getText();
                }
                int calorie = Integer.parseInt(calorieInput.getText());
                current +=Integer.parseInt(calorieInput.getText());
                Activity a = new Activity(selectedExercise, calorie);
                exercises.add(a);
                exercisesDropdown.getSelectionModel().clearSelection();
                calorieInput.clear();
                otherExerciseInput.clear();
                showAlert(Alert.AlertType.INFORMATION,"Done","You have successfully added your exercise \n Your total burned calories is "+ current);
            });
        });
        
        setGoalsButton.setOnAction(event -> {
            mainContent.getChildren().clear();
            VBox setGoalsContent = new VBox(new Label("Set Goals Page"),goalsLabel,goals,goalsDone);
            mainContent.getChildren().addAll(setGoalsContent);            
        });
        
        goalsDone.setOnAction(event -> {
        	goal=Integer.parseInt(goals.getText());
        	showAlert(Alert.AlertType.INFORMATION,"Done","Your Goal calories to burn is "+ goal);});
        
        viewProgressButton.setOnAction(event -> {
            mainContent.getChildren().clear();
            if(current>goal) {
            	 p = new ProgressBar(1);
            	 pi = new ProgressIndicator(1);
            	 showAlert(Alert.AlertType.INFORMATION,"Congrats","You have completed your fitness goal");
            	
            }
            else if(goal==0) {
           	 p = new ProgressBar(0);
           	 pi = new ProgressIndicator(0);
             showAlert(Alert.AlertType.INFORMATION,"No Goal Set","You have to set your goal");
                
            }
            else {
            	float f=(float)(current)/(float)(goal);
            	 p = new ProgressBar(f);
            	 pi = new ProgressIndicator(f);
            	}
            Label exercisesDone=new Label();
            String s = "";
            for(int i=0;i<exercises.size();i++) {
            	s+=exercises.get(i).getTitle()+"    "+exercises.get(i).getCalories()+"\n";
            	if(i+1==exercises.size()) {
            		s+=current;
            	}
            }
            exercisesDone.setText(s);
            VBox viewProgressContent = new VBox(new Label("View Progress Page"),p,pi,exercisesDone);
            mainContent.getChildren().add(viewProgressContent);
        });
        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Fitness Tracker App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
class Activity{
	private String Title;
	private int calories;
	public Activity(String t, int c) {
		this.Title=t;
		this.calories = c;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	
}
