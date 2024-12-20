package org.web3j.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.11.0.
 */
@SuppressWarnings("rawtypes")
public class Transactions extends Contract {
    public static final String BINARY = "60a0604052348015600e575f80fd5b5060405161084d38038061084d833981016040819052602b91603f565b60015f556001600160a01b0316608052606a565b5f60208284031215604e575f80fd5b81516001600160a01b03811681146063575f80fd5b9392505050565b6080516107af61009e5f395f8181610181015281816102b8015281816103680152818161049a015261054a01526107af5ff3fe608060405234801561000f575f80fd5b5060043610610060575f3560e01c806335ab842f146100645780636b57160d1461009e5780636de152b5146100c65780637c22f7f9146100db57806388b7e8b4146100ee5780638eb5534d14610116575b5f80fd5b61008c61007236600461066b565b6001600160a01b03165f9081526001602052604090205490565b60405190815260200160405180910390f35b61008c6100ac36600461066b565b6001600160a01b03165f9081526002602052604090205490565b6100d96100d436600461068b565b610129565b005b6100d96100e93660046106b3565b610259565b61008c6100fc36600461066b565b6001600160a01b03165f9081526003602052604090205490565b6100d96101243660046106dd565b61043b565b80805f0361014a576040516341e37bad60e01b815260040160405180910390fd5b610152610628565b335f908152600260205260408120805484929061017090849061072a565b909155505f90506001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000166340c10f19856101b9670de0b6b3a764000087610743565b6040516001600160e01b031960e085901b1681526001600160a01b03909216600483015260248201526044016020604051808303815f875af1158015610201573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610225919061075a565b905060018115151461024a57604051630258026960e61b815260040160405180910390fd5b5061025460015f55565b505050565b610261610628565b81805f03610282576040516341e37bad60e01b815260040160405180910390fd5b6001600160a01b0382165f90815260016020526040812080548592906102a990849061072a565b90915550506001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001663095ea7b3336102f0670de0b6b3a764000087610743565b6040516001600160e01b031960e085901b1681526001600160a01b03909216600483015260248201526044016020604051808303815f875af1158015610338573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061035c919061075a565b505f6001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016639dc29fac846103a0670de0b6b3a764000088610743565b6040516001600160e01b031960e085901b1681526001600160a01b03909216600483015260248201526044016020604051808303815f875af11580156103e8573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061040c919061075a565b90508061042c5760405163fdbf429f60e01b815260040160405180910390fd5b505061043760015f55565b5050565b82805f0361045c576040516341e37bad60e01b815260040160405180910390fd5b610464610628565b6001600160a01b0383165f908152600360205260408120805486929061048b90849061072a565b90915550506001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001663095ea7b3336104d2670de0b6b3a764000088610743565b6040516001600160e01b031960e085901b1681526001600160a01b03909216600483015260248201526044016020604051808303815f875af115801561051a573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061053e919061075a565b505f6001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000166323b872dd8585610583670de0b6b3a76400008a610743565b6040516001600160e01b031960e086901b1681526001600160a01b03938416600482015292909116602483015260448201526064016020604051808303815f875af11580156105d4573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105f8919061075a565b9050806106185760405163fdbf429f60e01b815260040160405180910390fd5b5061062260015f55565b50505050565b60025f540361064a57604051633ee5aeb560e01b815260040160405180910390fd5b60025f55565b80356001600160a01b0381168114610666575f80fd5b919050565b5f6020828403121561067b575f80fd5b61068482610650565b9392505050565b5f806040838503121561069c575f80fd5b6106a583610650565b946020939093013593505050565b5f80604083850312156106c4575f80fd5b823591506106d460208401610650565b90509250929050565b5f805f606084860312156106ef575f80fd5b833592506106ff60208501610650565b915061070d60408501610650565b90509250925092565b634e487b7160e01b5f52601160045260245ffd5b8082018082111561073d5761073d610716565b92915050565b808202811582820484141761073d5761073d610716565b5f6020828403121561076a575f80fd5b81518015158114610684575f80fdfea26469706673582212202478e9b56ed2ed70e78c4d4fa90be59fc10c15097c1482bbda1b4f94c6cc8d6864736f6c634300081a0033";

    public static final String FUNC_BURNDSC = "burnDsc";

    public static final String FUNC_GETTOTALTOKENSBURNT = "getTotalTokensBurnt";

    public static final String FUNC_GETTOTALTOKENSMINTEDTOUSER = "getTotalTokensMintedToUser";

    public static final String FUNC_GETTOTALTOKENSTRANSFERREDBYUSER = "getTotalTokensTransferredByUser";

    public static final String FUNC_MINTDSC = "mintDsc";

    public static final String FUNC_TRANSFERFROMUSER1TOUSER2 = "transferFromUser1ToUser2";

    @Deprecated
    protected Transactions(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Transactions(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Transactions(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Transactions(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> burnDsc(BigInteger amount, String from) {
        final Function function = new Function(
                FUNC_BURNDSC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getTotalTokensBurnt(String user) {
        final Function function = new Function(FUNC_GETTOTALTOKENSBURNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalTokensMintedToUser(String user) {
        final Function function = new Function(FUNC_GETTOTALTOKENSMINTEDTOUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalTokensTransferredByUser(String user) {
        final Function function = new Function(FUNC_GETTOTALTOKENSTRANSFERREDBYUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintDsc(String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_MINTDSC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFromUser1ToUser2(BigInteger amount, String from, String to) {
        final Function function = new Function(
                FUNC_TRANSFERFROMUSER1TOUSER2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Transactions load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transactions(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Transactions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transactions(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Transactions load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Transactions(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Transactions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Transactions(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Transactions> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String dscAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, dscAddress)));
        return deployRemoteCall(Transactions.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Transactions> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String dscAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, dscAddress)));
        return deployRemoteCall(Transactions.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Transactions> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String dscAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, dscAddress)));
        return deployRemoteCall(Transactions.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Transactions> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String dscAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, dscAddress)));
        return deployRemoteCall(Transactions.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
