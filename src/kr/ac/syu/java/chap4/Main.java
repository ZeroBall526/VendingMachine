package kr.ac.syu.java.chap4;

import java.util.*;

//메뉴 정보를 담당하는 클래스
//이진원님 구현
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

//이진원님 구현
public class Main {
    static int total = 0; // 받은 총액

    //메뉴 정보
    static Menu[] menus = {new Menu(1,"아메리카노",2000),
            new Menu(2,"크림커피",2500),
            new Menu(3,"고급커피",3000)};

    // 김초휘 구현: 메뉴판 만들고 싶어서..쩝
    // 이쁘고 정렬된 메뉴판 보여주는 함수
    private static void showMenuBoard() {
        System.out.println("====== 📋 메뉴판 ======");
        System.out.printf("%-5s | %-10s | %s\n", "번호", "메뉴명", "가격");
        System.out.println("-----------------------------");
        for (Menu m : menus) {
            System.out.printf("%-5d | %-10s | %,6d원\n", m.getMenuNum(), m.getMenuName(), m.getPrice());
        }
        System.out.println("=============================\n");
    }

    private static String menuSelectMsg(){
        String msg = "";
        for (Menu m: menus) msg += m.getMenuNum() + "."+m.getMenuName()+"\n";
        return msg;
    }

    private final static ArrayList<Integer> availableMoney = new ArrayList<>(Arrays.asList(0,100,500,1000,5000,10000)); // 사용가능한 돈 단위
    private final static ArrayList<Integer> buttonOptions = new ArrayList<>(Arrays.asList(0)); //사용 가능한 버튼 모음 | 기본값 : 0번 (나가기)
    private final static ArrayList<Integer> getMoneyList = new ArrayList<>(); //자판기에 받은 돈 저장

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VendingMachineEngine vme = new VendingMachineEngine(); //자판기 엔진 클래스 호출

        // 김초휘: 메뉴판 보여주면 좋을 것 같아서 추가해봄...
        showMenuBoard(); //  메뉴판 보여주기

        // 사용가능한 화폐 단위 설정, 사용 가능한 버튼 종류 설정 단계
        int addMoneyButtonNum = menus.length+1; // '돈 추가로 넣기' 버튼 번호
        for(Menu menu : menus) buttonOptions.add(menu.getMenuNum()); // 메뉴들 버튼 등록
        buttonOptions.add(addMoneyButtonNum); // '돈 추가로 넣기' 버튼 등록

        while (true) {
            //금액 받는 코드
            putMoney(sc);
            System.out.println("총 "+total+"원 받았습니다."); //이제까지 받은 금액 출력
            //메뉴 선택
            int choice = optionSelect(sc);
            if(choice==-1) continue; //돈추가 넣기로 이동
            if(choice==0) break; //자판기 끄기
            //자판기 동작 시작
            boolean result = vme.processSelection(choice,getMoneyList);
            //자판기 동작 실패시 원인 출력
            if(!result) System.out.println(VendingMachineEngine.failReason);
            else{
                //성공시 자판기 초기화
                total = 0;
                getMoneyList.clear(); //돈 받은 내역 초기화 -> 이미 VendingMachineEngine에 돈이 들어갔기에 메인에서 계산된 돈은 리셋
                System.out.println(vme.cameOutCoffee+" 나왔습니다."); // vme 에서 나온 커피 변수 불러오기
                System.out.println(readableChangeMoney(vme.changeMoney)); // vme 에서 거스름돈 가져오기
            }
        }
        sc.close();
    }

    private static void putMoney(Scanner sc){
        while(true){
            System.out.println("금액을 투입하세요: ");
            int money;
            try{
                money = sc.nextInt();
            }catch (InputMismatchException e){ // 올바른 타입이 아닐시
                System.out.println("올바른 요청이 아니에요! 다시 시도 해주세요");
                continue; // 돈 투입 과정 다시 실행
            }

            if(availableMoney.contains(money)) {
                //올바른 화폐를 받을시
                System.out.println(money+"원 받았습니다.");
                total+=money; // 총액에 금액 추가
                getMoneyList.add(money);
                break;
            }else{
                //0,100,500,1000,5000,10000 이 아닐시
                System.out.println("올바르지 않은 화폐입니다.");
                continue; // 돈 투입 과정 다시 실행
            }
        }
    }

    private static int optionSelect(Scanner sc){
        while(true){
            System.out.println(menuSelectMsg()+ Collections.max(buttonOptions)+".추가 금액 넣기\n0.종료");
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
                continue;
            }

            //0번 입력시
            if(choice == 0){
                System.out.println("프로그램을 종료합니다.");
                System.out.println("거스름돈 받으세요:" + getMoneyList + "원");
                System.out.println("총 액 "+ total+"원");
                return 0;
            }
            // 추가 금액 넣기 입력시
            if(choice == Collections.max(buttonOptions)){
                return -1;
            }

            return choice;
        }
    }

    private static String readableChangeMoney(int[] cm){
        String msg = "";
        for(int i=0; i<cm.length; i++ ){
            if(cm[i]>0){
                msg += availableMoney.get(i+1) +"원 ";
                if(availableMoney.get(i+1) == 100||availableMoney.get(i+1) == 500)
                    msg += cm[i]+"개,";
                else msg += cm[i]+"장,";
            }
        }
        if(!msg.isBlank()) {
            msg = msg.substring(0,msg.length()-1);
            msg += " 거스름돈 나왔습니다.";
        }
        return msg;
    }
}



