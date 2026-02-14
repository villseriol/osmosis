package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.util.ArrayList;
import java.util.List;


public class TransformerUtil {
    private static TransformerUtil instance;

    private List<Transformer> inputTransformers = new ArrayList<>();
    private List<Transformer> outputTransformers = new ArrayList<>();

    private TransformerUtil() {
        super();
    }


    public static TransformerUtil getInstance() {
        if (instance == null) {
            instance = new TransformerUtil();
        }

        return instance;
    }


    public Transformer getComposedOutputTransformer() {
        return outputTransformers.stream().reduce(s -> s, (a, b) -> s -> b.transform(a.transform(s)));
    }


    public Transformer getComposedInputTransformer() {
        return inputTransformers.stream().reduce(s -> s, (a, b) -> s -> b.transform(a.transform(s)));
    }


    public void registerOutputTransformer(Transformer transformer) {
        if (transformer != null) {
            outputTransformers.add(transformer);
        }
    }


    public void registerInputTransformer(Transformer transformer) {
        if (transformer != null) {
            inputTransformers.add(transformer);
        }
    }
}
