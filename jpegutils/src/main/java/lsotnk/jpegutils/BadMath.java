package lsotnk.jpegutils;

public class BadMath {

    public static final BadMath INSTANCE = new BadMath();

    private final int bigEnoughInt = 16 * 1024;
    private final double bigEnoughFloor = this.bigEnoughInt;
    private final double bigEnoughRound = this.bigEnoughInt + 0.5;

    public BadMath() {
        setPrecision(12);
    }

    public final int fastFloor(final double x) {
        return (int) (x + this.bigEnoughFloor) - this.bigEnoughInt;
    }

    public final int fastRound(final double x) {
        return (int) (x + this.bigEnoughRound) - this.bigEnoughInt;
    }

    public final int fastCeil(final double x) {
        return this.bigEnoughInt - (int) (this.bigEnoughFloor - x);
    }

    public final double cos(final double rad) {
        return this.cos[(int) (rad * this.radToIndex) & this.sinMask];
    }

    public final double sin(final double rad) {
        return this.sin[(int) (rad * this.radToIndex) & this.sinMask];
    }

    public final double sinDeg(final double deg) {
        return this.sin[(int) (deg * this.degToIndex) & this.sinMask];
    }

    public final double cosDeg(final double deg) {
        return this.cos[(int) (deg * this.degToIndex) & this.sinMask];
    }

    private int sinBits, sinMask, sinCount;
    private double radFull, radToIndex;
    private double degFull, degToIndex;
    private double[] cos, sin;

    public void setPrecision(final int value) {
        this.sinBits = value;
        this.sinMask = ~(-1 << this.sinBits);
        this.sinCount = this.sinMask + 1;

        this.radFull = (Math.PI * 2.0);
        this.degFull = (360.0);
        this.radToIndex = this.sinCount / this.radFull;
        this.degToIndex = this.sinCount / this.degFull;

        this.sin = new double[this.sinCount];
        this.cos = new double[this.sinCount];

        for (int i = 0; i < this.sinCount; i++) {
            this.sin[i] = Math.sin(((i + 0.5d) / this.sinCount) * this.radFull);
            this.cos[i] = Math.cos(((i + 0.5d) / this.sinCount) * this.radFull);
        }

        for (int i = 0; i < 360; i += 90) {
            this.sin[(int) (i * this.degToIndex) & this.sinMask] = Math.sin((i * Math.PI) / 180.0);
            this.cos[(int) (i * this.degToIndex) & this.sinMask] = Math.cos((i * Math.PI) / 180.0);
        }
    }

}
