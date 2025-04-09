package kr.ac.syu.java.chap4;

public class MoneyBox {
    private int money[] = {5,5,5,5,5};// 현재 보유한 화폐의 양, 박홍근:수정-> 초기에 화폐를 각각 5개씩 보유하도록 변경
    
    public int getMoneyBox[] = new int[5];// 화폐단위로 분류된 받은 돈
    
    private int totalGetMoney = 0;//받은돈의 총액

    public int totalMoney[] = new int[1000];//받은 돈

    public void insertMoney() { // 돈을 받아서 money와 getMoneyBox에 추가하는메서드
        for(int i :totalMoney) {
            switch(i){
                case 100:
                    money[0] ++;
                    getMoneyBox[0] ++;
                    break;
                case 500:
                    money[1] ++;
                    getMoneyBox[1] ++;
                    break;
                case 1000:
                    money[2] ++;
                    getMoneyBox[2] ++;
                    break;
                case 5000:
                    money[3] ++;
                    getMoneyBox[3] ++;
                    break;
                case 10000:
                    money[4] ++;
                    getMoneyBox[4] ++;
                    break;
                //이진원 : 예외값이 나올걸 대비해 return 추가하는 걸 추천
                default:
                    return;
            }
            totalGetMoney += i;
        }
    }

    public boolean deductMoney(int cost) {//박홍근:설명추가->받은돈이 부족하지 않은지 boolean값으로 반환
        if(totalGetMoney>=cost) {
        	totalGetMoney -= cost;//박홍근: 받은 돈이 충분할 경우 true를 반환하고 받은돈에서 가격을 뺌-> 즉 거슬러줘야 되는 돈의 총액이 계산됨
            return true;
        }else {
            return false;//받은돈이 부족하면 false 반환
        }
    }

    public boolean remainJudge() { 
    	int x = totalGetMoney; // x에 totalGetMoney값을 임시로 저장해 무결성검사가 데이터에 영향을 미치지 않게함
        while(x !=0) { //거슬러줄 돈이 0 이 될때까지 로직 반복
            if (x>=10000 && money[4] !=0) {
                x -= 10000;
            }else if (x>=5000 && money[3] !=0) {
                x -= 5000;
            }else if (x>=1000 && money[2] !=0) {
                x -= 1000;
            }else if (x>=500 && money[1] !=0) {
                x -= 500;
            }else if (x>=100 && money[0] !=0) {
            	x -= 100;
            }else {
            	return false;//잔돈이 부족하면 false 반환
            }
        }
        return true;
    }
    
    public int[] remainingMoney() {//잔돈을 반환해주는 매서드 무결성 검사 이후이므로 데이터를 조작 
    	int changeMoney[] = new int[5];//거스름돈 배열 선언및 초기화
    	while(totalGetMoney !=0) { //거슬러줄 돈이 0 이 될때까지 로직 반복, totalGetMoney는 자연스럽게 0으로 초기화
            if (totalGetMoney>=10000 && money[4] !=0) {
                changeMoney[4]++;
                money[4] --;
                totalGetMoney -= 10000;
            }else if (totalGetMoney>=5000 && money[3] !=0) {
                changeMoney[3] ++;
                money[3] --;
                totalGetMoney -= 5000;
            }else if (totalGetMoney>=1000 && money[2] !=0) {
                changeMoney[2] ++;
                money[2] --;
                totalGetMoney -= 1000;
            }else if (totalGetMoney>=500 && money[1] !=0) {
                changeMoney[1] ++;
                money[1] --;
                totalGetMoney -= 500;
            }else if (totalGetMoney>=100 && money[0] !=0) {
                changeMoney[0] ++;
                money[0] --;
                totalGetMoney -= 100;
            }
        }
    	int getMoneyBox[] = new int[5];//받은 돈을 돌려주었으므로 초기화
        return changeMoney;//잔돈 반환
    }

    public int[] refunds() {//환불해주는 매서드 커피제공이 불가능할때 받았던 돌을 돌려줘야하지 않을까해서 제작했는데 필요 없으면 사용x 다만 해당 매서드를 사용해야 커피제공이 불가능할때 totalGEtMoney와 getMoneyBox가 변수가 초기화됨
    	int y[] = new int [5];
    	for (int j =0; j<5; j++) {
    		y[j] = getMoneyBox[j];
    	}//getMoneyBox를 초기화 하기 위해 배열 y에 값을 저장
     	money[4] -= getMoneyBox[4];
    	money[3] -= getMoneyBox[3];
    	money[2] -= getMoneyBox[2];
    	money[1] -= getMoneyBox[1];
    	money[0] -= getMoneyBox[0];
        totalGetMoney = 0;//받은 돈을 돌려주었으므로 0으로 초기화
        int getMoneyBox[] = new int[5];//받은 돈을 돌려주었으므로 초기화
    	return y;//받은 돈을 배열로 반환(100원 몇개 1000원 몇개 이런식)
    }

}


