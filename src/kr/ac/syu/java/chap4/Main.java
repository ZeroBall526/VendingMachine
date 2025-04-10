package kr.ac.syu.java.chap4;

import java.util.*;

// ==== [메뉴 정보를 담당하는 클래스] ====
//이진원님 구현
class Menu {
    private final int menuNum; //메뉴 번호
    private final String menuName; //메뉴 이름
    private final int price; //메뉴 가격

    //메뉴 클래스에 들어가야할 기본 인자
    public Menu(int menuNum, String menuName, int price) {
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.price = price;
    }

    public int getMenuNum() { return menuNum; } //메뉴 번호 가져오기
    public String getMenuName() { return menuName; } //메뉴 이름 가져오기
    public int getPrice() { return price; } //메뉴 가격 가져오기
}

// 이진원님 구현
public class Main {
    static int total = 0; // 받은 총액

    // ==== [메뉴 정보] ====
    static Menu[] menus = {
            new Menu(1, "아메리카노", 2000),
            new Menu(2, "크림커피", 2500),
            new Menu(3, "고급커피", 3000)
    };

    private final static ArrayList<Integer> availableMoney = new ArrayList<>(Arrays.asList(0, 100, 500, 1000, 5000, 10000)); // 사용가능한 돈 단위
    private final static ArrayList<Integer> buttonOptions = new ArrayList<>(Arrays.asList(0)); //사용 가능한 버튼 모음 | 기본값 : 0번 (나가기)
    private final static ArrayList<Integer> getMoneyList = new ArrayList<>(); //자판기에 받은 돈 저장

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VendingMachineEngine vme = new VendingMachineEngine(); //자판기 엔진 클래스 호출

        // 사용가능한 화폐 단위 설정, 사용 가능한 버튼 종류 설정 단계
        int addMoneyButtonNum = menus.length + 1; // '돈 추가로 넣기' 버튼 번호
        for (Menu menu : menus) buttonOptions.add(menu.getMenuNum()); // 메뉴들 버튼 등록
        buttonOptions.add(addMoneyButtonNum); // '돈 추가로 넣기' 버튼 등록

        boolean isShowBoard = true; // 메뉴판 보여줄지 말지 결정
        boolean skipPutMoney = false; // 현금 투입여부를 넘길까요?

