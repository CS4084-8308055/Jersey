package ie.ul.davidbeck.jersey;

public class Jersey {

    private String mName;
    private int mNumber;
    private boolean mHomeJersey;

    public Jersey() {
        setJerseyName("ANDROID");
        setJerseyNumber(17);
        setHomeJersey(true);
    }

    public Jersey(String mNewName, int mNewNumber, boolean mNewJersey){
        setJerseyName(mNewName);
        setJerseyNumber(mNewNumber);
        setHomeJersey(mNewJersey);
    }

    public String getJerseyName() {
        return mName;
    }

    public void setJerseyName(String mName) {
        this.mName = mName;
    }

    public int getJerseyNumber() {
        return mNumber;
    }

    public void setJerseyNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public boolean isHomeJersey() {
        return mHomeJersey;
    }

    public void setHomeJersey(boolean mHomeJersey) {
        this.mHomeJersey = mHomeJersey;
    }
}
