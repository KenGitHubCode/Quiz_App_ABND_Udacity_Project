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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Functions called by check answer buttons and calling answer check functions
     *
     * @param view of the current View in the program
     * @returns all are void but use Toast to output messages to user
     */
    public void submitAnswer1(View view) {
        CheckBox answer1 = (CheckBox) findViewById(R.id.q1a);
        CheckBox answer2 = (CheckBox) findViewById(R.id.q1b);
        CheckBox answer3 = (CheckBox) findViewById(R.id.q1c);
        CheckBox answer4 = (CheckBox) findViewById(R.id.q1d);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton1);
        checkAnswersCheckboxes(answer1, answer2, answer3, answer4, myButton);
    }

    public void submitAnswer2(View view) {
        EditText q2a = (EditText) findViewById(R.id.q2a);
        String answerForQ2 = getString(R.string.Q2answer);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton2);
        checkAnswersEditBoxes(q2a, answerForQ2, myButton);
    }

    public void submitAnswer3(View view) {
        RadioGroup question3Group = (RadioGroup) findViewById(R.id.q3group);
        int selectedId = question3Group.getCheckedRadioButtonId();
        RadioButton answer = (RadioButton) findViewById(R.id.q3b);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton3);
        checkAnswersRadio(selectedId, answer, myButton);
    }

    public void submitAnswer4(View view) {
        CheckBox answer1 = (CheckBox) findViewById(R.id.q4a);
        CheckBox answer2 = (CheckBox) findViewById(R.id.q4b);
        CheckBox answer3 = (CheckBox) findViewById(R.id.q4c);
        CheckBox answer4 = (CheckBox) findViewById(R.id.q4d);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton4);

        checkAnswersCheckboxes(answer1, answer2, answer3, answer4, myButton);
    }

    public void submitAnswer5(View view) {
        EditText q5a = (EditText) findViewById(R.id.q5a);
        String answerForQ5 = getString(R.string.Q5answer);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton5);
        checkAnswersEditBoxes(q5a, answerForQ5, myButton);
    }

    public void submitAnswer6(View view) {
        RadioGroup question6Group = (RadioGroup) findViewById(R.id.q6group);
        int selectedId = question6Group.getCheckedRadioButtonId();
        RadioButton answer = (RadioButton) findViewById(R.id.q6b);
        Button myButton = (Button) findViewById(R.id.submitAnswersButton6);
        checkAnswersRadio(selectedId, answer, myButton);

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
