package Players;

import org.json.simple.JSONObject;

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
    public void defineWinner(String winnerName, String loserName, boolean deadHeat) { return; }

    public void winnerDefinition(String winnerName, String loserName, boolean deadHeat) {
        int resultOfGame = (winnerName.compareTo(player.getName()) == 0 ? 1 : 0);

        if (deadHeat) {
            resultOfGame = 2;
        }

        player.getMatrix().setCurrentState(StateEventMatrix.END);
        player.getEnemyPlayer().getMatrix().setCurrentState(StateEventMatrix.END);

        JSONObject ownMsg = new JSONObject();

        ownMsg.put("event", StateEventMatrix.CHANGE_STATE);
        ownMsg.put("state", StateEventMatrix.END);
        ownMsg.put("result_of_game", resultOfGame);
        ownMsg.put("winner", winnerName);
        ownMsg.put("loser", loserName);
        player.sendMailToItself(ownMsg.toString());


        if (!deadHeat) {
            resultOfGame = (resultOfGame == 0) ? 1 : 0;
        }

        JSONObject enemyMsg = new JSONObject();

        enemyMsg.put("event", StateEventMatrix.CHANGE_STATE);
        enemyMsg.put("state", StateEventMatrix.END);
        enemyMsg.put("result_of_game", resultOfGame);
        enemyMsg.put("winner", winnerName);
        enemyMsg.put("loser", loserName);
        player.sendMailToEnemy(ownMsg.toString());

        try {
            /*player.getOwnSocket().close();
            player.getEnemySocket().close();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
