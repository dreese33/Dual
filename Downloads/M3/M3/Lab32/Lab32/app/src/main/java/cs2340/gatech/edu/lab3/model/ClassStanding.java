package cs2340.gatech.edu.lab3.model;

/**
 * Created by Dylan Reese on 2/5/18.
 */

public enum ClassStanding {
    FRESHMAN ("FR"), SOPHOMORE ("SO"), JUNIOR ("JR"), SENIOR ("SR");

    private String value;

    private ClassStanding(String value) {
        this.value = value;
    }
}
