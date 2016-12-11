package Players;

import gameElements.GameArea;
import gameElements.GameWinnerPair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.omg.PortableInterceptor.Interceptor;

public class StateEventMatrix {

    public static final short INITIAL = 0;
    public static final short WAIT_FOR_GAME = 1;
    public static final short ACTION = 2;
    public static final short WAIT = 3;
    public static final short END = 4;

    public static final short CHANGE_STATE = 0;
    public static final short CONFIRMATION = 1;
    public static final short UPDATE = 2;
    public static final short MARK_AS_WIN = 3;
    public static final short ASSIGN_COLOR = 4;

    final short SET_NAME_EVENT = 0;
    final short PERFORM_ACTION = 1;

    private Player player;
    private short currentState;

    private State[] states;

    GameArea game;

    public StateEventMatrix(Player player) {

        game = new GameArea();
        this.player = player;
        states = new State[]{
                new InitialState(player),
                new WaitForGameState(player),
                new ActionState(player),
                new WaitState(player)
        };
        currentState = INITIAL;
    }

    public GameArea getGame() {
        return game;
    }

    public void handleEvent(String msg) {

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(msg);
            JSONObject jsonObj = (JSONObject) obj;
            long event = (Long)jsonObj.get("event");
            int intEvent = (int) event;
            Integer arrayOfWinRect[] = null;
            System.out.println("Event = " + event);
            switch (intEvent)
            {
                case SET_NAME_EVENT:
                {
                    states[getCurrentState()].setName((String)jsonObj.get("name"));

                    break;
                }
                case PERFORM_ACTION:
                {
                    String line = (String)jsonObj.get("line");

                    JSONObject confirmation = new JSONObject();

                    int result = getCurrentState() == ACTION ? 1 : 0;

                    if (result == 1) {
                        GameWinnerPair<Integer, Integer[]> pair = game.markLineAsUsed(line, player.getName());

                        if (pair.marked < 0) {
                            result = 0;

                        } else {
                            player.getEnemyPlayer().getMatrix().getGame().markLineAsUsed(line, player.getName());
                            arrayOfWinRect = pair.arrayOfWin;
                        }
                    }

                    confirmation.put("event", StateEventMatrix.CONFIRMATION);
                    confirmation.put("result", result);
                    confirmation.put("line", line);
                    confirmation.put("color", player.getColor());

                    player.sendMailToItself(confirmation.toString());

                    if (result == 1) {

                        JSONObject update = new JSONObject();
                        update.put("event", StateEventMatrix.UPDATE);
                        update.put("line", line);
                        update.put("color", player.getColor());

                        player.sendMailToEnemy(update.toString());

                        states[getCurrentState()].performAction(jsonObj.toString());
                    }

                    if (result == 1) {
                        if (arrayOfWinRect != null) {
                            for (int i = 0; i < 9; i++) {
                                if (arrayOfWinRect[i] == 1) {
                                    JSONObject markAsWin = new JSONObject();
                                    markAsWin.put("event", StateEventMatrix.MARK_AS_WIN);
                                    markAsWin.put("rect", "rect" + (i + 1));
                                    markAsWin.put("color", player.getColor());

                                    player.sendMailToItself(markAsWin.toString());
                                    player.sendMailToEnemy(markAsWin.toString());
                                }
                            }
                        }
                    }

                    if (result == 1) {
                        boolean deadHeat = false;
                        String gameWinner[] = game.defineWinnerOfGame();

                        if ((gameWinner[0].compareTo("") == 0) && (gameWinner[1].compareTo("") == 0)) {
                            break;
                        } else {
                            if (((gameWinner[0].compareTo(player.getName()) == 0) && (gameWinner[1].compareTo(player.getEnemyPlayer().getName()) == 0)) ||
                                    ((gameWinner[0].compareTo(player.getEnemyPlayer().getName()) == 0) && (gameWinner[1].compareTo(player.getName()) == 0))) {
                                deadHeat = true;
                            }

                            if (!deadHeat) {
                                if (gameWinner[0].compareTo(player.getName()) == 0) {
                                    states[getCurrentState()].defineWinner(player.getName(), player.getEnemyPlayer().getName(), deadHeat);
                                } else {
                                    states[getCurrentState()].defineWinner(player.getEnemyPlayer().getName(), player.getName(), deadHeat);
                                }
                            }
                        }

                    }

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public State[] getStates() {
        return states;
    }

    public short getCurrentState() {
        return currentState;
    }

    public void setCurrentState(short currentState) {
        this.currentState = currentState;
    }
}
