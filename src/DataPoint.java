
class DataPoint {
    private final double[] features;
    public final String className;
    public int clusterId;

    public DataPoint(double[] features, String className) {
        this.features = features;
        this.className = className;
        this.clusterId = -1;
    }

    public double[] getFeatures() {
        return features;
    }

    public double distance(DataPoint other) {
        double sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum += Math.pow(features[i] - other.features[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double distance(double[] point) {
        double sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum += Math.pow(features[i] - point[i], 2);
        }
        return Math.sqrt(sum);
    }
}
