public class REUSQLTUPLE
{
    private final double X;
    private final double Y;
    private final int RSS;

    public REUSQLTUPLE(double first, double second, int third)
    {
        X = first;
        Y = second;
        RSS = third;
    }

    public double getX() { return X; }
    public double getY() { return Y; }
    public int getRSS() { return RSS; }
}