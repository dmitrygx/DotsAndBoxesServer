package gameElements;

/**
 * Created by Дмитрий on 12/10/2016.
 */
public class GameRectangle {

    GameLine lines[];

    String winner;

    public GameRectangle(String lineIds[]) {
        lines = new GameLine[] {
                new GameLine(lineIds[0]),
                new GameLine(lineIds[1]),
                new GameLine(lineIds[2]),
                new GameLine(lineIds[3])
        };

        winner = "";
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void markLineAsUsed(String id, String usedBy) {
        for (int i = 0; i < 4; i++) {
            if ((0 == lines[i].getId().compareTo(id)) && (!lines[i].isUsedFlag())) {
                lines[i].markLineAsUsed(usedBy);
            }
        }
    }

    public boolean isBelongLine(String id) {
        for (int i = 0; i < 4; i++) {
            if (0 == lines[i].getId().compareTo(id)) {
                return true;
            }
        }
        return false;
    }

    public  boolean isUsedLine(String id) {
        for (int i = 0; i < 4; i++) {
            if (0 == lines[i].getId().compareTo(id)) {
                return lines[i].isUsedFlag();
            }
        }
        return false;
    }
}
