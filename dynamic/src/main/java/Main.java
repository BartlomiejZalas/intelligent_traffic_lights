public class Main {

    public static void main(String[] args) throws Exception {
        DataSet dataSet = new DataSet();
        NeuralNetwork neuralNetwork = new NeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        double[] testInputs = {0.5, 0.75, 0.5, 1};
        double result = neuralNetwork.getOutput(testInputs);
        neuralNetwork.close();

        System.out.println("Output: "+ result + ", Expected: "+0.2);

        new ReportPrinter(dataSet, testInputs, result).createReport();

        System.out.println("Report saved");
    }
}
