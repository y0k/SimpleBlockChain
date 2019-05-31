import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    
    public String transactionId; //hash of transaction
    public PublicKey sender; // sender adress/public key
    public PublicKey reciepient; // recipients adress/public key
    public float value;
    public byte[] signature; // prevent anybody else from spending funds in wallet
    
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    
    private static int sequence = 0; // rough count of how many transactions have been generated. 
    
    
    //constructor
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }
    //calculates the transaction hash \ Id
    private String calculateHash() {
        sequence++; //inscrease the sequence to avoid 2 identical transactions naving the same hash
        return StringUtil.applySha256(
                        StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence
                        );
    }
    //Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }
    //Returns true if new transaction could be created.
    public boolean processTransaction() {
        if(verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }
        //gather transaction inputs (Make sure they are unspent):
        for(TransactionInput i : inputs) {
            i.UTXO = QuBe.UTXOs.get(i.transactionOutputId);
        }
        //check if transaction is valid:
        if(getInputsValue() < QuBe.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }
        //generate transaction outputs:
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calculateHash();
        
    }
}
