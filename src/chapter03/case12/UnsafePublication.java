package chapter03.case12;

public class UnsafePublication {

    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

    public class Holder {

        int value;

        public Holder(int value) {
            this.value = value;
        }

    }

}
