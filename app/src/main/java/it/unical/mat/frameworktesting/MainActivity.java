package it.unical.mat.frameworktesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import it.unical.mat.embasp.asp.AnswerSet;
import it.unical.mat.embasp.asp.AnswerSets;
import it.unical.mat.embasp.asp.IllegalTermException;
import it.unical.mat.embasp.base.Callback;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.base.Service;
import it.unical.mat.embasp.platforms.android.AndroidHandler;
import it.unical.mat.embasp.specializations.dlv.android.DLVAndroidService;


public class MainActivity extends AppCompatActivity implements Callback{


    final int N=9;
    final int M=9;
    int [][]matrix={{1,0,0,0,0,7,0,9,0},
                    {0,3,0,0,2,0,0,0,8},
                    {0,0,9,6,0,0,5,0,0},
                    {0,0,5,3,0,0,9,0,0},
                    {0,1,0,0,8,0,0,0,2},
                    {6,0,0,0,0,4,0,0,0},
                    {3,0,0,0,0,0,0,1,0},
                    {0,4,1,0,0,0,0,0,7},
                    {0,0,7,0,0,0,3,0,0}};

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new AndroidHandler(getApplicationContext(), DLVAndroidService.class);
        printMatrix();
    }



    String getResourceToString(String resourceName){
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier(resourceName,
                        "raw", getPackageName()));
        BufferedReader reader=new BufferedReader(new InputStreamReader(ins));
        String line="";
        StringBuilder builder=new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }



    public void onClick(final View view){

        handler.addProgram(new InputProgram(getResourceToString("sudoku")));
        InputProgram inputProgram=new InputProgram();
        for ( int i = 0; i < N; i++)
            for ( int j = 0; j < M; j++)
                try {
                    if(matrix [ i ] [ j ]!=0) {
                        inputProgram.addObjectInput(
                                new Cell(i, j, matrix[i][j]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        handler.addProgram(inputProgram);
        handler.addOption(new OptionDescriptor("-n=1"));
        handler.startAsync(this);
        Button button=(Button) findViewById(R.id.button);
        button.setEnabled(false);
    }

    @Override
    public void callback(final Output o) {

        if(!(o instanceof AnswerSets))return;
        AnswerSets answerSets=(AnswerSets)o;
        if(answerSets.getAnswersets().size()==0)return;
        AnswerSet as = answerSets.getAnswersets().get(0);
            try {
                for(Object obj:as.getAtoms()) {
                    Cell cell = (Cell) obj;
                    matrix[cell.getRow()][cell.getColumn()]=cell.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        printMatrix();

    }

    private void printMatrix() {
        String out="";
        for ( int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                out += matrix[i][j]+" ";
            }
            out+="\n";

        }


        final String finalOut = out;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) findViewById(R.id.result);
                text.setText(finalOut);
            }
        });
    }



}
