package com.bitgo.machineround.services.impl;

import com.bitgo.machineround.models.Transaction;
import com.bitgo.machineround.services.ITransactionAncestryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TransactionAncestryService implements ITransactionAncestryService {


    public Map<String, Transaction> transactions;
    public TransactionAncestryService() {
        this.transactions = new HashMap<>();
    }

    public void addTransaction(String transactionId, List<String> parentIds) {
        Transaction transaction = transactions.getOrDefault(transactionId, new Transaction(transactionId));
        transactions.put(transactionId, transaction);

        for (String parentId : parentIds) {
                Transaction parent = transactions.getOrDefault(parentId, new Transaction(parentId));
               // transactions.put(parentId, parent);
                transaction.addParent(parent);
        }
    }

    public int getDirectIndirectParentCount(Transaction transaction) {
        Queue<Transaction> queue = new LinkedList<>();
        Set<Transaction> visited = new HashSet<>();
        queue.offer(transaction);
        visited.add(transaction);
        int count = 0;
        while (!queue.isEmpty()) {
            Transaction current = queue.poll();
            count++;
            for (Transaction parent : current.getParents()) {
                if(transactions.containsKey(parent.getTransactionId())) {
                    if (!visited.contains(parent)) {
                        queue.offer(parent);
                        visited.add(parent);
                    }
                }
            }
        }
        return count - 1;
    }

    public List<Transaction> getTopTransactions(int n) {
        List<Transaction> allTransactions = new ArrayList<>(transactions.values());
        allTransactions.sort(Comparator.comparingInt(this::getDirectIndirectParentCount).reversed());

        return allTransactions.subList(0, Math.min(n, allTransactions.size()));
    }

    public void mergeJsonFiles() {
        ObjectMapper objectMapper = new ObjectMapper();

        // List of JSON array files to merge
        List<String> jsonFiles = new ArrayList<>();
        for(int i=25;i<=2825;i+=25) {
            String path = "src/main/resources/static/response" + i + ".txt";
            jsonFiles.add(path);
        }

        // Create an empty array node to hold the merged data
        ArrayNode mergedArray = objectMapper.createArrayNode();

        for (String jsonFile : jsonFiles) {
            try {
                // Read each JSON file
                File file = new File(jsonFile);
                ArrayNode arrayNode = (ArrayNode) objectMapper.readTree(file);

                // Merge the array node into the merged array
                mergedArray.addAll(arrayNode);
            } catch (IOException e) {
                System.out.println("Failed to read file: " + jsonFile);
                e.printStackTrace();
            }
        }

        try {
            // Write the merged array to a new JSON file
            objectMapper.writeValue(new File("src/main/resources/static/merged.json"), mergedArray);
            System.out.println("Merged JSON array file created: merged.json");
        } catch (IOException e) {
            System.out.println("Failed to write merged JSON array file.");
            e.printStackTrace();
        }
    }

}
