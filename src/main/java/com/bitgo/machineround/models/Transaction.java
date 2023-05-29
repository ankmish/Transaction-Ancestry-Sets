package com.bitgo.machineround.models;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private String transactionId;
    private List<Transaction> parents;

    public Transaction(String transactionId) {
        this.transactionId = transactionId;
        this.parents = new ArrayList<>();
    }

    public List<Transaction> getParents() {
        return parents;
    }

    public void addParent(Transaction parent) {
        parents.add(parent);
    }
}