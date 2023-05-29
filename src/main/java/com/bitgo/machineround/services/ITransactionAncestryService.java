package com.bitgo.machineround.services;

import com.bitgo.machineround.models.Transaction;
import java.util.List;

public interface ITransactionAncestryService {

     void addTransaction(String transactionId, List<String> parentIds);
     int getDirectIndirectParentCount(Transaction transaction);
    List<Transaction> getTopTransactions(int n);

}
