package Players;

import org.json.simple.JSONObject;

public class ActionState extends State {
    public ActionState(Player player) {
        super(player);
    }

    @Override
    public void performAction(String action) {
        System.out.println("RECEIVED from ACTION - " + action);
        player.getMatrix().setCurrentState(StateEventMatrix.WAIT);
        player.getEnemyPlayer().getMatrix().setCurrentState(StateEventMatrix.ACTION);

        JSONObject ownMsg = new JSONObject();

        ownMsg.put("event", StateEventMatrix.CHANGE_STATE);
        ownMsg.put("state", StateEventMatrix.WAIT);
        ownMsg.put("name_of_enemy", player.getEnemyPlayer().getName());
        player.sendMailToItself(ownMsg.toString());

        JSONObject enemyMsg = new JSONObject();

        enemyMsg.put("event", StateEventMatrix.CHANGE_STATE);
        enemyMsg.put("state", StateEventMatrix.ACTION);
        enemyMsg.put("name_of_enemy", player.getName());

        player.sendMailToEnemy(enemyMsg.toString());
    }

    @Override
    public void defineWinner(String winnerName, String loserName, boolean deadHeat) {
        winnerDefinition(winnerName, loserName, deadHeat);
    }
}
