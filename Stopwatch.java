public class Stopwatch {
    private int minutes;
    private int seconds;
    private int totalSeconds;
    private boolean running = true;
    private boolean reset_tag = false;


    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes){
        this.minutes=minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void start(){
        totalSeconds = this.minutes * 60 + this.seconds;
        while (totalSeconds > 0 && running){
            System.out.println(totalSeconds/60 + ":" + totalSeconds%60);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            totalSeconds--;

        }
    }
    public void reset(){
        reset_tag = true;


    }
    public void stop(){
        this.running = false;
    }


}
