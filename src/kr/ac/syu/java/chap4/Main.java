package kr.ac.syu.java.chap4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int total = 0;

    //이진원 구현
    public static void main(String[] args) {
        //TODO : TryCatch 로 올바른 타입인지 구분, 금액투입과 메뉴 선택 모듈화
        Scanner sc = new Scanner(System.in);
        VendingMachineEngine vm = new VendingMachineEngine();

        ArrayList<Integer> availableMoney = new ArrayList<>(Arrays.asList(0,100,500,1000,5000,10000));
        ArrayList<Integer> buttonOptions = new ArrayList<>(Arrays.asList(1,2,3,4,0));

        ArrayList<Integer> getMoneyList = new ArrayList<>();

        while (true) {
            //금액 받는 코드
            System.out.println("금액을 투입하세요: ");
            int money = sc.nextInt();
            if(availableMoney.contains(money)) {
                System.out.println(money+"원 받았습니다.");
                total+=money;
                getMoneyList.add(money);
            }else{
                //100,500,1000,5000,10000 받을시
                System.out.println("올바르지 않은 화폐입니다.");
                continue;
            }
            System.out.println("총"+total+" 원 받았습니다.");

            //메뉴 선택
            System.out.println("1.아메리카노\n2.라떼\n3.고급커피\n4.추가 금액 넣기\n0.종료: ");
            System.out.println("메뉴 번호를 입력하세요");
            int choice = sc.nextInt();

            //번호 선택이 잘못 됬을 때
            if(!buttonOptions.contains(choice)) {
                System.out.println("올바르지 않은 번호입니다!");
            }

            //0번 입력시
            if(choice == 0){
                System.out.println("프로그램을 종료합니다.");
                System.out.println("거스름돈 받으세요:" + getMoneyList + "원");
                System.out.println("총 액"+ total+"원");
                break;
            }
            //4번 입력시
            if(choice == 4){
                continue;
            }

            //자판기 동작시작
            boolean result = vm.processSelection(choice,getMoneyList);
            //자판기 동작 실패시 원인 출력
            if(!result) System.out.println(VendingMachineEngine.failReason);
            else{
                //성공시 자판기 초기화
                total = 0;
                getMoneyList.clear();
                System.out.println(vm.cameOutCoffee+" 나왔습니다.");
                System.out.println(VendingMachineEngine.changeMoney + "거스름돈 나왔습니다.");
            }
        }
    }
}
