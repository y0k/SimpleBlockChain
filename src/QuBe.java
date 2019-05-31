import com.google.gson.GsonBuilder;
import qube.Wallet;

import java.security.Security;
import  java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;

public class QuBe {
        
        public static ArrayList<Block> blockchain = new ArrayList<Block>();
        public static HashMap<String,TransactionOutputs> UTXOs = new HashMap<String, TransactionOutputs>(); //list of all unspent transactions
        public static int difficulty = 5;
        public static Wallet walletA;
        public static Wallet walletB;
    
        public static void main(String[] args) {
            //Setup Bouncey castle as a Security Provider
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            //create new wallets
            walletA = new Wallet();
            walletB = new Wallet();
            //test public and private keys
            System.out.println("Private and public keys:");
            System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
            System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
            //Create a test transaction from WalletA to walletB
            Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
            transaction.generateSignature(walletA.privateKey);
            //Verify the signature works and verify it from the public key
            System.out.println("Is signature verified");
            System.out.println(transaction.verifySignature());
//            Mining test
//            blockchain.add(new Block("First block", "0"));
//            System.out.println("Trying to Mine block 1...");
//            blockchain.get(0).mineBlock(difficulty);
//            
//            blockchain.add(new Block("Second block",blockchain.get(blockchain.size()-1).hash));
//            System.out.println("Trying to mine block 2...");
//            blockchain.get(1).mineBlock(difficulty);
//            
//            blockchain.add(new Block("Third block",blockchain.get(blockchain.size()-1).hash));
//            System.out.println("Trying to Mine block 3...");
//            blockchain.get(2).mineBlock(difficulty);
//            
//            System.out.println("\nBlockchain is Valid: " + isChainValid());
//            
//            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//            System.out.println("\nThe block chain: ");
//            System.out.println(blockchainJson);
//        }
//    public static Boolean isChainValid() {
//         Block currentBlock;
//         Block previousblock;
//         String hashTarget = new String(new char[difficulty]).replace('\0','0');
//        
//        for(int i=1; i < blockchain.size(); i++) {
//            currentBlock = blockchain.get(i);
//            previousblock = blockchain.get(i-1);
//            //compare registered and calculated hash
//            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
//                System.out.println("Current hashes not equal");
//                return false;
//            }
//            //comapre previous and registered previous hash
//            if (!previousblock.hash.equals(currentBlock.previousHash)) {
//                System.out.println("Previous hashes not equal");
//                return false;
//            }
//            //check if hash is solved
//            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
//                System.out.println("This block hasn't been mined");
//                return false;
//            }
//        }
//        return true;
    }
}
