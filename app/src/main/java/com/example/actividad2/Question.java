package com.example.actividad2;

public class Question {
    //Attributes
    private int number1, number2;
    private String operator;
    private String [] operators = {"+", "-", "x", "/"};

    //Constructor
    public Question(){
        this.number1 = (int) (Math.random()*11);
        this.number2 = (int) (Math.random()*11);

        int position = (int) (Math.random()*4);
        this.operator = operators[position];
    }

    //Methods
    public String getQuestion(){
        return number1 + " " + operator + " " + number2;
    }

    public int getAnswer(){
        int answer = 0;
        switch (operator){
            case "+":
                answer = this.number1 + this.number2;
                break;
            case "-":
                answer = this.number1 - this.number2;
                break;
            case "x":
                answer = this.number1 * this.number2;
                break;
            case "/":
                answer = this.number1 / this.number2;
                break;
        }

        return answer;
    }

}
