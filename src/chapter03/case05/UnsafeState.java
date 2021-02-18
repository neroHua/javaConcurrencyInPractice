package chapter03.case05;

public class UnsafeState {

    private String[] state = new String[] {"a1", "a2", "b1", "b2"};

    public String[] getState() {
       return this.state;
    }

}
