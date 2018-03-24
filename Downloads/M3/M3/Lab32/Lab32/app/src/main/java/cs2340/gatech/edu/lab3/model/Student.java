package cs2340.gatech.edu.lab3.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by robertwaters on 1/5/17.
 *
 * Information Holder - represents a single student in model
 *
 * We are passing this object in a bundle between intents, so we implement
 * the Parcelable interface.
 *
 */

public class Student implements Parcelable {

    /** a demonstration of using something other than an enum for holding choices */
    public static List<String> legalMajors = Arrays.asList("CS", "CM", "ISYE", "MATH", "EE", "CMPE", "NA");

    /** allow us to assign unique id numbers to each student */
    private static int Next_Id = 0;

    /** this students id number */
    private int _id;

    /** this students name */
    private String _name;

    /** this students major */
    private String _major;

    /** enum of class standings **/
    private ClassStanding _classStanding;

    /* **********************
     * Getters and setters
     */
    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    //no setter for this.  id is a read only field
    public int getId() { return _id; }

    public String getMajor() {return _major; }
    public void setMajor(String major) { _major = major; }

    public ClassStanding getClassStanding() { return _classStanding; }
    public void setClassStanding(ClassStanding classStanding) { _classStanding = classStanding; }

    /**
     * Lookup a major based on its code.  Returns the postion of that
     * major in the array
     *
     * @param code the major to find
     *
     * @return the index of the array that corresponds to the submitted major
     */
    public static int findPosition(String code) {
        int i = 0;
        while (i < legalMajors.size()) {
            if (code.equals(legalMajors.get(i))) return i;
            ++i;
        }
        return 0;
    }


    /**
     * Make a new student
     * @param name      the student's name
     * @param major     the student's major
     */
    public Student(String name, String major) {
        this(name, major, ClassStanding.FRESHMAN);
    }

    /**
     * @param name             the student's name
     * @param major            the student's major
     * @param classStanding    the student's class standing
     */
    public Student(String name, String major, ClassStanding classStanding) {
        _name = name;
        _major = major;
        _classStanding = classStanding;
        _id = Student.Next_Id++;
    }


    /**
     * No param constructor -- DO NOT CALL NORMALLY
     * This constructor only for GUI use in edit/new student dialog
     */
    public Student() {
        this("enter new name" , "NA");
    }

    /**
     *
     * @return the display string representation
     */
    @Override
    public String toString() {
        return _name + " " + _major + " " + _classStanding;
    }


    /* *********************************
     * These methods are required by the Parcelable interface
     * I just wanted to demo how to pass information from one activity
     * to another through an intent.   If this were a real project, I
     * would probably have the facade maintain information about the
     * currently selected student which would remove the need to
     * pass the student object in an intent, which would remove the need
     * to implement the Parcelable interface and these methods.
     */

    /**
     * Constructor used by Parcel to make a new student out of the
     * parceled information
     *
     * @param in  the parcel containing the student information
     */
    private Student(Parcel in) {
        _name = in.readString();
        _major = in.readString();
        _id = in.readInt();
        _classStanding = (ClassStanding) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /* *************************
       If you add new instance vars to Student, you will need to add them to the write
       Be sure the order you write information matches the order that the constructor above
       reads them.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(_name);
         dest.writeString(_major);
         dest.writeInt(_id);
         dest.writeSerializable(_classStanding);
    }

    /**
     * Should not have to edit this method if the constructor and write method are
     * working correctly.
     */
    public static final Parcelable.Creator<Student> CREATOR
            = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
