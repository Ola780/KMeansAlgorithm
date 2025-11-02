import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("iris.data"))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                //if (parts.length < 4) continue;
                double[] features = new double[2];
                features[0] = Double.parseDouble(parts[0]);
                features[1] = Double.parseDouble(parts[1]);
                String className = parts[4];
                dataPoints.add(new DataPoint(features, className));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int k = 3;
        KMeansAlgorithm kMeans = new KMeansAlgorithm(k, dataPoints);
        kMeans.initializeCentroids();
        kMeans.run(10);

        JFrame frame = new JFrame("K-Means Clustering ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new ClusterVisualization(dataPoints, kMeans.getCentroids()));
        frame.setVisible(true);
    }
}

