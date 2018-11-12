import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
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

public class PA10hardCodedDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Line input_to_thing1, input_to_thing2, thing1_to_sink, thing2_to_sink;
    Group root;
    
    @Override
    public void start(Stage primaryStage) {

        // ===== set up the scene with a hardcoded sculpture
        Rectangle background = new Rectangle(0,0,400,400);
        background.setFill(Color.WHITE);
        
        // Sculpture nodes
        int width = 30;
        int height = 40;

        int inputX = 12;
        int inputY = 12;
        Rectangle input = new Rectangle(inputX, inputY, width, height);
        input.setStroke(Color.BLACK);
        input.setStrokeWidth(2);
        input.setFill(Color.WHITE);

        int thing1X = 70;
        int thing1Y = 25;
        Rectangle thing1 = new Rectangle(thing1X, thing1Y, width, height);
        thing1.setStroke(Color.BLACK);
        thing1.setStrokeWidth(2);
        thing1.setFill(Color.WHITE);

        int thing2X = 60;
        int thing2Y = 200;
        Rectangle thing2 = new Rectangle(thing2X, thing2Y, width, height);
        thing2.setStroke(Color.BLACK);
        thing2.setStrokeWidth(2);
        thing2.setFill(Color.WHITE);

        int sinkX = 200;
        int sinkY = 150;
        Rectangle sink = new Rectangle(sinkX, sinkY, width, height);
        sink.setStroke(Color.BLACK);
        sink.setStrokeWidth(2);
        sink.setFill(Color.WHITE);

        // ============== Connections between sculpture nodes
        input_to_thing1 = new Line(inputX + width, inputY + height / 2, thing1X,
                thing1Y + height / 2);
        input_to_thing2 = new Line(inputX + width, inputY + height / 2, thing2X,
                thing2Y + height / 2);
        thing1_to_sink = new Line(thing1X + width, thing1Y + height / 2, sinkX,
                sinkY + height / 2);
        thing2_to_sink = new Line(thing2X + width, thing2Y + height / 2, sinkX,
                sinkY + height / 2);
        
 
        // Putting it all together into a group node.
        root = new Group();
        root.getChildren().add(background);
        root.getChildren().add(input);
        root.getChildren().add(thing1);
        root.getChildren().add(thing2);
        root.getChildren().add(sink);
        root.getChildren().add(input_to_thing1);
        root.getChildren().add(input_to_thing2);
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

    // Method that will cause all of the sculpture nodes to process
    // their inputs and set up path transitions for all of their output edges.
    private void playSculpture() {
        // ----- Set up a timeline with an event that processes sculpture
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        final KeyFrame kf = new KeyFrame(Duration.seconds(1.5),
                (ActionEvent e) -> {
            switch(demo_step) {
            case 0:
                process0();
                demo_step++;
                break;
            case 1:
                edgeTransition0();
                demo_step++;
                break;
            case 2:
                process1();
                demo_step++;
                break;
            case 3:
                edgeTransition1();
                demo_step++;
                break;
            case 4:
                process2();
                demo_step++;
                break;
            case 5:
                edgeTransition2();
                demo_step++;
                break;
            case 6:
                process3();
                demo_step++;
                break;
            default:
                System.out.println("Nothing more to process");
            }
        });

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    private List<Circle> input_input;
    private List<Circle> input_output;
    private List<Circle> thing1_input;
    private List<Circle> thing2_input;
    private List<Circle> thing1_output;
    private List<Circle> thing2_output;
    private List<Circle> sink_input;
    
    private void initInOutLists() {
        // Assume the input to the start node is a list with [RED,BLUE].
        // For PA10 will need to be read from the input file.
        input_input = new ArrayList<>();
        Circle marble = new Circle();
        marble.setFill(Color.RED);
        marble.setRadius(10);
        input_input.add(marble);
        marble = new Circle();
        marble.setFill(Color.BLUE);
        marble.setRadius(10);
        input_input.add(marble);
            
        // All the other input and output lists will just be empty.
        // TODO: these list should probably be part of some kind
        // of sculpture node class.
        input_output = new ArrayList<>();
        thing1_input = new ArrayList<>();
        thing2_input = new ArrayList<>();
        thing1_output = new ArrayList<>();
        thing2_output = new ArrayList<>();
        sink_input = new ArrayList<>();
    }
    
    private void process0() {
        // In the zeroth processing step, the only node that will
        // need to process anything is the input node.
        
        // Start sculpture node processing.
        // Take the next color off the input list and create a marble with
        // that color the input node output.
        Circle input_marble = input_input.remove(0);
        input_output.add(input_marble);
        root.getChildren().remove(input_marble);
    }
    
    private void edgeTransition0() {
        // for each edge, make a clone of the marble on the associated
        // sculpture node output and then transition it to the target node input
            
        // After process0() only the start node has any marbles on its
        // output.  It has two edges coming out of it.
        
        // first edge
        Circle input_to_thing1_marble = marbleClone(input_output.get(0));
        thing1_input.add(input_to_thing1_marble);
        root.getChildren().add(input_to_thing1_marble);
        PathTransition trans1 = new PathTransition(Duration.seconds(1),
                input_to_thing1, input_to_thing1_marble);
        trans1.play();
        
        // second edge
        Circle input_to_thing2_marble = marbleClone(input_output.get(0));
        thing2_input.add(input_to_thing2_marble);
        root.getChildren().add(input_to_thing2_marble);
        PathTransition trans2 = new PathTransition(Duration.seconds(1),
                input_to_thing2, input_to_thing2_marble);
        trans2.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        input_output.remove(0);
    }

    private void process1() {

        // In the next processing step, the input node will have a BLUE
        // in the input list to process and the thing1 and thing 
        // 2 nodes
        // will each have a marble at their input.
        
        // ==== Processing the input sculpture node
        // Take the next color off the input list and create a marble with
        // that color the input node output.
        Circle input_marble = input_input.remove(0);
        input_output.add(input_marble);
        root.getChildren().remove(input_marble);
        
        // ==== Processing thing1
        // Copy input reference to output reference and then
        // remove marble from the graphical root's list of children.
        Circle thing1_marble = thing1_input.remove(0);
        thing1_output.add(thing1_marble);
        root.getChildren().remove(thing1_marble);
       
        // ==== Processing thing2
        // Copy input reference to output reference and then
        // remove marble from the graphical root's list of children.
        Circle thing2_marble = thing2_input.remove(0);
        thing2_output.add(thing2_marble);
        root.getChildren().remove(thing2_marble);
    }

    private void edgeTransition1() {
        // After process1() there is output for the input node and
        // thing1 and thing2
    
        // first edge out of input
        Circle input_to_thing1_marble = marbleClone(input_output.get(0));
        thing1_input.add(input_to_thing1_marble);
        root.getChildren().add(input_to_thing1_marble);
        PathTransition trans1 = new PathTransition(Duration.seconds(1),
                input_to_thing1, input_to_thing1_marble);
        trans1.play();
    
        // second edge out of input
        Circle input_to_thing2_marble = marbleClone(input_output.get(0));
        thing2_input.add(input_to_thing2_marble);
        root.getChildren().add(input_to_thing2_marble);
        PathTransition trans2 = new PathTransition(Duration.seconds(1),
                input_to_thing2, input_to_thing2_marble);
        trans2.play();
            
            // edge out of thing1
        Circle thing1_to_sink_marble = marbleClone(thing1_output.get(0));
        sink_input.add(thing1_to_sink_marble);
        root.getChildren().add(thing1_to_sink_marble);
        PathTransition trans3 = new PathTransition(Duration.seconds(1),
                thing1_to_sink, thing1_to_sink_marble);
        trans3.play();

        // edge out of thing2            
        Circle thing2_to_sink_marble = marbleClone(thing1_output.get(0));
        sink_input.add(thing2_to_sink_marble);
        root.getChildren().add(thing2_to_sink_marble);
        PathTransition trans4 = new PathTransition(Duration.seconds(1),
                thing2_to_sink, thing2_to_sink_marble);
        trans4.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        input_output.remove(0);
        thing1_output.remove(0);
        thing2_output.remove(0);
    }
    
    private void process2() {

        // In the next processing step, the start node is out of inputs,
        // thing1 and thing2 should have a BLUE marble for an input, and
        // the sink node has a couple of inputs and just processes
        // all of them at once.
        
        // ==== Processing thing1
        // Copy input reference to output reference and then
        // remove marble from the graphical root's list of children.
        Circle thing1_marble = thing1_input.remove(0);
        thing1_output.add(thing1_marble);
        root.getChildren().remove(thing1_marble);
       
        // ==== Processing thing2
        // Copy input reference to output reference and then
        // remove marble from the graphical root's list of children.
        Circle thing2_marble = thing2_input.remove(0);
        thing2_output.add(thing2_marble);
        root.getChildren().remove(thing2_marble);
        
        // ==== Processing the sink node
        // Take the marbles off the input list, print them out,
        // and remove them from the graphical interface.
        for (Circle sink_marble : sink_input) {
            System.out.println("sink output = " + sink_marble);
        }
        root.getChildren().removeAll(sink_input);
        sink_input.clear();
    }

    private void edgeTransition2() {
        // After process2() there is output for thing1 and thing2.
                
            // edge out of thing1
        Circle thing1_to_sink_marble = marbleClone(thing1_output.get(0));
        sink_input.add(thing1_to_sink_marble);
        root.getChildren().add(thing1_to_sink_marble);
        PathTransition trans3 = new PathTransition(Duration.seconds(1),
                thing1_to_sink, thing1_to_sink_marble);
        trans3.play();

        // edge out of thing2            
        Circle thing2_to_sink_marble = marbleClone(thing1_output.get(0));
        sink_input.add(thing2_to_sink_marble);
        root.getChildren().add(thing2_to_sink_marble);
        PathTransition trans4 = new PathTransition(Duration.seconds(1),
                thing2_to_sink, thing2_to_sink_marble);
        trans4.play();
        
        // ===== AFTER all the transitions have happened, all of the
        // output queues with elements in them need their first item removed.
        thing1_output.remove(0);
        thing2_output.remove(0);
    }
    
    private void process3() {

        // In the next processing step, only the sink has input.
        
        // ==== Processing the sink sculpture node
        // Take the marbles off the input list, print them out,
        // and remove them from the graphical interface.
        for (Circle sink_marble : sink_input) {
            System.out.println("sink output = " + sink_marble);
        }
        root.getChildren().removeAll(sink_input);
        sink_input.clear();
    }
    
    private Circle marbleClone(Circle toClone) {
        Circle clone = new Circle();
        clone.setFill(toClone.getFill());
        clone.setRadius(toClone.getRadius());
        return clone;
    }
}