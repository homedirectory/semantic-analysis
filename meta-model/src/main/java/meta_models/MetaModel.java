package main.java.meta_models;

public class MetaModel {
    private String context;
    
    public MetaModel(String context) {
        this.context = context;
    }

    protected final String joinContext(String prop) {
        if (this.context.length() > 0) {
            return this.context + "." + prop;
        }
        return prop;
    }
    
    public final String toString() {
        return this.context;
    }
}
