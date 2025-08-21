package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

public class MyPrinterV4 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while(true){
            log("프린터할 문서를 입력하세요. 종료 (p): ");
            String input = userInput.nextLine();
            if(input.equals("p")){
                printer.work = false;
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable{
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while(!Thread.interrupted()){
                if(jobQueue.isEmpty()){
                    Thread.yield(); // 추가
                    continue;
                }

                String job = jobQueue.poll();
                log("출력 시작: " + job + ", 대기 문서:" + jobQueue);
                log("출력 완료");
            }
            log("프린터 종료");
        }

        public void addJob(String input){
            jobQueue.offer(input);
        }
    }
}
