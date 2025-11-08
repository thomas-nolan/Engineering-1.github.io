package io.github.some_example_name;

/* This class is responsible for calculating the player score in the game.
 * Points are earned by in game events but can also be lost due to 
 * penalties such as being caught by the Dean.
 * 
 * If the player escapes, the remaining time left is multipled by 50 and 
 * added to the score.
 * 
 * The final score is displayed at the end of the game.
 */
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

    // Called at the end of the game
    public void calcPoints(double remainingTime){
        int timeBonus = (int) (remainingTime * 50);
        addPoints(timeBonus);
        finalScore = baseScore - penalties;
        if (finalScore < 0){
            finalScore = 0;
        }

    }

    // Getter for points
    public int getScore(){
        return finalScore;
    }
         
}
