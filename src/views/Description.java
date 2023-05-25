package views;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class Description{
	
    private VBox vbox;
	private Button close;
    
	static String CSS_URL = "styles.css";
	
	public Description() {
		vbox = new VBox(); // Your VBox

		vbox.getStyleClass().add("vbox");
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			getText(lines);
		} catch (IOException e) {
			System.out.println("File Not Found!");
		}
		
		for (String line : lines){
			Label label = new Label(line);

            if (line.startsWith("#")) {
                label.getStyleClass().add("heading-label");
            } else {
                label.getStyleClass().add("normal-label");
            }
            
            vbox.getChildren().add(label);
		}
		
		
		close = new Button("CLOSE");
		close.getStyleClass().add("styled-button");
		vbox.getChildren().add(close);
        vbox.setVisible(false); // Initially set to invisible
	
	}
	
	
	public static void getText(ArrayList<String> lines) throws IOException{
		
		try {
            InputStream inputStream = Description.class.getResourceAsStream("DescriptionText.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
	}
	
	
	public void Open(VBox Buttons){
		vbox.setVisible(true);
		Buttons.setVisible(false);
	}
	
	public void close(VBox Buttons){
		vbox.setVisible(false);
		Buttons.setVisible(true);
	}
	
    public VBox getVBox() {
        return vbox;
    }
    
    public Button getButton(){
    	return close;
    }
	
	
	
}
