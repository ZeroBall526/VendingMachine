package kr.ac.syu.java.chap4;

//김초휘님 구현
public class CreamTank {

    public CreamTank(int amount){
        creamAmount = amount;
    }

    private int creamAmount; // 초기값

    public boolean useCream(int amount) {
        if (creamAmount >= amount) { // 크림통에 물이 필요한 양보다 적을때
            creamAmount -= amount;
            return true; // return 실행되면 메서드 종료
        }
        return false;
    }
    // 이진원이 수정 요청
    // 크림을 사용할 수 있는지 없는지 boolean으로 알려주는 코드
    public boolean availableCream(int use) {
        return creamAmount >= use;
    }
    /*
    다른 클래스에서 (VendingMachine?)에서
    대충 호출해서 이런식으로 쓰기
    WaterTank tank = new WaterTank();

    if (tank.availableWater(150)) {
        System.out.println("크림 사용 가능!");
    } else {
        System.out.println("크림 부족!");
    }
    
     */
}
