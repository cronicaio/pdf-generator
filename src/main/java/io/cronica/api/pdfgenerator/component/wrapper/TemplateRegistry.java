package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.0.
 */
public class TemplateRegistry extends Contract {
    private static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610aad806100536000396000f3fe608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806331543cf41461007d5780638da5cb5b1461019d578063bc525652146101f4578063f2fde38b14610314578063f3fe1cd714610365578063f61a0d9214610440575b600080fd5b34801561008957600080fd5b506100b6600480360360208110156100a057600080fd5b810190808035906020019092919050505061046b565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156100fa5780820151818401526020810190506100df565b50505050905090810190601f1680156101275780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610160578082015181840152602081019050610145565b50505050905090810190601f16801561018d5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156101a957600080fd5b506101b26105e9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561020057600080fd5b5061022d6004803603602081101561021757600080fd5b810190808035906020019092919050505061060e565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b83811015610271578082015181840152602081019050610256565b50505050905090810190601f16801561029e5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156102d75780820151818401526020810190506102bc565b50505050905090810190601f1680156103045780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561032057600080fd5b506103636004803603602081101561033757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610771565b005b34801561037157600080fd5b5061043e6004803603604081101561038857600080fd5b81019080803590602001906401000000008111156103a557600080fd5b8201836020820111156103b757600080fd5b803590602001918460018302840111640100000000831117156103d957600080fd5b9091929391929390803590602001906401000000008111156103fa57600080fd5b82018360208201111561040c57600080fd5b8035906020019184600183028401116401000000008311171561042e57600080fd5b9091929391929390505050610846565b005b34801561044c57600080fd5b506104556109b5565b6040518082815260200191505060405180910390f35b60608060018381548110151561047d57fe5b90600052602060002090600202016000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105225780601f106104f757610100808354040283529160200191610522565b820191906000526020600020905b81548152906001019060200180831161050557829003601f168201915b5050505050915060018381548110151561053857fe5b90600052602060002090600202016001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105dd5780601f106105b2576101008083540402835291602001916105dd565b820191906000526020600020905b8154815290600101906020018083116105c057829003601f168201915b50505050509050915091565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60018181548110151561061d57fe5b9060005260206000209060020201600091509050806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106c95780601f1061069e576101008083540402835291602001916106c9565b820191906000526020600020905b8154815290600101906020018083116106ac57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107675780601f1061073c57610100808354040283529160200191610767565b820191906000526020600020905b81548152906001019060200180831161074a57829003601f168201915b5050505050905082565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156107cc57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151561084357806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156108a157600080fd5b6108a96109c2565b84848080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050816000018190525082828080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050816020018190525060018190806001815401808255809150509060018203906000526020600020906002020160009091929091909150600082015181600001908051906020019061098d9291906109dc565b5060208201518160010190805190602001906109aa9291906109dc565b505050505050505050565b6000600180549050905090565b604080519081016040528060608152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a1d57805160ff1916838001178555610a4b565b82800160010185558215610a4b579182015b82811115610a4a578251825591602001919060010190610a2f565b5b509050610a589190610a5c565b5090565b610a7e91905b80821115610a7a576000816000905550600101610a62565b5090565b9056fea165627a7a7230582006f776b3d9c755269188b010cd7b0e80b4ea07ae519525de1ab5a785f378b5b10029";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TEMPLATES = "templates";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_ADDTEMPLATE = "addTemplate";

    public static final String FUNC_GETTEMPLATE = "getTemplate";

    public static final String FUNC_GETCOUNTOFTEMPLATES = "getCountOfTemplates";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected TemplateRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TemplateRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TemplateRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TemplateRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Tuple2<Utf8String, Utf8String>> templates(Uint256 param0) {
        final Function function = new Function(FUNC_TEMPLATES, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<Utf8String, Utf8String>>(
                new Callable<Tuple2<Utf8String, Utf8String>>() {
                    @Override
                    public Tuple2<Utf8String, Utf8String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Utf8String, Utf8String>(
                                (Utf8String) results.get(0), 
                                (Utf8String) results.get(1));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transferOwnership(Address newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addTemplate(Utf8String _contractAddress, Utf8String _name) {
        final Function function = new Function(
                FUNC_ADDTEMPLATE, 
                Arrays.<Type>asList(_contractAddress, _name), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<Utf8String, Utf8String>> getTemplate(Uint256 _index) {
        final Function function = new Function(FUNC_GETTEMPLATE, 
                Arrays.<Type>asList(_index), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<Utf8String, Utf8String>>(
                new Callable<Tuple2<Utf8String, Utf8String>>() {
                    @Override
                    public Tuple2<Utf8String, Utf8String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Utf8String, Utf8String>(
                                (Utf8String) results.get(0), 
                                (Utf8String) results.get(1));
                    }
                });
    }

    public RemoteCall<Uint256> getCountOfTemplates() {
        final Function function = new Function(FUNC_GETCOUNTOFTEMPLATES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static TemplateRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemplateRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TemplateRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemplateRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TemplateRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TemplateRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TemplateRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TemplateRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TemplateRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemplateRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemplateRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemplateRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TemplateRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemplateRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemplateRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemplateRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getBinary() {
        return BINARY;
    }
}
