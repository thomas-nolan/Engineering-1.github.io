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
    /* Points constructor
     * 
     * Initialises all points to 0 at the start.
     */
    public Points() {
        baseScore = 0;
        penalties = 0;
        finalScore = 0;
    }

    /* Adds a number of points to the player
     * @param points - The number of points to add
     */
    public void addPoints(int points){
        baseScore += points;
    }
     
    /* Subtracts a number of points to the player
     * @param points - The number of points to subtract
     */
    public void subtractPoints(int points){
        penalties += points;
    }

    /* Applies 200 point penalty if caught by dean */
    public void deanCaughtYou(){
        penalties += 200;
    }

    /* Calculates a final total of points at the end of the game
     * based on remaining time, base score and any pentlties incurred
     * @param remainingTime - The time left on the timer
     */
    public void calcPoints(double remainingTime){
        int timeBonus = (int) (remainingTime * 50);
        addPoints(timeBonus);
        finalScore = baseScore - penalties;
        if (finalScore < 0){
            finalScore = 0;
        }

    }

    /* Getter for the final score
     * @return finalScore - The player's final score
     */
    public int getScore(){
        return finalScore;
    }
         
}
