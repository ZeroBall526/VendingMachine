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
    // 이진원 수정 요청
    // 물을 사용할 수 있는지 없는지 boolean으로 알려주는 코드
    public boolean availableWater (int use) {
        return waterAmount >= use;
    }
    /*
    다른 클래스에서 사용할 때 이런식으로 호출해서 쓰기
    WaterTank tank = new WaterTank();

    if (tank.availableWater(200)) {
        System.out.println("물 사용 가능!");
    } else {
        System.out.println("물 부족!");
    }
    
     */
}
