package com.quizgame;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


//include action listener to click to the next question

public class Quiz implements ActionListener {
    
    //1d array of questions
    String[] questions = {
                            "What is my name?",
                            "What school do I go to?",
                            "How do I like my pizza?",
                            "What is my favorite programming language?"
    };

    //2d array of options for answers (array of arrays)
    String[][] options = {
                            {"Nick Kelley", "Mark Zuckerburg", "Bill Gates", "Nik Kelly"},
                            {"U of M", "Metro State", "Harvard", "MIT"},
                            {"Pepperoni", "Sausage", "Cheese", "Hawaiian"},
                            {"JavaScript", "Kotlin", "Python", "Java"}
    };

    //1d array of characters or strings to hold all the correct answers
    char[] answers = {
                        'A',
                        'B',
                        'C',
                        'D'
    };

    char guess; //will hold our guess
    char answer; //will hold the correct answer
    int index; //index
    int correct_guesses = 0; //correct guesses
    int total_questions = questions.length; //will dynamically change with number of questions in array
    int result; //integer to hold result
    int seconds = 10; //will give a 10 second count down to guess answer


    //GUI component initialization
    JFrame frame = new JFrame(); //JFrame to hold everything
    JTextField textfield = new JTextField(); // JTextField to hold the current question we're on
    JTextArea textarea = new JTextArea(); //JTextArea to hold the current question
    //JButtons
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    //JLabels to hold all of the different answers
    JLabel answer_labelA = new JLabel(); //answer A
    JLabel answer_labelB = new JLabel(); //answer B
    JLabel answer_labelC = new JLabel(); //answer C
    JLabel answer_labelD = new JLabel(); //answer D
    //Labels for the timer
    JLabel time_label = new JLabel(); //display word timer or something
    JLabel seconds_left = new JLabel(); //function as a display for the count down timer
    //Appear after calculation of results
    JTextField number_right = new JTextField(); //display the number of questions you've gotten right
    JTextField percentage = new JTextField(); //display what you got for a percentage on your score

    //By not declaring the above variables in the constructor yet in the class, they essentially act as global variables.
    //So they can be accessed and changed anywhere, which we will be doing.

