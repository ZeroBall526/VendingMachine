package kr.ac.syu.java.chap4;


import java.util.ArrayList;

//이진원 구현
public class VendingMachineEngine {
    //실패사유 로그
    static String failReason;
    static int[] changeMoney;
    String cameOutCoffee;

    //여유 재고
    private final WaterTank waterTank = new WaterTank();
    private final CreamTank creamTank = new CreamTank();
    private final CoffeeBox coffeeBox = new CoffeeBox();
    private final PremiumCoffeeBox premiumCoffeeBox = new PremiumCoffeeBox();
    private final MoneyBox moneyBox = new MoneyBox();

    /*
    선택번호에 따른 가격
    0 -> null(없는 메뉴)
    1 -> 2000(아메리카노)
    2 -> 2500(크림커피)
    3 -> 3000(고급커피)
     */
    //map으로 바꿔보는건 어떨까?
    private final int[] MenuChoicePrice = {0,2000,2500,3000};

    //저지패턴
    private boolean integrityCheck(){
        //NOTE : intergrityCheck 메서드의 조건문은 항상 failReason과 함께하는 전제로 설계되어있어요
        //거스름 잔고 체크
        if(!moneyBox.remainJudge()) {
            failReason = "거스름돈 잔고가 부족합니다!";
            return false;
        }
        //TODO: 물탱크, 크림탱크, 커피박스 재고 무결성 검사
        return true;
    }

    //실행 패턴
    public boolean processSelection(int choice, ArrayList<Integer> money){
        moneyBox.totalMoney = money.stream().mapToInt(i -> i).toArray(); //money ArrayList -> Int Array로 형 변환

        //먼저 선택한 번호의 커피가 생산 가능한지 체크
        boolean intergrity = integrityCheck();
        if(!intergrity) return false;

        //커피제조 단계
        moneyBox.deductMoney(MenuChoicePrice[choice]); //돈통에 수금

        //재료 소진을 통한 커피 제조
        switch(choice){
            case 3:
                //고급 커피 레시피
                cameOutCoffee = "고급커피";
                waterTank.useWater(150);
                premiumCoffeeBox.useCoffee(20);
                creamTank.useCream(20);
            case 2:
                //크림 커피 레시피
                cameOutCoffee = "크림커피";
                waterTank.useWater(150);
                coffeeBox.useCoffee(20);
                creamTank.useCream(20);
            case 1:
                //아메리카노 레시피
                cameOutCoffee = "아메리카노";
                waterTank.useWater(150);
                coffeeBox.useCoffee(20);
                break;
            default:
                failReason = "올바르지 않은 주문이에요!";
                return false;
        }

        //TODO: cameOutCoffee 에 나온 커피
        changeMoney = moneyBox.remainingMoney();
        return true;
    }
}
