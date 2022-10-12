public class Camera {

    private double coordinateX;
    private double coordinateY;
    private double coordinateZ;
    private Angle3D angle;

    public Camera (double x, double y, double z, Angle3D angle) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.coordinateZ = z;
        this.angle = angle;
    }

    public void setCoordinates (double x, double y, double z){
        this.coordinateX = x;
        this.coordinateY = y;
        this.coordinateZ = z;
    }

    public void rotate ( Angle3D angle) {
        this.angle = angle;
    }
}