    //timer setup
    Timer timer = new Timer(1000, new ActionListener() { //have it execute each second


        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--; //decrement by 1 after each second
            seconds_left.setText(String.valueOf(seconds)); //update timer with decrement
            if (seconds == 0) { //check for timer runout
                displayAnswer(); //you'd have gotten the question wrong then
            }
        }
    });


    //constructor
    public Quiz() {

        //begin with frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,650); //decent size to start with, can change.
        frame.getContentPane().setBackground(new Color(50,50,50)); //gives the nice greyish background

        //since not using / dont want to use a layout manager
        frame.setLayout(null); //null because im gonna layout everything by hand
        frame.setResizable(false); //optional, false if we don't want folks to resize our frame window
        
        //create textfield to hold number of question where on
        textfield.setBounds(0,0, 650, 50 ); //x = 0, y = 0, 650px width, 50px height
        textfield.setBackground(new Color(25,25,25)); //background color (blackish)
        textfield.setForeground(new Color(25,255,0)); //text color (greenish)
        textfield.setFont(new Font("MV Boli", Font.BOLD, 30)); //font style and such
        textfield.setBorder(BorderFactory.createBevelBorder(1)); //border style recommendation
        textfield.setHorizontalAlignment(JTextField.CENTER); //align it center (you can change this)
        textfield.setEditable(false); //set false because I don't want people to edit it.

        //copy textfield, set up to display the question (is a textarea now)
        textarea.setBounds(0,50, 650, 50 ); //move it under the last textfield (down by 50px)
        textarea.setLineWrap(true); //in case text goes off screen, make it wrap around (true)
        textarea.setWrapStyleWord(true); //wraps at the word boundaries according to style
        textarea.setBackground(new Color(25,25,25));
        textarea.setForeground(new Color(25,255,0));
        textarea.setFont(new Font("MV Boli", Font.BOLD, 25));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        //cant align text areas
        textarea.setEditable(false); //Don't want people to edit it.

        //Button A
        buttonA.setBounds(0, 100, 100, 100);
        buttonA.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonA.setFocusable(false); //make it so button aint highlighted
        buttonA.addActionListener(this);
        buttonA.setText("A"); //set Button text

        //Button B
        buttonB.setBounds(0, 200, 100, 100);
        buttonB.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");

        //Button C
        buttonC.setBounds(0, 300, 100, 100);
        buttonC.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");

        //Button D
        buttonD.setBounds(0, 400, 100, 100);
        buttonD.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setText("D");

        //add answer Labels

        //answer_labelA
        answer_labelA.setBounds(125, 100, 500, 100);
        answer_labelA.setBackground(new Color(50, 50, 50));
        answer_labelA.setForeground(new Color(25,255,0));
        answer_labelA.setFont(new Font("MV Boli", Font.PLAIN, 35));


        //answer_labelB
        answer_labelB.setBounds(125, 200, 500, 100);
        answer_labelB.setBackground(new Color(50, 50, 50));
        answer_labelB.setForeground(new Color(25,255,0));
        answer_labelB.setFont(new Font("MV Boli", Font.PLAIN, 35));


        //answer_labelC
        answer_labelC.setBounds(125, 300, 500, 100);
        answer_labelC.setBackground(new Color(50, 50, 50));
        answer_labelC.setForeground(new Color(25,255,0));
        answer_labelC.setFont(new Font("MV Boli", Font.PLAIN, 35));


        //answer_labelD
        answer_labelD.setBounds(125, 400, 500, 100);
        answer_labelD.setBackground(new Color(50, 50, 50));
        answer_labelD.setForeground(new Color(25,255,0));
        answer_labelD.setFont(new Font("MV Boli", Font.PLAIN, 35));

        //timer for seconds
        seconds_left.setBounds(535,510,100,100); //100x100 is a box
        seconds_left.setBackground(new Color(25,25,25));
        seconds_left.setForeground(new Color(255,0,0)); //red Foreground
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 60));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds)); //need to convert to string so we can display it as seconds

        //display time label (optional, just makes the timer clearer)
        time_label.setBounds(535, 472, 100, 25);
        time_label.setBackground(new Color (50,50,50)); //matches frame color
        time_label.setForeground(new Color(255,0,0));
        time_label.setFont(new Font("MV Boli", Font.PLAIN, 16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Timer >:D");

        //display number correct
        //(wont be visible right away, only after)
        number_right.setBounds(225, 225, 200, 100);
        number_right.setBackground(new Color(25,25,25));
        number_right.setForeground(new Color(25,255,0));
        number_right.setFont(new Font("Ink Free", Font.BOLD, 50)); //will want a big size for results
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false); //definitely don't want folks cheating

        //display label for number right
        percentage.setBounds(225,325, 200, 100);
        percentage.setBackground(new Color(25,25,25));
        percentage.setForeground(new Color(25,255,0));
        percentage.setFont(new Font("Ink Free", Font.BOLD, 50));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);



        //adding components to the frame
        frame.add(time_label);
        frame.add(seconds_left);
        frame.add(answer_labelA);
        frame.add(answer_labelB);
        frame.add(answer_labelC);
        frame.add(answer_labelD);
        frame.add(buttonA);
        frame.add(buttonB);
        frame.add(buttonC);
        frame.add(buttonD);
        frame.add(textarea);
        frame.add(textfield);
        frame.setVisible(true);


        //lastly in constructor, call nextQuestion() method to begin the quiz.
        nextQuestion();

    }

    //method to move to the next question
    public void nextQuestion() {
        
        if (index >= total_questions) { //if we've finished, show the results
            results();
        } else {

        textfield.setText("Question " + (index + 1)); //say what question it is (first bar)
        textarea.setText(questions[index]); //display question being asked (will start at 0 in the array) (second bar)
        answer_labelA.setText(options[index][0]);
        answer_labelB.setText(options[index][1]);
        answer_labelC.setText(options[index][2]);
        answer_labelD.setText(options[index][3]);

        //start the timer
        timer.start();

        }
    }

    //anything related to button clicking is in this method
    @Override
    public void actionPerformed(ActionEvent e) {

        //first disable buttons
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        //if someone click button A
        if (e.getSource() == buttonA) {
            answer = 'A'; //make sure its in double quotes if I do strings.
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }

        //Button B
        if (e.getSource() == buttonB) {
            answer = 'B';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }

        //Button C
        if (e.getSource() == buttonC) {
            answer = 'C';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }

        //Button D
        if (e.getSource() == buttonD) {
            answer = 'D';
            if (answer == answers[index]) {
                correct_guesses++;
            }
        }

        //move onto next answer
        displayAnswer();

    }

    //method to display answers
    public void displayAnswer() {

        //stop the timer then once answer is being displayed
        timer.stop();

        //disable buttons again just in case
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);


        //if incorrect, change font to red
        if(answers[index] != 'A') {
            answer_labelA.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'B') {
            answer_labelB.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'C') {
            answer_labelC.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'D') {
            answer_labelD.setForeground(new Color(255,0,0));
        }

        //before next question, revert the colors
        //but need to time it so its not showing red on the next question
        Timer pause = new Timer(2000, new ActionListener() {  //named pause cause its timing it before next question

            @Override
            public void actionPerformed(ActionEvent e) {

                //change answers back to green
                answer_labelA.setForeground(new Color(25,255,0));
                answer_labelB.setForeground(new Color(25,255,0));
                answer_labelC.setForeground(new Color(25,255,0));
                answer_labelD.setForeground(new Color(25,255,0));
                
                //reset answer
                answer = ' ';
                seconds = 10;
                seconds_left.setText(String.valueOf(seconds));
                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                buttonC.setEnabled(true);
                buttonD.setEnabled(true);
                index++;
                nextQuestion();

            }
        });
        //only want timer to activate once, so call method to help:
        pause.setRepeats(false); //only executes once then
        pause.start();

    }

    //method to display final results
    public void results() {

        //first disable all buttons
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);


        result = (int)((correct_guesses / (double)total_questions) * 100); //casted as integer so no decimal will be shown

        textfield.setText("Results!");

        //clearing out so nothing gets shown in these areas
        textarea.setText("");
        answer_labelA.setText("");
        answer_labelB.setText("");
        answer_labelC.setText("");
        answer_labelD.setText("");

        //show how much we got right
        number_right.setText("(" + correct_guesses + "/" + total_questions + ")");
        percentage.setText(result + "%");

        //add to frame
        frame.add(number_right);
        frame.add(percentage);
    }

}
