package com.example.calculator;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, button10, buttonC, buttonEqual;
    EditText resEditText;
    TextView txt;
    float mValueOne, mValueTwo;
    boolean Addition, Subtract, Multiplication, Division;
    private TableLayout tableLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        tableLayout = (TableLayout) findViewById(R.id.root_view);
        animationDrawable = (AnimationDrawable) tableLayout.getBackground();
// setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(5000);

// setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);

        button0 = (Button) findViewById(R.id.zero);
        button1 = (Button) findViewById(R.id.one);
        button2 = (Button) findViewById(R.id.two);
        button3 = (Button) findViewById(R.id.three);
        button4 = (Button) findViewById(R.id.four);
        button5 = (Button) findViewById(R.id.five);
        button6 = (Button) findViewById(R.id.six);
        button7 = (Button) findViewById(R.id.seven);
        button8 = (Button) findViewById(R.id.eight);
        button9 = (Button) findViewById(R.id.nine);

        buttonAdd = (Button) findViewById(R.id.plus);
        buttonSub = (Button) findViewById(R.id.minus);
        buttonMul = (Button) findViewById(R.id.multiple);
        buttonDivision = (Button) findViewById(R.id.divide);
        buttonC = (Button) findViewById(R.id.clear);
        buttonEqual = (Button) findViewById(R.id.equal);
        txt = (TextView) findViewById(R.id.tv);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "0");
            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                txt.setText(txt.getText() + "+");

            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "-");
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "*");
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText(txt.getText() + "/");
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // txt.setText(txt.getText()+"=");


                String s = txt.getText().toString();

               double reslt=compute(s);
               String ans=""+reslt;

                txt.setText(ans);


            }


        });
    }
    public enum Operator{ADD, SUBTRACT, MULTIPLY, DIVIDE, BLANK}


    public double compute(String s){
        Stack<Double> numberStack = new Stack<Double>();
        Stack<Operator> operatorStack = new Stack<Operator>();
        for(int i = 0; i < s.length(); i++){
            try{
                int number = parseNumber(s, i);
                numberStack.push((double)number);

                i += Integer.toString(number).length();
                if(i >= s.length()){
                    break;
                }

                Operator op = parseOperator(s, i);
                collapseTop(numberStack, operatorStack, op);
                operatorStack.push(op);
            } catch(NumberFormatException ex){
                return Integer.MIN_VALUE;
            }
        }
        collapseTop(numberStack, operatorStack, Operator.BLANK);
        if(numberStack.size() == 1 && operatorStack.size() == 0){
            return numberStack.pop();
        }
        return 0;
    }

    private void collapseTop(Stack<Double> numberStack, Stack<Operator> operatorStack, Operator futureTop){
        while(numberStack.size() >= 2 && operatorStack.size() >= 1){
            if(priorityOfOperator(futureTop) <= priorityOfOperator(operatorStack.peek())){
                double second = numberStack.pop();
                double first = numberStack.pop();
                Operator op = operatorStack.pop();
                double result = applyOp(first, op, second);
                numberStack.push(result);
            } else{
                break;
            }
        }
    }

    private double applyOp(double left, Operator op, double right){
        switch (op){
            case ADD: return left + right;
            case SUBTRACT: return left - right;
            case MULTIPLY: return left * right;
            case DIVIDE: return left / right;
            default: return right;
        }

    }

    private int priorityOfOperator(Operator op){
        switch (op){
            case ADD: return 1;
            case SUBTRACT: return 1;
            case MULTIPLY: return 2;
            case DIVIDE: return 2;
            case BLANK: return 0;
        }
        return 0;
    }

    private int parseNumber(String sequence, int offset){
        StringBuilder sb = new StringBuilder();
        while(offset < sequence.length() && Character.isDigit(sequence.charAt(offset))){
            sb.append(sequence.charAt(offset));
            offset++;
        }
        return Integer.parseInt(sb.toString());
    }

    private Operator parseOperator(String sequence, int offset){
        if(offset < sequence.length()){
            char op = sequence.charAt(offset);
            switch (op){
                case '+': return Operator.ADD;
                case '-': return Operator.SUBTRACT;
                case '*': return Operator.MULTIPLY;
                case '/': return Operator.DIVIDE;
            }
        }
        return Operator.BLANK;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
// start the animation
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
// stop the animation
            animationDrawable.stop();
        }
    }
}

