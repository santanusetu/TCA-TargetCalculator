package com.tca.tcatargetcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final double DECIMAL_TO_BALLS = 1.667;

    Button showButton;
    EditText firstInningsScore, oversEntered;
    LinearLayout teamChasing, teamDefending;

    TextView win20Chase, win18Chase, win17Chase,
            lose20Chase, lose18Chase, lose17Chase,
            win20defend, win18defend, win17defend,
            lose20defend, lose18defend, lose17defend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDisplay();

        //Show equation button clicked
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = firstInningsScore.getText().toString();

                if (str.equalsIgnoreCase("")) {
                    firstInningsScore.setError("Enter score");
                } else {
                    int targetScore = Integer.parseInt(firstInningsScore.getText().toString());
                    int overs = Integer.parseInt(oversEntered.getText().toString());
                    double team1nrr = (double) targetScore / overs;

                    //Chasing equation display section
                    teamChasing.setVisibility(View.VISIBLE);
                    setChasingEquationData((targetScore + 1), team1nrr, overs);

                    //Defending equation display section
                    teamDefending.setVisibility(View.VISIBLE);
                    setDefendingEquationData((targetScore + 1), team1nrr, overs);
                }
            }
        });

    }

    //Method to setup the UI for the screen
    private void setUpDisplay() {
        showButton = (Button) findViewById(R.id.btShow);
        oversEntered = (EditText) findViewById(R.id.etOversField);
        firstInningsScore = (EditText) findViewById(R.id.etFirstInningScore);
        teamChasing = (LinearLayout) findViewById(R.id.llTeamChasing);
        teamDefending = (LinearLayout) findViewById(R.id.llTeamDefending);


        win20Chase = (TextView) findViewById(R.id.tv20ChaseWinMsg);
        win18Chase = (TextView) findViewById(R.id.tv18ChaseWinMsg);
        win17Chase = (TextView) findViewById(R.id.tv17ChaseWinMsg);

        lose20Chase = (TextView) findViewById(R.id.tv20ChaseLoseMsg);
        lose18Chase = (TextView) findViewById(R.id.tv18ChaseLoseMsg);
        lose17Chase = (TextView) findViewById(R.id.tv17ChaseLoseMsg);

        win20defend = (TextView) findViewById(R.id.tv20DefendWinMsg);
        win18defend = (TextView) findViewById(R.id.tv18DefendWinMsg);
        win17defend = (TextView) findViewById(R.id.tv17DefendWinMsg);

        lose20defend = (TextView) findViewById(R.id.tv20DefendLoseMsg);
        lose18defend = (TextView) findViewById(R.id.tv18DefendLoseMsg);
        lose17defend = (TextView) findViewById(R.id.tv17DefendLoseMsg);
    }


    //Function to display equation while Chasing
    private void setChasingEquationData(int targetScore, double team1nrr, int overs) {

        Log.v("NRR", " targetScore -> " + targetScore + " team1nrr " + team1nrr);

        win20Chase.setText(Html.fromHtml("Score " + "<b>" + (targetScore) + "</b>" + " or more runs in " + "<b><font color=\"#009900\">" + getProperOvers((targetScore / (team1nrr + 3)), true) + "</font></b>" + " or less overs"));
        win18Chase.setText(Html.fromHtml("Score " + "<b>" + (targetScore) + "</b>" + " or more runs in " + "<b><font color=\"#009900\">" + getProperOvers((targetScore / (team1nrr + 2)), true) + "</font></b>" + " or less overs"));
        win17Chase.setText(Html.fromHtml("Score " + "<b>" + (targetScore) + "</b>" + " or more runs in " + "<b><font color=\"#009900\">" + getProperOvers((targetScore / (team1nrr + 1)), true) + "</font></b>" + " or less overs"));

        int chLoss4 = (int) Math.ceil(overs * (team1nrr - 0.25));
        int chLoss2 = (int) Math.ceil(overs * (team1nrr - 0.5));
        int chLoss1 = (int) Math.ceil(overs * (team1nrr - 0.75));

        if (chLoss4 > 0)
            lose20Chase.setText(Html.fromHtml("Score " + "<b><font color=\"#ff0000\">" + (chLoss4) + "</font></b>" + " or more  runs "));
        else
            lose20Chase.setText("You have already got 4 bonus points");

        if (chLoss2 > 0)
            lose18Chase.setText(Html.fromHtml("Score " + "<b><font color=\"#ff0000\">" + (chLoss2) + "-" + (chLoss4 - 1) + "</font></b>" + " runs "));
        else
            lose18Chase.setText("You have already got 2 bonus points");

        if (chLoss1 > 0)
            lose17Chase.setText(Html.fromHtml("Score " + "<b><font color=\"#ff0000\">" + (chLoss1) + "-" + (chLoss2 - 1) + "</font></b>" + " runs "));
        else
            lose17Chase.setText("You have already got 1 bonus point");
    }


    //Function to display equation while Defending
    private void setDefendingEquationData(int targetScore, double team1nrr, int overs) {

        int defWin20 = (targetScore - 1) - (overs * 3);
        int defWin18 = (targetScore - 1) - (overs * 2);
        int defWin17 = (targetScore - 1) - (overs);


        if (defWin20 >= 0) {
            win20defend.setText(Html.fromHtml("Restrict the opposition below " + "<b><font color=\"#009900\">" + defWin20 + "</font></b>" + " runs"));
        } else {
            win20defend.setText("Score is too less to get 20 points");
        }

        if (defWin18 >= 0) {
            win18defend.setText(Html.fromHtml("Restrict the opposition below " + "<b><font color=\"#009900\">" + defWin18 + "</font></b>" + " runs"));
        } else {
            win18defend.setText("Score is too less to get 18 points");
        }

        if (defWin17 >= 0) {
            win17defend.setText(Html.fromHtml("Restrict the opposition below " + "<b><font color=\"#009900\">" + defWin17 + "</font></b>" + " runs"));
        } else {
            win17defend.setText("Score is too less to get 17 points");
        }

        lose20defend.setText(Html.fromHtml("Have the opposition bat for " + "<b><font color=\"#ff0000\">" + getProperOvers(((targetScore) / (team1nrr + 0.25)), false) + "</font></b>" + " or more overs"));
        lose18defend.setText(Html.fromHtml("Have the opposition bat for " + "<b><font color=\"#ff0000\">" + getProperOvers(((targetScore) / (team1nrr + 0.5)), false) + "</font></b>" + " or more overs"));
        lose17defend.setText(Html.fromHtml("Have the opposition bat for " + "<b><font color=\"#ff0000\">" + getProperOvers(((targetScore) / (team1nrr + 0.75)), false) + "</font></b>" + " or more overs"));
    }


    private String getProperOvers(double v, boolean isChasing) {
        Log.v("NRR", " target over -> " + v + " isChasing " + isChasing);
        int fullOvers = (int) v;

        Log.v("NRR", " fullOvers -> " + fullOvers);
        double remainingBalls = v - fullOvers;
        Log.v("NRR", " remainingBalls 1 -> " + remainingBalls);
        remainingBalls = remainingBalls / (DECIMAL_TO_BALLS);
        Log.v("NRR", " remainingBalls 2 -> " + remainingBalls);

        if (isChasing) {
            remainingBalls = Math.floor(remainingBalls * 10);
        } else {
            remainingBalls = Math.ceil(remainingBalls * 10);
        }
        Log.v("NRR", " remainingBalls 3 -> " + remainingBalls);

        int remBalls = (int) remainingBalls;

        String res;

        if (remBalls == 6) {
            fullOvers = fullOvers + 1;
            res = "" + fullOvers;
        } else if (remBalls == 0) {
            res = "" + fullOvers;
        } else {
            res = fullOvers + "." + remBalls;
        }

        Log.v("NRR", " res -> " + res);
        return res;
    }

}
