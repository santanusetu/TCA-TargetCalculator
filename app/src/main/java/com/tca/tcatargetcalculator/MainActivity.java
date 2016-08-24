package com.tca.tcatargetcalculator;

import android.content.SyncStatusObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = firstInningsScore.getText().toString();

                if (str.equalsIgnoreCase("")) {
                    firstInningsScore.setError("Enter score");//it gives user to info message //use any one //
                } else {
                    int targetScore = Integer.parseInt(firstInningsScore.getText().toString());
                    int overs = Integer.parseInt(oversEntered.getText().toString());
                    double team1nrr = (double) targetScore / overs;

                    teamChasing.setVisibility(View.VISIBLE);
                    setChasingEquationData((targetScore + 1), team1nrr);

                    teamDefending.setVisibility(View.VISIBLE);
                    setDefendingEquationData((targetScore + 1), team1nrr, overs);
                }
            }
        });

    }


    private void setChasingEquationData(int targetScore, double team1nrr) {

        Log.v("NRR", " targetScore -> " + targetScore + " team1nrr " + team1nrr);

        win20Chase.setText("Score " + (targetScore) + " or more runs in " + getProperOvers(targetScore / (team1nrr + 3)) + " or less overs");
        win18Chase.setText("Score " + (targetScore) + " or more runs in " + getProperOvers(targetScore / (team1nrr + 2)) + " or less overs");
        win17Chase.setText("Score " + (targetScore) + " or more runs in " + getProperOvers(targetScore / (team1nrr + 1)) + " or less overs");

        int chLoss4 = (int) Math.ceil(20 * (team1nrr - 0.25));
        int chLoss2 = (int) Math.ceil(20 * (team1nrr - 0.5));
        int chLoss1 = (int) Math.ceil(20 * (team1nrr - 0.75));

        lose20Chase.setText("Score " + (chLoss4) + " or more  runs ");
        lose18Chase.setText("Score " + (chLoss2) + "-" + (chLoss4 - 1) + " runs ");
        lose17Chase.setText("Score " + (chLoss1) + "-" + (chLoss2 - 1) + " runs ");
    }

    private String getProperOvers(double v) {
        Log.v("NRR", " target over -> " + v);
        int fullOvers = (int) v;

        Log.v("NRR", " fullOvers -> " + fullOvers);
        double remainingBalls = v - fullOvers;
        Log.v("NRR", " remainingBalls 1 -> " + remainingBalls);
        remainingBalls = remainingBalls / (1.667);
        Log.v("NRR", " remainingBalls 2 -> " + remainingBalls);
        remainingBalls = Math.floor(remainingBalls * 10);
        Log.v("NRR", " remainingBalls 3 -> " + remainingBalls);

        int remBalls = (int) remainingBalls;
        String res = fullOvers + "." + remBalls;
        Log.v("NRR", " res -> " + res);
        return res;
    }

    private String getProperOversDefend(double v) {
        Log.v("NRR", " target over -> " + v);
        int fullOvers = (int) v;

        Log.v("NRR", " fullOvers -> " + fullOvers);
        double remainingBalls = v - fullOvers;
        Log.v("NRR", " remainingBalls 1 -> " + remainingBalls);
        remainingBalls = remainingBalls / (1.667);
        Log.v("NRR", " remainingBalls 2 -> " + remainingBalls);
        remainingBalls = Math.ceil(remainingBalls * 10);
        Log.v("NRR", " remainingBalls 3 -> " + remainingBalls);

        int remBalls = (int) remainingBalls;
        String res = fullOvers + "." + remBalls;
        Log.v("NRR", " res -> " + res);
        return res;
    }


    private void setDefendingEquationData(int targetScore, double team1nrr, int overs) {

        int defWin20 = (targetScore - 1) - (overs * 3);
        int defWin18 = (targetScore - 1) - (overs * 2);
        int defWin17 = (targetScore - 1) - (overs * 1);

        //  int defWin20 = (int) ((team1nrr < 3) ? 0 :  Math.floor(20*(team1nrr - 3)));
        //  int defWin18 = (int) ((team1nrr < 2) ? 0 :  Math.floor(20*(team1nrr - 2)));
        // int defWin17 = (int) ((team1nrr < 1) ? 0 :  Math.floor(20*(team1nrr - 1)));

        if (defWin20 >= 0) {
            win20defend.setText("Restrict the opposition below " + defWin20 + " runs");
        } else {
            win20defend.setText("Score is too less to get 20 points");
        }

        if (defWin18 >= 0) {
            win18defend.setText("Restrict the opposition below " + defWin18 + " runs");
        } else {
            win18defend.setText("Score is too less to get 18 points");
        }

        if (defWin17 >= 0) {
            win17defend.setText("Restrict the opposition below " + defWin17 + " runs");
        } else {
            win17defend.setText("Score is too less to get 17 points");
        }


        lose20defend.setText(getProperOversDefend((targetScore)/(team1nrr+0.25)));
        lose18defend.setText(getProperOversDefend((targetScore)/(team1nrr+0.5)));
        lose17defend.setText(getProperOversDefend((targetScore)/(team1nrr+0.75)));


        //  defLoss4 = getOvers('d',(fiScore+1)/(team1Drr + 0.25));
        //  defLoss2 = getOvers('d',(fiScore+1)/(team1Drr + 0.5));
        // defLoss1 = getOvers('d',(fiScore+1)/(team1Drr + 0.75));


    }



    /*
    function calculateTarget(fiScore, overs) {
        var chWin20, chWin18, chWin17, defWin20, defWin18, defWin17, chLoss4, chLoss2, chLoss1, defLoss4, defLoss2, defLoss1;

        var team1Drr = fiScore/overs;

        chWin20 = getOvers('c',fiScore/(team1Drr + 3));
        chWin18 = getOvers('c',fiScore/(team1Drr + 2));
        chWin17 = getOvers('c',fiScore/(team1Drr + 1));

        chLoss4 = Math.ceil(20*(team1Drr - 0.25));
        chLoss2 = Math.ceil(20*(team1Drr - 0.5));
        chLoss1 = Math.ceil(20*(team1Drr - 0.75));

        defWin20 = (team1Drr < 3) ? 0 :  Math.floor(20*(team1Drr - 3));
        defWin18 = (team1Drr < 2) ? 0 :  Math.floor(20*(team1Drr - 2));
        defWin17 = (team1Drr < 1) ? 0 :  Math.floor(20*(team1Drr - 1));

        defLoss4 = getOvers('d',(fiScore+1)/(team1Drr + 0.25));
        defLoss2 = getOvers('d',(fiScore+1)/(team1Drr + 0.5));
        defLoss1 = getOvers('d',(fiScore+1)/(team1Drr + 0.75));

        $("#ch-win-20").html("Score <b>" + (fiScore+1) + "</b> or more runs in <span style='font-size:16px; color:red'>" + chWin20 + "</span> or less overs.");
        $("#ch-win-18").html("Score <b>" + (fiScore+1) + "</b> or more runs in <span style='font-size:16px; color:red'>" + chWin18 + "</span> or less overs.");
        $("#ch-win-17").html("Score <b>" + (fiScore+1) + "</b> or more runs in <span style='font-size:16px; color:red'>" + chWin17 + "</span> or less overs.");

        $("#ch-loss-4").html("Score <span style='font-size:16px; color:red'>" + chLoss4 + "</span> or more runs.");
        $("#ch-loss-2").html("Score <span style='font-size:16px; color:red'>" + chLoss2 + "-" + (chLoss4-1) +"</span> runs.");
        $("#ch-loss-1").html("Score <span style='font-size:16px; color:red'>" + chLoss1 + "-" + (chLoss2-1) +"</span> runs.");

        var defWin20Text = (defWin20 == 0) ? "Score is too less to get 20 points." :  "Restrict the opposition to <span style='font-size:16px; color:red'>" + defWin20 + "</span> or less runs.";
        var defWin18Text = (defWin18 == 0) ? "Score is too less to get 18 points." :  "Restrict the opposition to <span style='font-size:16px; color:red'>" + (defWin20+1) + "-" + defWin18 + "</span> runs.";
        var defWin17Text = (defWin17 == 0) ? "Score is too less to get 17 points." :  "Restrict the opposition to <span style='font-size:16px; color:red'>" + (defWin18+1) + "-" + defWin17 + "</span> runs.";

        $("#def-win-20").html(defWin20Text);
        $("#def-win-18").html(defWin18Text);
        $("#def-win-17").html(defWin17Text);

        $("#def-loss-4").html("Have the opposition bat <span style='font-size:16px; color:red'>" + defLoss4 + "</span> or more overs.");
        $("#def-loss-2").html("Have the opposition bat <span style='font-size:16px; color:red'>" + defLoss2 + "</span> or more overs.");
        $("#def-loss-1").html("Have the opposition bat <span style='font-size:16px; color:red'>" + defLoss1 + "</span> or more overs.");

        $("#content .modal1").remove();
        $('#base-content').removeClass('invisible');
    }*/
}
