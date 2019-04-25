package ch.zhaw.unlimitedWar.model;

import java.util.Date;

public class ResultBean {
    private boolean outcome;
    private Date date;

    public boolean getOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
