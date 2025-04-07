package kr.ac.syu.java.chap4;

//김초휘님 구현
public class WaterTank {
    private int waterAmount = 1000; // 초기값

    public boolean useWater(int amount) {
        if (waterAmount >= amount) { // 물통에 물이 필요한 양보다 적을때
            waterAmount-= amount;
            return true; // return 실행되면 메서드 종료
        }
        return false;
    }
}
