package io.github.some_example_name;

public class Points {
    private int baseScore;
    private int penalties;
    private int finalScore;

    public Points() {
        baseScore = 0;
        penalties = 0;
        finalScore = 0;
    }

    public void addPoints(int points){
        baseScore += points;
    }
        
    public void subtractPoints(int points){
        penalties += points;
    }

    public void deanCaughtYou(){
        penalties += 200;
    }

    public void calcPoints(float remainingTime){
        int timeBonus = (int) (remainingTime * 50);
        addPoints(timeBonus);
        finalScore = baseScore - penalties;
        if (finalScore < 0){
            finalScore = 0;
        }

    }

    public int getScore(){
        return finalScore;
    }
         
}
