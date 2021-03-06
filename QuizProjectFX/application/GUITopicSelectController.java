package application;
    
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUITopicSelectController extends Scene {
	
	//The selection box
	private ArrayList<String> checkBoxSelected = new ArrayList<String>();
	
	//The question number selection
	private boolean topicPicked = false;
	private boolean questionsPicked = false;
	private int maxQuestions;
	private int numQuestions;
	private Label questionsLabel;
	private TextField questionsText;
	
	//Scene Controllers
	private Stage Primary;
	private List<Scene> sceneList;
	private List<Question> qListFinal;
	Parent root;   // Used by HomeController
	
    public GUITopicSelectController(Parent root,Stage Primary, List<Scene> sceneList, List<Question> qListFinal, int[] correct) {
      super(root,800,600);
      
      //Set constructor variables to class variables
      this.sceneList = sceneList;
      this.Primary = Primary;
      this.qListFinal = qListFinal;
      
      //Primary.setTitle("Topic Select");
      BorderPane parent = (BorderPane) root;
      this.root = parent;
      this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      
      VBox vbox = new VBox();

      Label titleLabel = new Label("Select a Topic:");
      titleLabel.setTranslateY(100);
      titleLabel.setId("title-text");
      //titleLabel.setFont(new Font("Arial", 55));

      vbox.setSpacing(10);
      vbox.getChildren().add((titleLabel));
      vbox.setAlignment(Pos.CENTER);
      
      //Getting the list of topics
      List<String> topicList = Main.table.getTopicList();

      //topicSelectBox.setTranslateY(100);
      
      //Sense when a topic is selected
      //topicSelectBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> pickTopic());
      
      //vbox.getChildren().add(topicSelectBox);
      for (int i = 0; i < topicList.size(); i++) { 

          //Create a Checkbox and move it to the right spot 
          CheckBox c = new CheckBox(topicList.get(i)); 
          c.setTranslateY(100);
          
          String thisTopic = topicList.get(i);
          
          //Add the checkbox to the vbox
          vbox.getChildren().add(c); 

          //Set to be indeterminate 
          c.setIndeterminate(true); 
          
          //Add event handler for when checked
          
          // create a event handler 
          EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 

              public void handle(ActionEvent e) 
              { 
                  if (c.isSelected()) {
                	  checkBoxSelected.add(thisTopic);
                	  pickTopic(false);
                  } else {
                	  checkBoxSelected.remove(thisTopic);
                	  pickTopic(true);
                  }
              } 
          };
          
          c.setOnAction(event);  
          
      } 
      
      questionsLabel = new Label("# of Questions (0): ");
      questionsText = new TextField();
      HBox questions = new HBox(8);
      questions.getChildren().addAll(questionsLabel,questionsText);
      questions.setTranslateY(100);
      questions.setAlignment(Pos.CENTER);
      
      vbox.getChildren().add(questions);
      
      parent.setTop(vbox);
      
      Button button = new Button("Take Quiz");
      parent.setCenter(button);
      button.setOnAction(e ->startButton());
  }
    
  private void pickTopic(Boolean rem) {
	  if (!rem) {
		  topicPicked = true;
		  questionsPicked = false;
		  maxQuestions = 0;
		  for (int i=0; i<checkBoxSelected.size(); i++) {
			  maxQuestions += Main.table.getQuestionsList(checkBoxSelected.get(i)).size();
		  }
		  questionsLabel.setText("# of Questions (1-"+maxQuestions+ ") : ");
	  } else {
    	  questionsPicked = false;
    	  if (checkBoxSelected.size()==0) {
    		  topicPicked = false;
    		  questionsLabel.setText("# of Questions (0): ");
    	  } else {
    		  maxQuestions = 0;
        	  for (int i=0; i<checkBoxSelected.size(); i++) {
        		  maxQuestions += Main.table.getQuestionsList(checkBoxSelected.get(i)).size();
        	  }
    		  questionsLabel.setText("# of Questions (1-"+maxQuestions+ ") : ");
    	  }
	  }
  }
  
  private void pickQuestion() {
	  String numQuestionsString = (String) questionsText.getText();
	  try
        {
	      numQuestions = Integer.parseInt(numQuestionsString);
        } catch (NumberFormatException e)
        {
            Alert alert = new Alert(AlertType.ERROR, "Please enter the number of questions you would like");
            alert.show();
        }
	  
	  if (numQuestions > maxQuestions) {
		  numQuestions = maxQuestions;
	  }
	  if (topicPicked && numQuestions >= 1 && numQuestions <= maxQuestions) {
		  questionsPicked = true;
	  }
  }
  
  public VBox updateTopics() {
	  qListFinal.clear();
	  checkBoxSelected.clear();
	  
	  VBox vbox = new VBox();

      Label titleLabel = new Label("Select a Topic:");
      titleLabel.setTranslateY(100);
      titleLabel.setId("title-text");
      //titleLabel.setFont(new Font("Arial", 55));

      vbox.setSpacing(10);
      vbox.getChildren().add((titleLabel));
      vbox.setAlignment(Pos.CENTER);
      
      //Getting the list of topics
      List<String> topicList = Main.table.getTopicList();

      //topicSelectBox.setTranslateY(100);
      
      //Sense when a topic is selected
      //topicSelectBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> pickTopic());
      
      //vbox.getChildren().add(topicSelectBox);
      for (int i = 0; i < topicList.size(); i++) { 

          //Create a Checkbox and move it to the right spot 
          CheckBox c = new CheckBox(topicList.get(i)); 
          c.setTranslateY(100);
          
          String thisTopic = topicList.get(i);
          
          //Add the checkbox to the vbox
          vbox.getChildren().add(c); 

          //Set to be indeterminate 
          c.setIndeterminate(true); 
          
          //Add event handler for when checked
          
          // create a event handler 
          EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 

              public void handle(ActionEvent e) 
              { 
                  if (c.isSelected()) {
                	  checkBoxSelected.add(thisTopic);
                	  pickTopic(false);
                  } else {
                	  checkBoxSelected.remove(thisTopic);
                	  pickTopic(true);
                  }
              } 
          };
          
          c.setOnAction(event);  
          
      } 
      
      questionsLabel = new Label("# of Questions (0): ");
      questionsText = new TextField();
      HBox questions = new HBox(8);
      questions.getChildren().addAll(questionsLabel,questionsText);
      questions.setTranslateY(100);
      questions.setAlignment(Pos.CENTER);
      
      vbox.getChildren().add(questions);
      
      //parent.setTop(vbox);

      return vbox;
  }
  
  private void startButton() {
	  pickQuestion();
	  if (questionsPicked && topicPicked) {
		  
		  //Generate random questions
		  //Get a list of total questions
		  List<Question> qList = new ArrayList<Question>();
		  for (int i=0; i<checkBoxSelected.size(); i++) 
		  {
    		      qList.addAll(Main.table.getQuestionsList(checkBoxSelected.get(i)));
    	          }
		  
		  
		  //List<Question> qListFinal = new ArrayList<Question>();
		  int randNum = 0;
		  Random rand = new Random();
		  for (int i = 0; i<numQuestions;i++) {
			  randNum = rand.nextInt(qList.size());
			  qListFinal.add(qList.get(randNum));
			  qList.remove(randNum);
		  }
		  
		  
		  // Question & Answer Layout
		  Primary.setScene(sceneList.get(1));
		  
		  // Reset these to false for next time the quiz is started
		  questionsPicked = false; 
		  maxQuestions = 0;
		  topicPicked = false;
		  
		  VBox questionDisplay;
		  
		  questionDisplay = (((GUIQuizController) sceneList.get(1)).displayQuestion(((GUIQuizController)sceneList.get(1)).root, Primary, sceneList, 0, ((GUIQuizController)sceneList.get(1)).correct));        
		  ((BorderPane) ((GUIQuizController) sceneList.get(1)).root).setCenter(questionDisplay);
		  ((GUIQuizController) sceneList.get(1)).questionNumber = 0;
		  ((GUIQuizController) sceneList.get(1)).correct[0] = 0;
		  
	  }
  }
}