        while (true) {
            if(!skipPutMoney){
                // 김초휘: 메뉴판 함수 실행
                if(isShowBoard) showMenuBoard(); //  메뉴판 보여주기

                putMoney(sc); //금액 투입 받기
                System.out.println("총 " + total + "원 받았습니다.");
                isShowBoard = false;
            }
            int choice = optionSelect(sc); // 메뉴 선택
            if (choice == -1) { // 자판기 추가 금액 넣기
                skipPutMoney = false;
                continue;
            }

            if (choice == 0) { // 자판기 종료
                    System.out.println("프로그램을 종료합니다.");
                    System.out.println("거스름돈 받으세요, " + readableChangeMoney(swapMoneyArray(getMoneyList.stream().mapToInt(i->i).toArray())));
                    System.out.println("총 액 " + total + "원");
                    break;
            }

            // ==== [자판기 동작 시작] ====
            boolean result = vme.processSelection(choice, getMoneyList);
            if (!result) {
                skipPutMoney = true;
                System.out.println(VendingMachineEngine.failReason); //자판기 동작 실패시 원인 출력
            } else {
                total = 0;
                getMoneyList.clear(); // 받은 돈 초기화
                isShowBoard = true; // 금액 투입때 메뉴판 다시 보이기
                skipPutMoney = false;
                System.out.println(vme.cameOutCoffee + " 나왔습니다."); // 커피 출력
                System.out.println(readableChangeMoney(vme.changeMoney)); // 거스름돈 출력
            }

            /*
            // 김초휘 추가: 다시 주문할지 여부 확인
            System.out.println("다시 주문하시겠습니까? (1: 예, 0: 아니요): ");
            int again = sc.nextInt();
            if (again != 1) {
                System.out.println("감사합니다. 자판기를 종료합니다.");
                sc.close();
                return;
            }
            */
        }
        sc.close();
    }

    // 김초휘 구현: 처음에 메뉴판 보여주고 금액 넣는게 좋을 것 같아서 추가함 + 이쁘게
    // ==== [메뉴판] ====
    private static void showMenuBoard() {
        System.out.println("====== 📋 메뉴판 ======");
        System.out.printf("%-5s | %-10s | %s\n", "번호", "메뉴명", "가격");
        System.out.println("-----------------------------");
        for (Menu m : menus) {
            System.out.printf("%-5d | %-10s | %,6d원\n", m.getMenuNum(), m.getMenuName(), m.getPrice());
        }
        System.out.println("=============================\n");
    }
    
    private static void putMoney(Scanner sc) {
        while (true) {
            System.out.println("금액을 투입하세요: ");
            int money;
            try {
                String input = sc.nextLine();  // buffer flush bug fix
                money = Integer.parseInt(input);
            } catch (InputMismatchException e) { // 올바른 타입이 아닐시
                System.out.println("올바른 요청이 아니에요! 다시 시도 해주세요");
                sc.next(); // 버퍼 비우기
                continue;
            }

            if (availableMoney.contains(money)) {
                //올바른 화폐를 받을시
                System.out.println(money + "원 받았습니다.");
                total += money; // 총액에 금액 추가
                getMoneyList.add(money);
                break;
            } else {
                //0,100,500,1000,5000,10000 이 아닐시
                // 김초휘 수정: 사용가능한 화폐 표시되게함
                System.out.println("올바르지 않은 화폐입니다.");
                System.out.println("사용 가능한 화폐: 0, 100, 500, 1000, 5000, 10000원\n"); //디버깅 옵션으로 0원도 가능함
            }
        }
    }

    // 김초휘 수정: String Builder 로 바꿈
    private static String menuSelectMsg() {
        StringBuilder msg = new StringBuilder();
        for (Menu m : menus) {
            msg.append(m.getMenuNum())
                    .append(". ")
                    .append(m.getMenuName())
                    .append(" - ")
                    .append(String.format("%,d원", m.getPrice())) // 금액까지 나오게함
                    .append("\n");
        }
        return msg.toString();
    }

    // 사용자가 선택할 메뉴 번호를 입력받는 함수
    private static int optionSelect(Scanner sc) {
        while (true) {
            System.out.println(menuSelectMsg() + Collections.max(buttonOptions) + ". 추가 금액 넣기\n0. 종료");
            System.out.println("메뉴 번호를 입력하세요");
            int choice;
            try {
                String input = sc.nextLine();
                choice = Integer.parseInt(input); // buffer flush bug fix
            } catch (InputMismatchException e) {
                System.out.println("올바른 요청이 아니에요! 다시 시도해주세요");
                sc.next(); // 버퍼 비우기
                continue;
            }

            //번호 선택이 잘못 됐을 때
            if (!buttonOptions.contains(choice)) {
                System.out.println("올바르지 않은 번호입니다!");
                continue;
            }

            if (choice == Collections.max(buttonOptions)) {
                return -1;
            }

            return choice;
        }
    }
    //  거스름돈 배열을 사람이 읽기 쉬운 문자열로 변환해주는 함수
    private static String readableChangeMoney(int[] cm) {
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < cm.length; i++) {
            if (cm[i] > 0) {
                msg.append(availableMoney.get(i + 1)).append("원 ");
                msg.append(cm[i]).append((availableMoney.get(i + 1) < 1000 ? "개" : "장")).append(", ");
            }
        }
        if (!msg.isEmpty()) {
            msg.setLength(msg.length() - 2); // 마지막 콤마 제거
            msg.append(" 거스름돈 나왔습니다.");
        }
        return msg.toString();
    }

    private static int[] swapMoneyArray(int[] moneyArr){
        int[] result = new int[5];
        for (int m : moneyArr) {
            switch (m) {
                case 100:
                    result[0]++;
                    break;
                case 500:
                    result[1]++;
                    break;
                case 1000:
                    result[2]++;
                    break;
                case 5000:
                    result[3]++;
                    break;
                case 10000:
                    result[4]++;
                    break;
            }
        }
        return result;
    }
}
