package gameElements;

/**
 * Created by Дмитрий on 12/10/2016.
 */
public class GameLine {

    private String id;

    private boolean usedFlag;

    private String usedBy;

    public GameLine(String lineId) {
        this.usedFlag = false;
        this.id = lineId;
        usedBy = "";
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public String getId() {
        return id;
    }

    public boolean isUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(boolean usedFlag) {
        this.usedFlag = usedFlag;
    }

    public void markLineAsUsed(String usedBy) {
        setUsedFlag(true);
        setUsedBy(usedBy);
    }
}
