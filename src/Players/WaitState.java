package Players;

import org.json.simple.JSONObject;

public class WaitState extends State {
    public WaitState(Player player) {
        super(player);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void performAction(String action) {
        super.performAction(action);
    }

    @Override
    public void defineWinner(String winnerName, String loserName, boolean deadHeat) {
        winnerDefinition(winnerName, loserName, deadHeat);
    }
}
