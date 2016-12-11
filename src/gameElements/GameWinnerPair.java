package gameElements;

public class GameWinnerPair<T, U> {
    public final T marked;
    public final U arrayOfWin;

    public GameWinnerPair(T marked, U arrayOfWin) {
        this.marked = marked;
        this.arrayOfWin = arrayOfWin;
    }
}
