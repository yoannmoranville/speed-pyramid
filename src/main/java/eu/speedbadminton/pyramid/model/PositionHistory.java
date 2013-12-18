package eu.speedbadminton.pyramid.model;

import java.util.Date;

/**
 * User: lukasgotter
 * Date: 18.12.13
 */
public class PositionHistory {

    private long position;
    private Date date;

    public PositionHistory(long position, Date date){
        this.position = position;
        this.date = date;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + ":"+position;
    }
}
