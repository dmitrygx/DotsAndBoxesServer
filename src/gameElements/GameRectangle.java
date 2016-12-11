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

    public boolean markLineAsUsed(String id, String usedBy) {
        boolean marked = false;
        int win = 0;

        for (int i = 0; i < 4; i++) {
            if ((0 == lines[i].getId().compareTo(id)) && (!lines[i].isUsedFlag())) {
                lines[i].markLineAsUsed(usedBy);
                marked = true;
                break;
            }
        }

        for (int i = 0; i < 4; i++) {
            win += (lines[i].isUsedFlag() ? 1 : 0);
        }

        if (win == 4) {
            setWinner(usedBy);
            return true;
        }
        else {
            return false;
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
