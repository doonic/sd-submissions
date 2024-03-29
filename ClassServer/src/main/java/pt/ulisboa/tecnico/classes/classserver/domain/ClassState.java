package pt.ulisboa.tecnico.classes.classserver.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class ClassState {

    private int capacity;

    private int currentCapacity;

    private boolean openEnrollments;

    private List<Student> enrolled;

    private List<Student> discarded;

    private ConcurrentHashMap<String,Student> registered;


    public ClassState() {
        enrolled = new ArrayList<>();
        discarded = new ArrayList<>();
        registered = new ConcurrentHashMap<>();

    }

    public synchronized int getCapacity() {
        return capacity;

    }

    public synchronized boolean getOpenEnrollments() {
        return openEnrollments;

    }

    public synchronized void addEnroll(Student student) {
        enrolled.add(student);

    }

    public synchronized void addDiscard(Student student) {
        discarded.add(student);

    }


    public synchronized void removeEnroll(Student student) {
        enrolled.remove(student);

    }

    public synchronized void removeDiscard(Student student) {
        discarded.remove(student);

    }

    public synchronized List<Student> getDiscarded() {
        return discarded;

    }

    public synchronized List<Student> getEnrolled() {
        return enrolled;

    }

    public synchronized void setCapacity(int capacity) {
        this.capacity = capacity;

    }

    public synchronized void setOpenEnrollments(boolean status)  {
        this.openEnrollments = status;

    }

    public void setEnrolled(List<Student> enrolled) {
        this.enrolled = enrolled;
    }

    public void setDiscarded(List<Student> discarded) {
        this.discarded = discarded;
    }

    public void addToRegistry(String studentId, Student student) {
        this.registered.put(studentId, student);

    }

    public ConcurrentHashMap<String, Student> getRegistered() {
        return registered;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void upEnrolled() {
        currentCapacity += 1;

    }

    public void downEnrolled() {
        if(currentCapacity == 0) {
            currentCapacity = 0;
        }
        currentCapacity -= 1;

    }

    public boolean checkForFullCapacity() {
        return currentCapacity >= getCapacity();

    }

    public void setCurrentCapacity(int value) {
        currentCapacity = value;
    }

}



