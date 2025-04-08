package kr.ac.syu.java.chap4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//메뉴 정보를 담당하는 클래스
class Menu{
    private final int menuNum; //메뉴 번호
    private final String menuName; //메뉴 이름
    private final int price; //메뉴 가격

    //메뉴 클래스에 들어가야할 기본 인자
    public Menu(int menuNum, String menuName, int price){
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.price = price;
    }

    public int getMenuNum(){ return menuNum; } //메뉴 번호 가져오기
    public String getMenuName(){ return menuName; } //메뉴 이름 가져오기
    public int getPrice(){ return price; } //메뉴 가격 가져오기
}

public class Main {
    static int total = 0; // 받은 총액

    //메뉴 정보
    static Menu[] menus = {new Menu(1,"아메리카노",2000),
                           new Menu(2,"크림커피",2500),
                           new Menu(3,"고급커피",3000)};

    static private String menuSelectMsg(){
        String msg = "";
        for (Menu m: menus) msg += m.getMenuNum() + "."+m.getMenuName()+"\n";
        return msg;
    }

    //이진원 구현
    public static void main(String[] args) {
        //TODO : TryCatch 로 올바른 타입인지 구분, 금액투입과 메뉴 선택 모듈화
        Scanner sc = new Scanner(System.in);
        VendingMachineEngine vme = new VendingMachineEngine(); //자판기 엔진 클래스 호출

        // 사용가능한 화폐 단위 설정, 사용 가능한 버튼 종류 설정 단계
        ArrayList<Integer> availableMoney = new ArrayList<>(Arrays.asList(0,100,500,1000,5000,10000)); // 사용가능한 돈 단위
        ArrayList<Integer> buttonOptions = new ArrayList<>(Arrays.asList(0)); //사용 가능한 버튼 모음 | 기본값 : 0번 (나가기)
        int addMoneyButtonNum = menus.length+1; // '돈 추가로 넣기' 버튼 번호
        for(Menu menu : menus) buttonOptions.add(menu.getMenuNum()); // 메뉴들 버튼 등록
        buttonOptions.add(addMoneyButtonNum); // '돈 추가로 넣기' 버튼 등록

        ArrayList<Integer> getMoneyList = new ArrayList<>();

        while (true) {
            //금액 받는 코드
            System.out.println("금액을 투입하세요: ");
            int money;
            try{
                money = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("올바른 요청이 아니에요! 다시 시도 해주세요");
                continue;
            }

            if(availableMoney.contains(money)) {
                //올바른 화폐를 받을시
                System.out.println(money+"원 받았습니다.");
                total+=money; // 총액 계산기에
                getMoneyList.add(money);
            }else{
                //0,100,500,1000,5000,10000 받을시
                System.out.println("올바르지 않은 화폐입니다.");
                continue;
            }
            System.out.println("총"+total+" 원 받았습니다.");

            //메뉴 선택
            System.out.println(menuSelectMsg()+addMoneyButtonNum+".추가 금액 넣기\n0.종료: ");
            System.out.println("메뉴 번호를 입력하세요");
            int choice;
            try{
                choice = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("올바른 요청이 아니에요! 다시 시도해주세요");
                continue;
            }

            //번호 선택이 잘못 됬을 때
            if(!buttonOptions.contains(choice)) {
                System.out.println("올바르지 않은 번호입니다!");
            }

            //0번 입력시
            if(choice == 0){
                System.out.println("프로그램을 종료합니다.");
                System.out.println("거스름돈 받으세요:" + getMoneyList + "원");
                System.out.println("총 액 "+ total+"원");
                break;
            }
            //4번 입력시
            if(choice == 4){
                continue;
            }

            //자판기 동작시작
            boolean result = vme.processSelection(choice,getMoneyList);
            //자판기 동작 실패시 원인 출력
            if(!result) System.out.println(VendingMachineEngine.failReason);
            else{
                //성공시 자판기 초기화
                total = 0;
                getMoneyList.clear(); //돈 받은 내역 초기화 -> 이미 VendingMachineEngine에 돈이 들어갔기에 메인에서 계산된 돈은 리셋
                System.out.println(vme.cameOutCoffee+" 나왔습니다."); // vme 에서 나온 커피 변수 불러오기
                System.out.println(Arrays.toString(VendingMachineEngine.changeMoney) + "거스름돈 나왔습니다."); // vme 에서 거스름돈 가져오기
            }
        }
        sc.close();
    }
}
