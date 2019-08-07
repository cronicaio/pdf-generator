package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple8;
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
public class StructuredDoc extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600960006101000a81548160ff0219169083600381111561007057fe5b02179055506112f6806100846000396000f3fe6080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630f1917d1146100bf5780631a40ac3714610145578063200d2ed2146101d55780632105401d1461020e5780632f91c898146103db5780633bc5de301461046157806381497ac5146106b35780638a330a40146107675780638da5cb5b14610796578063b6549f75146107ed578063e3f6618d14610804578063f2fde38b1461082f575b600080fd5b3480156100cb57600080fd5b50610143600480360360208110156100e257600080fd5b81019080803590602001906401000000008111156100ff57600080fd5b82018360208201111561011157600080fd5b8035906020019184600183028401116401000000008311171561013357600080fd5b9091929391929390505050610880565b005b34801561015157600080fd5b5061015a610955565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561019a57808201518184015260208101905061017f565b50505050905090810190601f1680156101c75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101e157600080fd5b506101ea6109f3565b604051808260038111156101fa57fe5b60ff16815260200191505060405180910390f35b34801561021a57600080fd5b506103d9600480360360e081101561023157600080fd5b810190808035906020019064010000000081111561024e57600080fd5b82018360208201111561026057600080fd5b8035906020019184600183028401116401000000008311171561028257600080fd5b9091929391929390803590602001906401000000008111156102a357600080fd5b8201836020820111156102b557600080fd5b803590602001918460018302840111640100000000831117156102d757600080fd5b9091929391929390803590602001906401000000008111156102f857600080fd5b82018360208201111561030a57600080fd5b8035906020019184600183028401116401000000008311171561032c57600080fd5b90919293919293908035906020019064010000000081111561034d57600080fd5b82018360208201111561035f57600080fd5b8035906020019184600183028401116401000000008311171561038157600080fd5b9091929391929390803567ffffffffffffffff169060200190929190803567ffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610a06565b005b3480156103e757600080fd5b5061045f600480360360208110156103fe57600080fd5b810190808035906020019064010000000081111561041b57600080fd5b82018360208201111561042d57600080fd5b8035906020019184600183028401116401000000008311171561044f57600080fd5b9091929391929390505050610be8565b005b34801561046d57600080fd5b50610476610cb1565b604051808060200180602001806020018967ffffffffffffffff1667ffffffffffffffff1681526020018867ffffffffffffffff1667ffffffffffffffff168152602001806020018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018660038111156104fc57fe5b60ff16815260200185810385528d818151815260200191508051906020019080838360005b8381101561053c578082015181840152602081019050610521565b50505050905090810190601f1680156105695780820380516001836020036101000a031916815260200191505b5085810384528c818151815260200191508051906020019080838360005b838110156105a2578082015181840152602081019050610587565b50505050905090810190601f1680156105cf5780820380516001836020036101000a031916815260200191505b5085810383528b818151815260200191508051906020019080838360005b838110156106085780820151818401526020810190506105ed565b50505050905090810190601f1680156106355780820380516001836020036101000a031916815260200191505b50858103825288818151815260200191508051906020019080838360005b8381101561066e578082015181840152602081019050610653565b50505050905090810190601f16801561069b5780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b3480156106bf57600080fd5b506106ec600480360360208110156106d657600080fd5b8101908080359060200190929190505050610fa5565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561072c578082015181840152602081019050610711565b50505050905090810190601f1680156107595780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561077357600080fd5b5061077c611060565b604051808215151515815260200191505060405180910390f35b3480156107a257600080fd5b506107ab611069565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156107f957600080fd5b5061080261108e565b005b34801561081057600080fd5b50610819611143565b6040518082815260200191505060405180910390f35b34801561083b57600080fd5b5061087e6004803603602081101561085257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611150565b005b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156108db57600080fd5b600060038111156108e857fe5b600960009054906101000a900460ff16600381111561090357fe5b14151561090f57600080fd5b600782829091806001815401808255809150509060018203906000526020600020016000909192939091929390919290919250919061094f929190611225565b50505050565b60088054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109eb5780601f106109c0576101008083540402835291602001916109eb565b820191906000526020600020905b8154815290600101906020018083116109ce57829003601f168201915b505050505081565b600960009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610a6157600080fd5b60006003811115610a6e57fe5b600960009054906101000a900460ff166003811115610a8957fe5b141515610a9557600080fd5b600067ffffffffffffffff168367ffffffffffffffff16111515610ab857600080fd5b600067ffffffffffffffff168267ffffffffffffffff1610151515610adc57600080fd5b8a8a60019190610aed929190611225565b50888860029190610aff929190611225565b50868660039190610b11929190611225565b50848460049190610b23929190611225565b5082600560146101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555081600660006101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555080600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600960006101000a81548160ff02191690836003811115610bd657fe5b02179055505050505050505050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c4357600080fd5b60016003811115610c5057fe5b600960009054906101000a900460ff166003811115610c6b57fe5b141515610c7757600080fd5b818160089190610c88929190611225565b506002600960006101000a81548160ff02191690836003811115610ca857fe5b02179055505050565b6060806060600080606060008060018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d545780601f10610d2957610100808354040283529160200191610d54565b820191906000526020600020905b815481529060010190602001808311610d3757829003601f168201915b5050505050975060028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610df15780601f10610dc657610100808354040283529160200191610df1565b820191906000526020600020905b815481529060010190602001808311610dd457829003601f168201915b5050505050965060038054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e8e5780601f10610e6357610100808354040283529160200191610e8e565b820191906000526020600020905b815481529060010190602001808311610e7157829003601f168201915b50505050509550600560149054906101000a900467ffffffffffffffff169450600660009054906101000a900467ffffffffffffffff16935060048054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f5d5780601f10610f3257610100808354040283529160200191610f5d565b820191906000526020600020905b815481529060010190602001808311610f4057829003601f168201915b50505050509250600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169150600960009054906101000a900460ff1690509091929394959697565b600781815481101515610fb457fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110585780601f1061102d57610100808354040283529160200191611058565b820191906000526020600020905b81548152906001019060200180831161103b57829003601f168201915b505050505081565b60006001905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156110e957600080fd5b600260038111156110f657fe5b600960009054906101000a900460ff16600381111561111157fe5b14151561111d57600080fd5b6003600960006101000a81548160ff0219169083600381111561113c57fe5b0217905550565b6000600780549050905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156111ab57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151561122257806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061126657803560ff1916838001178555611294565b82800160010185558215611294579182015b82811115611293578235825591602001919060010190611278565b5b5090506112a191906112a5565b5090565b6112c791905b808211156112c35760008160009055506001016112ab565b5090565b9056fea165627a7a723058206832d2b1c5b6bfae66995423ade85448e8375485cf755f0460596abf5dc30b120029";

    public static final String FUNC_SECURERANDOMTOKEN = "secureRandomToken";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_STRUCTUREDDATA = "structuredData";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_PUSHSTRUCTUREDDATA = "pushStructuredData";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_INITSECURERANDOMTOKEN = "initSecureRandomToken";

    public static final String FUNC_REVOKE = "revoke";

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_GETSIZEOFSTRUCTUREDDATA = "getSizeOfStructuredData";

    public static final String FUNC_ISSTRUCTURED = "isStructured";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected StructuredDoc(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public StructuredDoc(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public StructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Utf8String> secureRandomToken() {
        final Function function = new Function(FUNC_SECURERANDOMTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint8> status() {
        final Function function = new Function(FUNC_STATUS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> structuredData(Uint256 param0) {
        final Function function = new Function(FUNC_STRUCTUREDDATA, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(Address newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pushStructuredData(Utf8String _structuredData) {
        final Function function = new Function(
                FUNC_PUSHSTRUCTUREDDATA, 
                Arrays.<Type>asList(_structuredData), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> init(Utf8String _bankCode, Utf8String _documentName, Utf8String _recipientId, Utf8String _recipientName, Uint64 _issueTimestamp, Uint64 _expireTimestamp, Address _templateId) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(_bankCode, _documentName, _recipientId, _recipientName, _issueTimestamp, _expireTimestamp, _templateId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initSecureRandomToken(Utf8String _secureRandomToken) {
        final Function function = new Function(
                FUNC_INITSECURERANDOMTOKEN, 
                Arrays.<Type>asList(_secureRandomToken), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revoke() {
        final Function function = new Function(
                FUNC_REVOKE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8>> getData() {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {}));
        return new RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8>>(
                new Callable<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8>>() {
                    @Override
                    public Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8>(
                                (Utf8String) results.get(0), 
                                (Utf8String) results.get(1), 
                                (Utf8String) results.get(2), 
                                (Uint64) results.get(3), 
                                (Uint64) results.get(4), 
                                (Utf8String) results.get(5), 
                                (Address) results.get(6), 
                                (Uint8) results.get(7));
                    }
                });
    }

    public RemoteCall<Uint256> getSizeOfStructuredData() {
        final Function function = new Function(FUNC_GETSIZEOFSTRUCTUREDDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bool> isStructured() {
        final Function function = new Function(FUNC_ISSTRUCTURED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static StructuredDoc load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StructuredDoc(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StructuredDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StructuredDoc(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StructuredDoc load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StructuredDoc(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StructuredDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StructuredDoc(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StructuredDoc.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StructuredDoc.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StructuredDoc.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StructuredDoc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getDataOnDeploy() {
        return BINARY;
    }

    public static String getDataOnInit(String _bankCode, String _documentName, String _recipientId, String _recipientName, Long _issueTimestamp, Long _expireTimestamp, String _templateId) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.asList(
                        new Utf8String(_bankCode),
                        new Utf8String(_documentName),
                        new Utf8String(_recipientId),
                        new Utf8String(_recipientName),
                        new Uint64(_issueTimestamp),
                        new Uint64(_expireTimestamp),
                        new Address(_templateId)
                ),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }

    public static String getDataOnInitSecureRandomToken(String _secureRandomToken) {
        final Function function = new Function(
                FUNC_INITSECURERANDOMTOKEN,
                Arrays.asList(new Utf8String(_secureRandomToken)),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }

    public static String getDataOnPushStructuredData(String _structuredData) {
        final Function function = new Function(
                FUNC_PUSHSTRUCTUREDDATA,
                Arrays.asList(new Utf8String(_structuredData)),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }

    public static String getDataOnRevoke() {
        final Function function = new Function(
                FUNC_REVOKE,
                Arrays.asList(),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }
}
