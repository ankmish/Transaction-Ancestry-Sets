package com.bitgo.machineround;

import com.bitgo.machineround.models.Transaction;
import com.bitgo.machineround.services.ITransactionAncestryService;
import com.bitgo.machineround.services.impl.TransactionAncestryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.management.DescriptorKey;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

public class WorkFlowTest {

    @Test
    @Description("Run the test case")
    public void testFlow() {
        TransactionAncestryService ancestryService = new TransactionAncestryService();
        // merge all blocks
        ancestryService.mergeJsonFiles();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/static/merged.json");
            JsonNode jsonArray = objectMapper.readTree(file);

            if (jsonArray.isArray()) {
                for (JsonNode jsonNode : jsonArray) {
                    String transactionId = jsonNode.get("txid").asText();
                    List<String> parents = new ArrayList<>();
                    JsonNode vin = jsonNode.get("vin");
                    for (JsonNode in : vin) {
                        parents.add(in.get("txid").asText());
                    }
                    ancestryService.addTransaction(transactionId, parents);
                }
            } else {
                System.out.println("The root node is not a JSON array.");
            }
        } catch (IOException e) {
            System.out.println("Failed to read JSON array file.");
            e.printStackTrace();
        }

        // Find count of direct + indirect parents of all transactions
        for (Transaction transaction : ancestryService.transactions.values()) {
            int parentCount = ancestryService.getDirectIndirectParentCount(transaction);
            System.out.println("Transaction " + transaction.getTransactionId() + ": " + parentCount);
        }

        // Print top 10 transactions with the highest direct plus indirect parent count
        List<Transaction> topTransactions = ancestryService.getTopTransactions(10);
        System.out.println("Top 10 Transactions....");
        for (Transaction transaction : topTransactions) {
            int parentCount = ancestryService.getDirectIndirectParentCount(transaction);
            System.out.println("Transaction " + transaction.getTransactionId() + ": " + parentCount);
        }
    }
}
