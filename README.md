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


## Output
```
Top 10 Transactions:
Transaction 7d08f0c61cda9379bdf1fa68095f827199a0d4cb6b466a6da3f0dc956772c52b: 14
Transaction b2bab595112517e8b6a06aa9f616272b479e57e21b4da52877ddf385316aa19b: 13
Transaction d294be35db0b5fab4a6a00d6e4441c7e54be88fa02dfc188b75e4604ec6c3fcf: 12
Transaction 7841dc7cf61d394094f4341fa98d0a6fd771e95ac93a9dcfec12a23ed3c670c5: 12
Transaction 7a128b0242d89d327fc2c273199c7529a31477d8ea949e5176b2a4eb69b74464: 12
Transaction 4205c68c68266259c5723948e0407dff25600e6420659cef4286dd1ae4658b63: 12
Transaction 973e5adb05cc1cb80cabc5e451200333c993034153a078733ec06af7bf3c860b: 11
Transaction ef6c8e97b62eced1913df503667d49b9f5890cdb201be5d5d6c304af1d3f5db1: 11
Transaction afe4b90e667df0171f63e6cc95c0a12d24592d436dd2e8b9b2a9998b4099ff6d: 11
Transaction 4b4c90943c1651eabb8c5dfb6f490c4e56bd6cf42950a0430db17b9691b0236c: 11
```
