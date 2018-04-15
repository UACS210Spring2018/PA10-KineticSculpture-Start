import java.util.ArrayList;
import java.util.List;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PA10Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Line source_to_thing1, source_to_thing2, thing1_to_sink, thing2_to_sink;
    Group root;
    
    @Override
    public void start(Stage primaryStage) {

        // ===== set up the scene with a hardcoded sculpture
        Rectangle background = new Rectangle(0,0,400,400);
        background.setFill(Color.WHITE);
        
        // Sculpture components
        Rectangle source = new Rectangle(10,10,30,30);
        source.setStroke(Color.BLACK);
        source.setStrokeWidth(2);
        source.setFill(Color.WHITE);

        Rectangle thing1 = new Rectangle(60,20,30,30);
        thing1.setStroke(Color.BLUE);
        thing1.setStrokeWidth(2);
        thing1.setFill(Color.WHITE);

        Rectangle thing2 = new Rectangle(70,80,30,30);
        thing2.setStroke(Color.RED);
        thing2.setStrokeWidth(2);
        thing2.setFill(Color.WHITE);

        Rectangle sink = new Rectangle(140,100,30,30);
        sink.setStroke(Color.ORANGE);
        sink.setStrokeWidth(2);
        sink.setFill(Color.WHITE);

        //============== Connections between sculpture components
        source_to_thing1 = new Line(40,25,60,35);
        source_to_thing2 = new Line(40,25,70,95);
        thing1_to_sink = new Line(90,35,140,115);
        thing2_to_sink = new Line(100,95,140,115);
        
 
        // Putting it all together into a group node.
        root = new Group();
        root.getChildren().add(background);
        root.getChildren().add(source);
        root.getChildren().add(thing1);
        root.getChildren().add(thing2);
        root.getChildren().add(sink);
        root.getChildren().add(source_to_thing1);
        root.getChildren().add(source_to_thing2);
        root.getChildren().add(thing1_to_sink);
        root.getChildren().add(thing2_to_sink);
        
        // Connect the group into the scene and show the window.
        primaryStage.setTitle("PA9: Some hardcoded phases");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        initInOutLists();
        playSculpture();
    }
    
    
    private int demo_step = 0;
    // Method that will cause all of the sculpture components to process
    // their inputs and set up path transitions for all of their output edges.
    private void playSculpture() {
        PauseTransition wait = new PauseTransition(Duration.seconds(1));
        wait.setOnFinished((ActionEvent e) -> {
            switch(demo_step) {
            case 0:
                process0();
                demo_step++;
                wait.playFromStart();
                break;
            case 1:
                edgeTransition0();
                demo_step++;
                wait.playFromStart();
                break;
            case 2:
                process1();
                demo_step++;
                wait.playFromStart();
                break;
            case 3:
                edgeTransition1();
                demo_step++;
                wait.playFromStart();
                break;
            case 4:
                process2();
                demo_step++;
                wait.playFromStart();
                break;
            case 5:
                edgeTransition2();
                demo_step++;
                wait.playFromStart();
                break;
            case 6:
                process3();
                demo_step++;
                wait.playFromStart();
                break;
            default:
                System.out.println("Nothing more to process");
                wait.stop();
            }
        });

        // Now that the PauseTransition thread is setup, get it going.
        wait.play();
    }
    
    private List<Circle> source_input;
    private List<Circle> source_output;
    private List<Circle> thing1_input;
    private List<Circle> thing2_input;
    private List<Circle> thing1_output;
    private List<Circle> thing2_output;
    private List<Circle> sink_input;
    
    private void initInOutLists() {
        // Assume the input to the start component is a list with [RED,BLUE].
        // For PA10 will need to be read from the input file.
        source_input = new ArrayList<>();
        Circle circle = new Circle();
        circle.setFill(Color.RED);
        circle.setRadius(10);
        source_input.add(circle);
        circle = new Circle();
        circle.setFill(Color.BLUE);
        circle.setRadius(10);
        source_input.add(circle);
            
        // All the other input and output lists will just be empty.
        // TODO: these list should probably be part of some kind
        // of component class.
        source_output = new ArrayList<>();
        thing1_input = new ArrayList<>();
        thing2_input = new ArrayList<>();
        thing1_output = new ArrayList<>();
        thing2_output = new ArrayList<>();
        sink_input = new ArrayList<>();
    }
    
    private void process0() {
        // In the zeroth processing step, the only component that will
        // need to process anything is the start component.
        
        // Start component processing.
        // Take the next color off the input list and create a circle with
        // that color the start component output.
        Circle source_circle = source_input.remove(0);
        source_output.add(source_circle);
        root.getChildren().remove(source_circle);
    }
    
    private void edgeTransition0() {
        // for each edge, make a clone of the circle on the associated
        // component output and then transition it to the target component input
            
        // After process0() only the start component has any circles on its 
        // output.  It has two edges coming out of it.
        
        // first edge
        Circle source_to_thing1_circle = circleClone(source_output.get(0));
        thing1_input.add(source_to_thing1_circle);
        root.getChildren().add(source_to_thing1_circle);
        PathTransition trans1 = new PathTransition(Duration.seconds(1),
                source_to_thing1, source_to_thing1_circle);
        trans1.play();
        
        // second edge
        Circle source_to_thing2_circle = circleClone(source_output.get(0));
        thing2_input.add(source_to_thing2_circle);
        root.getChildren().add(source_to_thing2_circle);
        PathTransition trans2 = new PathTransition(Duration.seconds(1),
                source_to_thing2, source_to_thing2_circle);
        trans2.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        source_output.remove(0);
    }

    private void process1() {

        // In the next processing step, the start component will have a BLUE
        // in the input list to process and the thing1 and thing 
        // 2 components
        // will each have a circle at their input.
        
        // ==== Processing the start component
        // Take the next color off the input list and create a circle with
        // that color the start component output.
        Circle source_circle = source_input.remove(0);
        source_output.add(source_circle);
        root.getChildren().remove(source_circle);
        
        // ==== Processing thing1
        // Copy input reference to output reference and then
        // remove circle from the graphical root's list of children.
        Circle thing1_circle = thing1_input.remove(0);
        thing1_output.add(thing1_circle);
        root.getChildren().remove(thing1_circle);
       
        // ==== Processing thing2
        // Copy input reference to output reference and then
        // remove circle from the graphical root's list of children.
        Circle thing2_circle = thing2_input.remove(0);
        thing2_output.add(thing2_circle);
        root.getChildren().remove(thing2_circle);
    }

    private void edgeTransition1() {
        // After process1() there is output for the source component and
        // thing1 and thing2
    
        // first edge out of source
        Circle source_to_thing1_circle = circleClone(source_output.get(0));
        thing1_input.add(source_to_thing1_circle);
        root.getChildren().add(source_to_thing1_circle);
        PathTransition trans1 = new PathTransition(Duration.seconds(1),
                source_to_thing1, source_to_thing1_circle);
        trans1.play();
    
            // second edge out of source
        Circle source_to_thing2_circle = circleClone(source_output.get(0));
        thing2_input.add(source_to_thing2_circle);
        root.getChildren().add(source_to_thing2_circle);
        PathTransition trans2 = new PathTransition(Duration.seconds(1),
                source_to_thing2, source_to_thing2_circle);
        trans2.play();
            
            // edge out of thing1
        Circle thing1_to_sink_circle = circleClone(thing1_output.get(0));
        sink_input.add(thing1_to_sink_circle);
        root.getChildren().add(thing1_to_sink_circle);
        PathTransition trans3 = new PathTransition(Duration.seconds(1),
                thing1_to_sink, thing1_to_sink_circle);
        trans3.play();

        // edge out of thing2            
        Circle thing2_to_sink_circle = circleClone(thing1_output.get(0));
        sink_input.add(thing2_to_sink_circle);
        root.getChildren().add(thing2_to_sink_circle);
        PathTransition trans4 = new PathTransition(Duration.seconds(1),
                thing2_to_sink, thing2_to_sink_circle);
        trans4.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        source_output.remove(0);
        thing1_output.remove(0);
        thing2_output.remove(0);
    }
    
    private void process2() {

        // In the next processing step, the start component is out of inputs,
            // thing1 and thing2 should have a BLUE circle for an input, and
            // the sink component has a couple of inputs and just processes
            // all of them at once.
        
        // ==== Processing thing1
        // Copy input reference to output reference and then
        // remove circle from the graphical root's list of children.
        Circle thing1_circle = thing1_input.remove(0);
        thing1_output.add(thing1_circle);
        root.getChildren().remove(thing1_circle);
       
        // ==== Processing thing2
        // Copy input reference to output reference and then
        // remove circle from the graphical root's list of children.
        Circle thing2_circle = thing2_input.remove(0);
        thing2_output.add(thing2_circle);
        root.getChildren().remove(thing2_circle);
        
        // ==== Processing the sink component
        // Take the circles off the input list, print them out,
        // and remove them from the graphical interface.
        for (Circle sink_circle : sink_input) {
            System.out.println("sink output = "+sink_circle);
        }
        root.getChildren().removeAll(sink_input);
        sink_input.clear();
    }

    private void edgeTransition2() {
        // After process2() there is output for thing1 and thing2.
                
            // edge out of thing1
        Circle thing1_to_sink_circle = circleClone(thing1_output.get(0));
        sink_input.add(thing1_to_sink_circle);
        root.getChildren().add(thing1_to_sink_circle);
        PathTransition trans3 = new PathTransition(Duration.seconds(1),
                thing1_to_sink, thing1_to_sink_circle);
        trans3.play();

        // edge out of thing2            
        Circle thing2_to_sink_circle = circleClone(thing1_output.get(0));
        sink_input.add(thing2_to_sink_circle);
        root.getChildren().add(thing2_to_sink_circle);
        PathTransition trans4 = new PathTransition(Duration.seconds(1),
                thing2_to_sink, thing2_to_sink_circle);
        trans4.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        thing1_output.remove(0);
        thing2_output.remove(0);
    }
    
    private void process3() {

        // In the next processing step, only the sink has input.
        
        // ==== Processing the sink component
        // Take the circles off the input list, print them out,
        // and remove them from the graphical interface.
        for (Circle sink_circle : sink_input) {
            System.out.println("sink output = "+sink_circle);
        }
        root.getChildren().removeAll(sink_input);
        sink_input.clear();
    }
    
    private Circle circleClone(Circle toClone) {
        Circle clone = new Circle();
        clone.setFill(toClone.getFill());
        clone.setRadius(toClone.getRadius());
        return clone;
    }
}