package com.enigma.wmb_sb.model.enm;

public enum EnumTransactionType {
    DI("Dine In"),
    TA("Take Away");

    private String id;

    EnumTransactionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
