package com.zalas.traffic.dynamic.network;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public interface NeuralNetwork {
    void create();

    void train();

    double getOutput(double[] inputs);

    void close();

    void save(String filePath) throws IOException;

    public static NeuralNetwork load(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            return (NeuralNetwork) in.readObject();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}

