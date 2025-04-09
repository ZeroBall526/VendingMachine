package kr.ac.syu.java.chap4;

import java.io.IOException;

public class DisplayEngine {
    public static void showMsg(String msg){
        System.out.println(msg);
    }

    public static void clear() throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")) new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        // 경고! ANSI escape 미지원 터미널은 동작이 안됩니다!
        else {
            System.out.println("\\033[H\\033[2J");
            System.out.flush();
        }
    }
}
