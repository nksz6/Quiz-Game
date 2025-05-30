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
    int correctGuesses = 0; //correct guesses
    int totalQuestions = questions.length; //will dynamically change with number of questions in array
    int result; //integer to hold result
    int seconds = 10; //will give a 10 second count down to guess answer

    //custom fonts
    String fontHM = "HonMincho";
    String fontD = "Dialog";


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
    JLabel answerLabelA = new JLabel(); //answer A
    JLabel answerLabelB = new JLabel(); //answer B
    JLabel answerLabelC = new JLabel(); //answer C
    JLabel answerLabelD = new JLabel(); //answer D
    //Labels for the timer
    JLabel timeLabel = new JLabel(); //display word timer or something
    JLabel secondsLeft = new JLabel(); //function as a display for the count down timer
    //Appear after calculation of results
    JTextField numberRight = new JTextField(); //display the number of questions you've gotten right
    JTextField percentage = new JTextField(); //display what you got for a percentage on your score

    //By not declaring the above variables in the constructor yet in the class, they essentially act as global variables.
    //So they can be accessed and changed anywhere, which we will be doing.

    //timer setup - as lambda
    Timer timer = new Timer(1000, e -> { // 'e' is the ActionEvent Parameter
        seconds--; //decrement seconds every second
        secondsLeft.setText(String.valueOf(seconds)); //display seconds remaining
        if (seconds == 0) { //when seconds run out
            displayAnswer();
        }
    });


    //constructor
    public Quiz() {

        //begin with frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //a.k.a (JFrame.EXIT_ON_CLOSE)
        frame.setSize(650,650); //decent size to start with, can change.
        frame.getContentPane().setBackground(new Color(50,50,50)); //gives the nice greyish background

        //since not using / dont want to use a layout manager
        frame.setLayout(null); //null because im gonna layout everything by hand
        frame.setResizable(false); //optional, false if we don't want folks to resize our frame window
        
        //create textfield to hold number of question where on
        textfield.setBounds(0,0, 650, 50 ); //x = 0, y = 0, 650px width, 50px height
        textfield.setBackground(new Color(25,25,25)); //background color (blackish)
        textfield.setForeground(new Color(25,255,0)); //text color (greenish)
        textfield.setFont(new Font(fontHM, Font.BOLD, 30)); //font style and such
        textfield.setBorder(BorderFactory.createBevelBorder(1)); //border style recommendation
        //textfield.setHorizontalAlignment(JTextField.CENTER); //align it center (changeable)
        textfield.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); //sonarquube telling me to fix :/
        textfield.setEditable(false); //set false because I don't want people to edit it.

        //copy textfield, set up to display the question (is a textarea now)
        textarea.setBounds(0,50, 650, 50 ); //move it under the last textfield (down by 50px)
        textarea.setLineWrap(true); //in case text goes off screen, make it wrap around (true)
        textarea.setWrapStyleWord(true); //wraps at the word boundaries according to style
        textarea.setBackground(new Color(25,25,25));
        textarea.setForeground(new Color(25,255,0));
        textarea.setFont(new Font(fontHM, Font.BOLD, 25));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        //cant align text areas
        textarea.setEditable(false); //Don't want people to edit it.

        //Button A
        buttonA.setBounds(0, 100, 100, 100);
        buttonA.setFont(new Font(fontHM, Font.BOLD, 35));
        buttonA.setFocusable(false); //make it so button aint highlighted
        buttonA.addActionListener(this);
        buttonA.setText("A"); //set Button text

        //Button B
        buttonB.setBounds(0, 200, 100, 100);
        buttonB.setFont(new Font(fontHM, Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");

        //Button C
        buttonC.setBounds(0, 300, 100, 100);
        buttonC.setFont(new Font(fontHM, Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");

        //Button D
        buttonD.setBounds(0, 400, 100, 100);
        buttonD.setFont(new Font(fontHM, Font.BOLD, 35));
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setText("D");

        //add answer Labels

        //answer_labelA
        answerLabelA.setBounds(125, 100, 500, 100);
        answerLabelA.setBackground(new Color(50, 50, 50));
        answerLabelA.setForeground(new Color(25,255,0));
        answerLabelA.setFont(new Font(fontHM, Font.PLAIN, 35));


        //answer_labelB
        answerLabelB.setBounds(125, 200, 500, 100);
        answerLabelB.setBackground(new Color(50, 50, 50));
        answerLabelB.setForeground(new Color(25,255,0));
        answerLabelB.setFont(new Font(fontHM, Font.PLAIN, 35));


        //answer_labelC
        answerLabelC.setBounds(125, 300, 500, 100);
        answerLabelC.setBackground(new Color(50, 50, 50));
        answerLabelC.setForeground(new Color(25,255,0));
        answerLabelC.setFont(new Font(fontHM, Font.PLAIN, 35));


        //answer_labelD
        answerLabelD.setBounds(125, 400, 500, 100);
        answerLabelD.setBackground(new Color(50, 50, 50));
        answerLabelD.setForeground(new Color(25,255,0));
        answerLabelD.setFont(new Font(fontHM, Font.PLAIN, 35));

        //timer for seconds
        secondsLeft.setBounds(535,510,100,100); //100x100 is a box
        secondsLeft.setBackground(new Color(25,25,25));
        secondsLeft.setForeground(new Color(255,0,0)); //red Foreground
        secondsLeft.setFont(new Font(fontD, Font.BOLD, 60));
        secondsLeft.setBorder(BorderFactory.createBevelBorder(1));
        secondsLeft.setOpaque(true);
        secondsLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        secondsLeft.setText(String.valueOf(seconds)); //need to convert to string so we can display it as seconds

        //display time label (optional, just makes the timer clearer)
        timeLabel.setBounds(535, 472, 100, 25);
        timeLabel.setBackground(new Color (50,50,50)); //matches frame color
        timeLabel.setForeground(new Color(255,0,0));
        timeLabel.setFont(new Font(fontD, Font.PLAIN, 16));
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("Timer");

        //display number correct
        //(wont be visible right away, only after)
        numberRight.setBounds(225, 225, 200, 100);
        numberRight.setBackground(new Color(25,25,25));
        numberRight.setForeground(new Color(25,255,0));
        numberRight.setFont(new Font(fontD, Font.BOLD, 50)); //will want a big size for results
        numberRight.setBorder(BorderFactory.createBevelBorder(1));
        numberRight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberRight.setEditable(false); //definitely don't want folks cheating

        //display label for number right
        percentage.setBounds(225,325, 200, 100);
        percentage.setBackground(new Color(25,25,25));
        percentage.setForeground(new Color(25,255,0));
        percentage.setFont(new Font(fontD, Font.BOLD, 50));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        percentage.setEditable(false);



        //adding components to the frame
        frame.add(timeLabel);
        frame.add(secondsLeft);
        frame.add(answerLabelA);
        frame.add(answerLabelB);
        frame.add(answerLabelC);
        frame.add(answerLabelD);
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
        
        if (index >= totalQuestions) { //if we've finished, show the results
            results();
        } else {

        textfield.setText("Question " + (index + 1)); //say what question it is (first bar)
        textarea.setText(questions[index]); //display question being asked (will start at 0 in the array) (second bar)
        answerLabelA.setText(options[index][0]);
        answerLabelB.setText(options[index][1]);
        answerLabelC.setText(options[index][2]);
        answerLabelD.setText(options[index][3]);

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
                correctGuesses++;
            }
        }

        //Button B
        if (e.getSource() == buttonB) {
            answer = 'B';
            if (answer == answers[index]) {
                correctGuesses++;
            }
        }

        //Button C
        if (e.getSource() == buttonC) {
            answer = 'C';
            if (answer == answers[index]) {
                correctGuesses++;
            }
        }

        //Button D
        if (e.getSource() == buttonD) {
            answer = 'D';
            if (answer == answers[index]) {
                correctGuesses++;
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
            answerLabelA.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'B') {
            answerLabelB.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'C') {
            answerLabelC.setForeground(new Color(255,0,0));
        }
        if(answers[index] != 'D') {
            answerLabelD.setForeground(new Color(255,0,0));
        }

        //before next question, revert the colors
        //but need to time it so its not showing red on the next question
        Timer pause = new Timer(2000, e -> {
            //change answers back to green
            answerLabelA.setForeground(new Color(25,255,0));
            answerLabelB.setForeground(new Color(25,255,0));
            answerLabelC.setForeground(new Color(25,255,0));
            answerLabelD.setForeground(new Color(25,255,0));
                
            //reset answer
            answer = ' ';
            seconds = 10;
            secondsLeft.setText(String.valueOf(seconds));
            buttonA.setEnabled(true);                buttonB.setEnabled(true);
            buttonC.setEnabled(true);
            buttonD.setEnabled(true);
            index++;
            nextQuestion();

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


        result = (int)((correctGuesses / (double)totalQuestions) * 100); //casted as integer so no decimal will be shown

        textfield.setText("Results!");

        //clearing out so nothing gets shown in these areas
        textarea.setText("");
        answerLabelA.setText("");
        answerLabelB.setText("");
        answerLabelC.setText("");
        answerLabelD.setText("");

        //show how much we got right
        numberRight.setText("(" + correctGuesses + "/" + totalQuestions + ")");
        percentage.setText(result + "%");

        //add to frame
        frame.add(numberRight);
        frame.add(percentage);
    }

}
