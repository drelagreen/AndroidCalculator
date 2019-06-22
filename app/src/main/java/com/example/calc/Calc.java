package com.example.calc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Calc {
    /**
     * by Drelagreen
     * 22.06.2019
     * Калькулятор на
     * обратной польской записb
     * **********приоритеты
     * ^ - high
     * * / - medium
     * + - low
     * ( ) - lowest
     * **********
     * *Первым шагом идет проверка строки на возможность вычисления
     * *Если обнаружится несостыковка - алгоритм прекращает работу и выводит ошибку
     * <p>
     * **Вторым шагом идет сортировочная станция, преобразующая строку в обратную польскую запись
     * <p>
     * ***Третий шаг - Стэковой машиной вычисляем значение
     * <p>
     * //**Метод nextNum() выделяет число из нескольких символов, если в нем что-то пойдет не так - алгоритм выведет ошибку
     * //***isOperation определяет операционный ли символ или нет
     */
    static StringBuilder rezult = new StringBuilder();
    static StringBuilder sb = new StringBuilder();
    private static Stack<Character> stack = new Stack<>();

    public static String main(String str) {
        char[] c = str.toCharArray();
        int otkrSkobki = 0;
        int zakrSkobki = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != '.' && c[i] != ',' && !isOperation(c[i]) && !Character.isDigit(c[i])) {
                System.out.println("1error " + c[i] + " " + i);
                return "error";
            } else if (c[i] == '(') otkrSkobki++;
            else if (c[i] == ')') zakrSkobki++;
            else if ((i == 0 || i == c.length - 1) &&c[i]!='-'&& (isOperation(c[i]) || c[i] == '.' || c[i] == ',')) {
                System.out.println("2error " + c[i] + " " + i);
                return "error";
            } else if (i != 0 && isOperation(c[i]) && isOperation(c[i - 1])) {
                if (!(c[i - 1] == '(' && c[i] == '-') && !(c[i - 1] == ')')) {
                    System.out.println("3error " + c[i] + " " + i);
                    return "error";
                }
            } else if ((c[i] == '.' || c[i] == ',') && !Character.isDigit(c[i - 1]) && !Character.isDigit(c[i + 1])) {
                System.out.println("4error " + c[i] + " " + i);
                return "error";
            }
        }
        if (otkrSkobki != zakrSkobki) {
            System.out.println("5error skobki");
            return "error";
        }


        for (int i = 0; i < c.length; i++) {

            if (Character.isDigit(c[i])) {
                sb.append(c[i]);
            } else if (c[i] == '.' || c[i] == ',') {
                sb.append(c[i]);
            } else if (isOperation(c[i])) {
                nextNum();
                int p = getPriorityLevel(c[i]);
                if (c[i] == '-' && c[i + 1] == '(') {
                    rezult.append(0 + " ");
                }
                if (!stack.empty() && c[i] != '(') {
                    if (c[i] == ')') {
                        char temp = stack.pop();
                        while (temp != '(') {
                            rezult.append(temp).append(" ");
                            temp = stack.pop();
                        }
                    } else if (getPriorityLevel(c[i]) > getPriorityLevel(stack.peek()))
                        stack.push(c[i]);
                    else {
                        while (!stack.empty() && getPriorityLevel(c[i]) <= getPriorityLevel(stack.peek()))
                            rezult.append(stack.pop()).append(" ");
                        stack.push(c[i]);
                    }
                } else
                    stack.push(c[i]);
            }
        }
        nextNum();

        while (!stack.empty()) {
            rezult.append(stack.pop()).append(" ");
        }

        System.out.println(rezult.toString());
        return (calcFromString(rezult.toString()));
    }
    static String calcFromString(String s) {
        Stack<String> stack = new Stack<>();
        LinkedList<String> ll = new LinkedList<>(Arrays.asList(s.split(" ")));
        String t = ll.remove();
        while (ll.size()>=0){
            if (!isOperation(t.charAt(0))){
                stack.push(t);
                t = ll.remove();
            }
            else{
                double a = 0;
                double b = 0;
                double d = (double) 0;
                switch (t){
                    case "+":

                        d = Double.parseDouble(stack.pop()) + Double.parseDouble(stack.pop());
                        break;
                    case "-":
                        a = Double.parseDouble(stack.pop());
                        b = Double.parseDouble(stack.pop());
                        d = b-a;
                        break;
                    case "*":
                        d = Double.parseDouble(stack.pop()) * Double.parseDouble(stack.pop());

                        break;
                    case "/":
                        a = Double.parseDouble(stack.pop());
                        b = Double.parseDouble(stack.pop());
                        d =b/a;

                        break;
                    case "^":
                        a = Double.parseDouble(stack.pop());
                        b = Double.parseDouble(stack.pop());
                        d = Math.pow(b,a);
                        break;
                }
                stack.push(Double.toString(d));
                if (ll.size()>0){
                    t = ll.remove();
                }
                else {
                    break;
                }
            }

        }
        return stack.pop();
    }


    static boolean isOperation(char c) {
        char[] operands = {'+', '-', '*', '/', '^', '(', ')'};
        for (char c1 : operands) {
            if (String.valueOf(c1).equals(String.valueOf(c))) return true;
        }
        return false;
    }

    static void nextNum() {
        if (sb.length() != 0) {
                double e = Double.parseDouble(sb.toString());
                rezult.append(e);
                rezult.append(" ");
                sb = new StringBuilder();
        }
    }

    static int getPriorityLevel(char c) {
        switch (c) {
            case '*':
            case '/':
                return 3;
            case '^':
                return 4;
            case '+':
            case '-':
                return 2;
            case '(':
            case ')':
                return 1;
        }
        return 0;
    }
}



