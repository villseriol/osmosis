package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

public class TrimTransformer implements Transformer {
    private static TrimTransformer instance;

    private TrimTransformer() {
        super();
    }


    @Override
    public String transform(String input) {
        return input.trim();
    }


    public static Transformer getInstance() {
        if (instance == null) {
            instance = new TrimTransformer();
        }

        return instance;
    }
}
