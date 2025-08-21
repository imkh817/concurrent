package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread t = new Thread(myTask);
        log("runFlag = " + myTask.runFlag);
        t.start();

        sleep(1000);
        log("runFlag를 false로 변경 시도");
        myTask.runFlag = false;
        log("runFlag = " + myTask.runFlag);
        log("main 종료");

    }

    static class MyTask implements Runnable{
        //boolean runFlag = true;
        volatile boolean runFlag = true;
        @Override
        public void run() {
            log("task 시작");
            while(runFlag){
                // runFlag 가 false면 탈출
            }
            log("종료");
        }
    }
}
