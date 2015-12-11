package com.zalas.traffic.dynamic.network;

import com.zalas.traffic.dynamic.data.DataSet;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class EncogNeuralNetwork implements NeuralNetwork {

    private static final int ITERATION_LIMIT = 1000;
    private DataSet dataSet;
    private BasicNetwork network;

    public EncogNeuralNetwork(DataSet dataSet) {

        this.dataSet = dataSet;
    }

    public void create() {
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, false, 4));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 4));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 2));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        network.getStructure().finalizeStructure();
        network.reset();
    }

    public void train() {
        MLDataSet trainingSet = new BasicMLDataSet(dataSet.getInputsAsNormalizedArray(), dataSet.getOutputAsNormalizedArray());
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;

        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
            if (epoch > ITERATION_LIMIT) break;
        } while (train.getError() > 0.001);
        train.finishTraining();
    }

    public double getOutput(double[] inputs) {
        MLDataPair pair = new BasicMLDataPair(new BasicMLData(inputs));
        final MLData output = network.compute(pair.getInput());
        return output.getData(0);
    }

    public void close() {
        Encog.getInstance().shutdown();
    }
}