package kr.ac.syu.java.chap4;

public class MoneyBox {
    private int money[] = new int[5];// 현재 보유한 화폐의 양

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

    public boolean deductMoney(int cost) {
        if(getMoney>=cost) {
            getMoney -= cost;// 받은돈의 총액을 거슬러줄 돈의 총액으로 변환
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

    public int[] remainingMoney() {
        return changeMoney;
    }

}
