package com.example.android.quizfinal;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    //GLOBAL VARIABLE
    int numberOfCorrectAnswers = 0;

    //Questions and Answers Views Initialized as Global (and then assinged in onCreate)
    CheckBox q1answer1;
    CheckBox q1answer2;
    CheckBox q1answer3;
    CheckBox q1answer4;
    Button q1myButton;
    //Question 2
    EditText q2a;
    String answerForQ2;
    Button q2myButton;
    //Question 3
    RadioGroup question3Group;
    int q3selectedId;
    RadioButton q3answer;
    Button q3myButton;
    //Question 4
    CheckBox q4answer1;
    CheckBox q4answer2;
    CheckBox q4answer3;
    CheckBox q4answer4;
    Button q4myButton;
    //Question 5
    EditText q5a;
    String answerForQ5;
    Button q5myButton;
    //Question 6
    RadioGroup question6Group;
    int q6selectedId;
    RadioButton q6answer;
    Button q6myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ensure user keyboard enables only when editbox is tapped
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize views
        //Question 1
        q1answer1 = (CheckBox) findViewById(R.id.q1a);
        q1answer2 = (CheckBox) findViewById(R.id.q1b);
        q1answer3 = (CheckBox) findViewById(R.id.q1c);
        q1answer4 = (CheckBox) findViewById(R.id.q1d);
        q1myButton = (Button) findViewById(R.id.submitAnswersButton1);
        //Question 2
        q2a = (EditText) findViewById(R.id.q2a);
        answerForQ2 = getString(R.string.Q2answer);
        q2myButton = (Button) findViewById(R.id.submitAnswersButton2);
        //Question 3
         question3Group = (RadioGroup) findViewById(R.id.q3group);

         q3answer = (RadioButton) findViewById(R.id.q3b);
         q3myButton = (Button) findViewById(R.id.submitAnswersButton3);
        //Question 4
         q4answer1 = (CheckBox) findViewById(R.id.q4a);
         q4answer2 = (CheckBox) findViewById(R.id.q4b);
         q4answer3 = (CheckBox) findViewById(R.id.q4c);
         q4answer4 = (CheckBox) findViewById(R.id.q4d);
         q4myButton = (Button) findViewById(R.id.submitAnswersButton4);
        //Question 5
         q5a = (EditText) findViewById(R.id.q5a);
         answerForQ5 = getString(R.string.Q5answer);
         q5myButton = (Button) findViewById(R.id.submitAnswersButton5);
        //Question 6
         question6Group = (RadioGroup) findViewById(R.id.q6group);

         q6answer = (RadioButton) findViewById(R.id.q6b);
         q6myButton = (Button) findViewById(R.id.submitAnswersButton6);

    }

    //Saving Android State for numberOfCorrectAnswers when screen is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numberOfCorrectAnswers", numberOfCorrectAnswers);
    }
    //Saving Android State for numberOfCorrectAnswers when screen is rotated
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        numberOfCorrectAnswers = savedInstanceState.getInt("numberOfCorrectAnswers");
    }

    /**
     * Functions called by check answer buttons and calling universal answer check functions while passing the question specific global views
     * @param view of the current View in the program
     */
    public void submitAnswer1(View view) {
        checkAnswersCheckboxes(q1answer1, q1answer2, q1answer3, q1answer4, q1myButton);
    }

    public void submitAnswer2(View view) {
        checkAnswersEditBoxes(q2a, answerForQ2, q2myButton);
    }

    public void submitAnswer3(View view) {
        q3selectedId = question3Group.getCheckedRadioButtonId();
        checkAnswersRadio(q3selectedId, q3answer, q3myButton);
    }

    public void submitAnswer4(View view) {
         checkAnswersCheckboxes(q4answer1, q4answer2, q4answer3, q4answer4, q4myButton);
    }

    public void submitAnswer5(View view) {
        checkAnswersEditBoxes(q5a, answerForQ5, q5myButton);
    }

    public void submitAnswer6(View view) {
        q6selectedId = question6Group.getCheckedRadioButtonId();
        checkAnswersRadio(q6selectedId, q6answer, q6myButton);
    }


    /**
     * Check for answers for a question, then checks if correct and output correctness message and if correct, adds to score
     *
     * @param answer1, answer2,  answer3, answer4 are all checkboxes from the question
     *                 myButton is the button from the question to disable when the answer is correct
     * @returns none; Toast outputs on screen
     */
    private void checkAnswersCheckboxes(CheckBox answer1, CheckBox answer2, CheckBox answer3, CheckBox answer4, Button myButton) {

        if (!answer1.isChecked() && !answer2.isChecked() && !answer3.isChecked() && !answer4.isChecked()) {
            Toast.makeText(getBaseContext(), "Please Select an Answer",
                    Toast.LENGTH_LONG).show();
        } else if (answer1.isChecked() && answer2.isChecked() && answer3.isChecked() && !answer4.isChecked()) {
            Toast.makeText(getBaseContext(), "Correct!",
                    Toast.LENGTH_LONG).show();
            numberOfCorrectAnswers++;
            myButton.setEnabled(false);
        } else {
            Toast.makeText(getBaseContext(), "Please Try Again",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Check for answers for a question, then checks if correct and output correctness message and if correct, adds to score
     *
     * @param userAnswer which is the input and key, which is the answer stored in Strings.xml
     *                   myButton is the button from the question to disable when the answer is correct
     * @returns none; Toast outputs on screen
     */
    private void checkAnswersEditBoxes(EditText userAnswer, String key, Button myButton) {

        String userAnswerString = userAnswer.getText().toString().toUpperCase().trim();

        if (userAnswerString == "") {
            Toast.makeText(getBaseContext(), "Please Select an Answer",
                    Toast.LENGTH_LONG).show();
        } else if (userAnswerString.equals(key)) {
            Toast.makeText(getBaseContext(), "Correct!",
                    Toast.LENGTH_LONG).show();
            numberOfCorrectAnswers++;
            myButton.setEnabled(false);
        } else {
            Toast.makeText(getBaseContext(), "Please Try Again",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Check for answers for a question, then checks if correct and output correctness message and if correct, adds to score
     *
     * @param selectedId as the user input, the answer key, and...
     *                   myButton is the button from the question to disable when the answer is correct
     * @returns none; Toast outputs on screen
     */
    private void checkAnswersRadio(int selectedId, RadioButton answer, Button myButton) {

        if (selectedId == -1) {
            Toast.makeText(getBaseContext(), "Please Select an Answer",
                    Toast.LENGTH_LONG).show();
        } else if (selectedId == answer.getId()) {
            Toast.makeText(getBaseContext(), "Correct!",
                    Toast.LENGTH_LONG).show();
            numberOfCorrectAnswers++;
            myButton.setEnabled(false);
        } else {
            Toast.makeText(getBaseContext(), "Please Try Again",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Composes an email based on the scores earned
     *
     * @param   subject, body
     * @returns none; email composed
     */
    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Composes an email based on the email provided
     *
     * @param view
     * @returns none; email composed
     */
    public void submitAnswersAndShare(View view) {
        String mySubject = "My A&B Fan Quiz Score: " + numberOfCorrectAnswers + "/6";

        composeEmail(mySubject, "I've scored "
                + numberOfCorrectAnswers
                + " out of 6 on the Above and Beyond Fan Quiz!");
    }
}
