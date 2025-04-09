package kr.ac.syu.java.chap4;

import java.util.ArrayList;

//이진원 구현
public class VendingMachineEngine {
    //실패사유 로그 기본값:null
    static String failReason;
    //잔돈 변수 본값:null
    static int[] changeMoney;
    //나온 커피 변수 기본값:null
    String cameOutCoffee;

    //필요한 클래스 호출
    private final WaterTank waterTank = new WaterTank();
    private final CreamTank creamTank = new CreamTank();
    private final CoffeeBox coffeeBox = new CoffeeBox();
    private final PremiumCoffeeBox premiumCoffeeBox = new PremiumCoffeeBox();
    private final MoneyBox moneyBox = new MoneyBox();

    //저지패턴
    private boolean integrityCheck(int price){
        //NOTE : intergrityCheck 메서드의 조건문은 항상 failReason과 함께하는 전제로 설계되어있어요
        // 모든 재고가 어떤 커피를 만들든 충분히 제작할수 있는가 검증
        if(!moneyBox.deductMoney(price)){
            failReason = "투입하신 금액이 부족해요!";
            moneyBox.refunds();
            return false;
        }
        if(!moneyBox.remainJudge()) {
            failReason = "거스름돈 잔고가 부족합니다!";
            moneyBox.refunds();
            return false;
        }
        if(!coffeeBox.enoughCoffee(20)){
            failReason = "커피가루가 부족합니다!";
            moneyBox.refunds();
            return false;
        }
        if(!premiumCoffeeBox.enoughCoffee(20)){
            failReason = "프리미엄 커피가루가 부족합니다!";
            moneyBox.refunds();
            return false;
        }
        if(!waterTank.availableWater(150)){
            failReason = "물이 부족합니다!";
            moneyBox.refunds();
            return false;
        }
        if(!creamTank.availableCream(20)){
            failReason = "크림이 부족합니다!";
            moneyBox.refunds();
            return false;
        }
        return true;
    }

    //제조 메서드?
    private void build(){

    }

    //실행 패턴
    public boolean processSelection(int choice, ArrayList<Integer> money){
        moneyBox.totalMoney = money.stream().mapToInt(i -> i).toArray(); //money ArrayList -> Int Array로 형 변환후 돈통에 돈 넣기
        moneyBox.insertMoney();

        //먼저 선택한 번호의 커피가 생산 가능한지 체크
        boolean integrity = integrityCheck(Main.menus[choice-1].getPrice());
        if(!integrity) return false; //만약 무결성검사에서 실패가 날시 바로 제조작업 취소

        //재료 소진을 통한 커피 제조
        switch(choice){
            case 3:
                //고급 커피 레시피
                //cameOutCoffee = "고급커피";
                waterTank.useWater(150);
                premiumCoffeeBox.useCoffee(20);
                creamTank.useCream(20);
                break;
            case 2:
                //크림 커피 레시피
                //cameOutCoffee = "크림커피";
                waterTank.useWater(150);
                coffeeBox.useCoffee(20);
                creamTank.useCream(20);
                break;
            case 1:
                //아메리카노 레시피
                //cameOutCoffee = "아메리카노";
                waterTank.useWater(150);
                coffeeBox.useCoffee(20);
                break;
            default:
                failReason = "올바르지 않은 주문이에요!";
                return false;
        }

        //TODO: cameOutCoffee 에 나온 커피
        cameOutCoffee = Main.menus[choice-1].getMenuName();
        changeMoney = moneyBox.remainingMoney();
        return true;
    }
}
