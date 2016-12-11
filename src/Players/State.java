package Players;

/**
 * Created by Дмитрий on 12/3/2016.
 */
public abstract class State {
    public Player player;
    public State(Player player){
        this.player = player;
    }
    public void setName(String name) {
        return;
    }
    public void performAction(String action) { return; }
}
