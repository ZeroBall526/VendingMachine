package kr.ac.syu.java.chap4;

public class MoneyBox {
     private int money[] = {5,5,5,5,5};// 현재 보유한 화폐의 양, 박홍근:수정-> 초기에 화폐를 각각 5개씩 보유하도록 변경

    private int getMoney = 0;//받은돈의 총액

    public int totalMoney[] = new int[1000];//받은 돈

    public int changeMoney[] = new int[5]; // 거스름돈 배열

    public void insertMoney() { // 돈 받기
        for(int i :totalMoney) {
            switch(i){
                case 100:
                    money[0] +=1;
                    break;
                case 500:
                    money[1] +=1;
                    break;
                case 1000:
                    money[2] +=1;
                    break;
                case 5000:
                    money[3] +=1;
                    break;
                case 10000:
                    money[4] +=1;
                    break;
                //이진원 : 예외값이 나올걸 대비해 return 추가하는 걸 추천
                default:
                    return;
            }
            getMoney += i;
        }
    }

    public boolean deductMoney(int cost) {//박홍근:설명추가->받은돈이 부족하지 않은지 boolean값으로 반환
        if(getMoney>=cost) {
            getMoney -= cost;//박홍근: 받은 돈이 충분할 경우 true를 반환하고 받은돈에서 가격을 뺌-> 즉 거슬러줘야 되는 돈의 총액이 계산됨
            return true;
        }else {
            return false;
        }
    }

    public boolean remainJudge() { //거스름돈 배열 구성 빨간색 왜뜨는지 모르겠음 -> boolean return 값이 없어서
        while(getMoney !=0) { //거슬러줄 돈이 0 이 될때까지 로직 반복
            if (getMoney>=10000 && money[4] !=0) {
                changeMoney[4]++;
                money[4] --;
                getMoney -= 10000;
            }else if (getMoney>=5000 && money[3] !=0) {
                changeMoney[3] ++;
                money[3] --;
                getMoney -= 5000;
            }else if (getMoney>=1000 && money[2] !=0) {
                changeMoney[2] ++;
                money[2] --;
                getMoney -= 1000;
            }else if (getMoney>=500 && money[1] !=0) {
                changeMoney[1] ++;
                money[1] --;
                getMoney -= 500;
            }else if (getMoney>=100 && money[0] !=0) {
                changeMoney[0] ++;
                money[0] --;
                getMoney -= 100;
            }else {
                return false;
            }
        }
        return true;
    }

    public int[] remainingMoney() {//거슬러줄 화페의 배열(ex 100원2개 500원1개)을 반환하는 매서드 
        return changeMoney;
    }

}
