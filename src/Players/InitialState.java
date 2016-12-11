package Players;

import org.json.simple.JSONObject;

public class InitialState extends State {

    public InitialState(Player player) {
        super(player);
    }
    @Override
    public void setName(String name) {
        player.setName(name);
        if (!player.isGameStarted()) {
            player.getMatrix().setCurrentState(StateEventMatrix.WAIT_FOR_GAME);
            JSONObject obj = new JSONObject();

            obj.put("event", StateEventMatrix.CHANGE_STATE);
            obj.put("state", StateEventMatrix.WAIT_FOR_GAME);
            player.sendMailToItself(obj.toString());
        } else {
            player.getMatrix().setCurrentState(StateEventMatrix.WAIT);
            JSONObject ownMsg = new JSONObject();

            ownMsg.put("event", StateEventMatrix.CHANGE_STATE);
            ownMsg.put("state", StateEventMatrix.WAIT);
            ownMsg.put("name_of_enemy", player.getEnemyPlayer().getName());
            player.sendMailToItself(ownMsg.toString());

            player.getMatrix().setCurrentState(StateEventMatrix.WAIT);

            JSONObject enemyMsg = new JSONObject();

            enemyMsg.put("event", StateEventMatrix.CHANGE_STATE);
            enemyMsg.put("state", StateEventMatrix.ACTION);
            enemyMsg.put("name_of_enemy", name);

            player.sendMailToEnemy(enemyMsg.toString());
        }
    }
}
