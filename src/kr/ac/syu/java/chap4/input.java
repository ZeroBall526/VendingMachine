package kr.ac.syu.java.chap4;

import java.util.ArrayList;

public class input {
    private int choice;
    private ArrayList<Integer> moneyList = new ArrayList<>();

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setMoneyList(ArrayList<Integer> moneyList) {
        this.moneyList = moneyList;
    }

    public int getChoice() {
        return choice;
    }

    public ArrayList<Integer> getMoneyList() {
        return moneyList;
    }
}
