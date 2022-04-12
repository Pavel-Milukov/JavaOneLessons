public class Robot implements Actions {

    private String model;

    public Robot (String model) {
        this.model = model;
    }

    public String getModel () {
        return model;
    }

    @Override
    public int run() {
        return 600;
    }

    @Override
    public int jump() {
        return 3;
    }
}
