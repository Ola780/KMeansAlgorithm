import java.util.*;

class KMeansAlgorithm {
    private final int k;
    private final ArrayList<DataPoint> dataPoints;
    private final double[][] centroids;

    public KMeansAlgorithm(int k, ArrayList<DataPoint> dataPoints) {
        this.k = k;
        this.dataPoints = dataPoints;
        this.centroids = new double[k][2];
    }

    public void initializeCentroids() {
        List<Integer> indeksy = new ArrayList<>();
        for (int i = 0; i < dataPoints.size(); i++) {
            indeksy.add(i);
        }

        Collections.shuffle(indeksy);

        for (int i = 0; i < k; i++) {
            double[] features = dataPoints.get(indeksy.get(i)).getFeatures();
            centroids[i][0] = features[0];
            centroids[i][1] = features[1];
        }
    }


    public void run(int maxIterations) {
        for (int i = 0; i < maxIterations; i++) {
            assignClusters();
            boolean changed = updateCentroids();

            if (!changed) {
                System.out.println("Centroidy się nie zmieniły – zakończono po " + (i + 1) + " iteracjach.");
                break;
            }
        }
    }


    public void assignClusters() {
        for (DataPoint point : dataPoints) {
            point.clusterId = getClosestCentroidIndex(point);
        }
    }

    private int getClosestCentroidIndex(DataPoint point) {
        int najblizszy = 0;
        double minDistance = point.distance(centroids[0]);

        for (int i = 1; i < k; i++) {
            double odleglosc = point.distance(centroids[i]);
            if (odleglosc < minDistance) {
                minDistance = odleglosc;
                najblizszy = i;
            }
        }

        return najblizszy;
    }


    public boolean updateCentroids() {
        double[][] sumaWspółwKlastrze = new double[k][2];
        int[] counts = new int[k];

        for (DataPoint point : dataPoints) {
            int klaster = point.clusterId;
            double[] features = point.getFeatures();
            sumaWspółwKlastrze[klaster][0] += features[0];
            sumaWspółwKlastrze[klaster][1] += features[1];
            counts[klaster]++;
        }

        boolean changed = false;

        for (int i = 0; i < k; i++) {
            if (counts[i] > 0) {
                double newX = sumaWspółwKlastrze[i][0] / counts[i];
                double newY = sumaWspółwKlastrze[i][1] / counts[i];

                if (centroids[i][0] != newX || centroids[i][1] != newY) {
                    changed = true;
                }

                centroids[i][0] = newX;
                centroids[i][1] = newY;
            }
        }

        return changed;
    }



    public double[][] getCentroids() {
        return centroids;
    }
}
