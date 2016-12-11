package gameElements;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Дмитрий on 12/10/2016.
 */
public class GameArea {
    GameRectangle gameRectangleArray[];

    String winnerOfGame;

    public GameArea() {
        winnerOfGame = "";

        gameRectangleArray = new GameRectangle[] {
                new GameRectangle(new String[] {
                        "h1", "v1", "v2", "h4"
                }),
                new GameRectangle(new String[] {
                        "h2", "v2", "v3", "h5"
                }),
                new GameRectangle(new String[] {
                        "h3", "v3", "v4", "h6"
                }),
                new GameRectangle(new String[] {
                        "h4", "v5", "v6", "h7"
                }),
                new GameRectangle(new String[] {
                        "h5", "v6", "v7", "h8"
                }),
                new GameRectangle(new String[] {
                        "h6", "v7", "v8", "h9"
                }),
                new GameRectangle(new String[] {
                        "h7", "v9", "v10", "h10"
                }),
                new GameRectangle(new String[] {
                        "h8", "v10", "v11", "h11"
                }),
                new GameRectangle(new String[] {
                        "h9", "v11", "v12", "h12"
                })
        };
    }

    public String getWinnerOfGame() {
        return winnerOfGame;
    }

    public void setWinnerOfGame(String winnerOfGame) {
        this.winnerOfGame = winnerOfGame;
    }

    public GameWinnerPair<Integer, Integer[]> markLineAsUsed(String id, String usedBy) {
        int marked = -1;
        Integer arrayOfWin[] = new Integer[9];

        Arrays.fill(arrayOfWin, 0);

        for (int i = 0; i < 9; i++) {
            if (gameRectangleArray[i].isBelongLine(id)) {
                if (!gameRectangleArray[i].isUsedLine(id)) {
                    if (gameRectangleArray[i].markLineAsUsed(id, usedBy)) {
                        arrayOfWin[i] = 1;
                    }

                    marked = 0;
                }
                else {
                    marked = -1;
                    return new GameWinnerPair<Integer, Integer[]>(marked, arrayOfWin);
                }
            }
        }

        return new GameWinnerPair<Integer, Integer[]>(marked, arrayOfWin);
    }
}
