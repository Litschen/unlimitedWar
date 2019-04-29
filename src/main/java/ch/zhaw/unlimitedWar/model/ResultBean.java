package ch.zhaw.unlimitedWar.model;

import java.time.LocalDate;

public class ResultBean {
    private boolean outcome;
    private LocalDate date;

    public boolean getOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
