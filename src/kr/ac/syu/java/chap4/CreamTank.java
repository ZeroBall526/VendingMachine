package kr.ac.syu.java.chap4;

//김초휘님 구현
public class CreamTank {
    private int creamAmount = 500; // 초기값

    public boolean useCream(int amount) {
        if (creamAmount >= amount) { // 크림통에 물이 필요한 양보다 적을때
            creamAmount -= amount;
            return true; // return 실행되면 메서드 종료
        }
        return false;
    }
}
