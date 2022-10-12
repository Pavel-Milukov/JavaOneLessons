import java.util.List;

public class Poligon {

    private List<Double> coordinateList;
    private String color;
    private Texture texture;

    public Poligon (List<Double> coordinateList, String color, Texture texture) {
        this.coordinateList = coordinateList;
        this.color = color;
        this.texture = texture;
    }

    public void setColor (String color){
        this.color = color;
    }

    public void setCoordinateList (List<Double> coordinateList){
        this.coordinateList = coordinateList;
    }
}
