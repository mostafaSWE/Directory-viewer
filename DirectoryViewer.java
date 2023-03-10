import java.io.File;
import javafx.collections.ListChangeListener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.util.ArrayList;



public class DirectoryViewer extends Application {
	private ArrayList<File> foldersArray = new ArrayList<File>();
	private TextArea textArea = new TextArea();
    @Override
    public void start(Stage primaryStage) {
    	
        TreeView<String> treeView = new TreeView<String>();
        
        
        
        textArea.setEditable(false);
        textArea.setPrefHeight(380);
        BorderPane borderPane = new BorderPane();
        Button select = new Button("Select Folder");
        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DirectoryChooser dc = new DirectoryChooser();
                dc.setInitialDirectory(new File(System.getProperty("user.home")));
                File choice = dc.showDialog(primaryStage);
                if(choice == null || ! choice.isDirectory()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("The file is invalid.");

                    alert.showAndWait();
                } else {
                	
                	treeView.setRoot(getNodesForDirectory(choice));
                    outputWindow dir= new outputWindow();
                    textArea.setText(dir.showFiles(choice.listFiles(), 0));
                   
                }
            }
        });
       
        
        

        VBox vbox = new VBox(textArea);
        vbox.setPadding((new Insets(20, 20, 20, 20)));
        
        borderPane.setBottom(select);
        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);
        primaryStage.setScene(new Scene(borderPane, 600, 400));
        primaryStage.setTitle("Folder View");
        primaryStage.show();
        
        treeView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem<String>>()
        {
            @Override
            public void onChanged(Change<? extends TreeItem<String>> change)
            {
            	
                TreeItem<String> node = treeView.getSelectionModel().getSelectedItem();
                
                if (node == null)
                    System.out.println("node is null"); 
                	
                else {
                    for (File f : foldersArray) {
                    	if(f.getName().equals(node.getValue())) {
                    		outputWindow dir= new outputWindow();
                    		String s= dir.showFiles(f.listFiles(), 0);
                    		textArea.setText(s);
                  
                    		
                            
                    	}
                    	
                    }
                    
                	
                }
                	
            }
        });

       
    }
        	
        
    


    public static void main(String[] args) {
        launch(args);
    }


    private TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for(File f : directory.listFiles()) {
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
                foldersArray.add(f);
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }
    
    
}