package com.zalas.traffic.dynamic.network;

public interface NeuralNetwork {
    void create();

    void train();

    double getOutput(double[] inputs);

    void close();
}

