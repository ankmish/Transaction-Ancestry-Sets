# Transaction Ancestry Sets

Every bitcoin transaction has inputs and outputs. When a transaction A uses an output of transaction B, B is a direct parent of A.
The transaction ancestry of A are all direct and indirect parents of A.



Write a program that:

Fetches all transactions for a block 680000
Determines the ancestor set for every transaction in the block (all ancestors need to be in the block as well)
Prints the 10 transaction with the largest ancestry sets.


Output format: txid and ancestry set size.

Use the Esplora HTTP API from blockstream to retrieve the data: https://github.com/Blockstream/esplora/blob/master/API.md

Hint: cache the API responses locally to avoid hitting rate limits.

## How to run the code
1. Clone the repo
2. Make sure you have java-17 installed. Setup usage: Maven to build the project
3. Run the `WorkFlowTest.java` file to validate the output.


## Key Note
1. To avoid network calls, all 2875 transactions are stored in local file in a block of 25. [inside `main > resources > static`]
2. All these blocks are merged into single output file `merged.json`
