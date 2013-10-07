
public class Player {
  //  private String team;
    private int number = 0;
	private int attack = 0;
	private int defense = 0;
    private int power = 0;
    private int precision = 0;
    private int speed = 0;
    private int tackle = 0;
	private int goal_keeping = 0;


    /*** GETTER METHODS ***/
    public int getNumber() {
        return number;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getPower() {
        return power;
    }

    public int getPrecision() {
        return precision;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTackle() {
        return tackle;
    }

    public int getGoal_keeping() {
        return goal_keeping;
    }

    /*** SETTER METHODS ***/
 /*   public void setTeam(String team) {
        this.team = team;
    }*/

     public void setNumber(int number) {
        this.number = number;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTackle(int tackle) {
        this.tackle = tackle;
    }

    public void setGoal_keeping(int goal_keeping) {
        this.goal_keeping = goal_keeping;
    }
}
