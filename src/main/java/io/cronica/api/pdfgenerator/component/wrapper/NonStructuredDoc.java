package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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
public class NonStructuredDoc extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600860006101000a81548160ff0219169083600381111561007057fe5b021790555061108b806100846000396000f3fe608060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631a40ac371461009e578063200d2ed21461012e5780632f91c898146101675780633bc5de30146101ed5780638da5cb5b14610478578063ac811ab8146104cf578063b6549f75146106d1578063ccca36ed146106e8578063f2fde38b14610717575b600080fd5b3480156100aa57600080fd5b506100b3610768565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100f35780820151818401526020810190506100d8565b50505050905090810190601f1680156101205780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561013a57600080fd5b50610143610806565b6040518082600381111561015357fe5b60ff16815260200191505060405180910390f35b34801561017357600080fd5b506101eb6004803603602081101561018a57600080fd5b81019080803590602001906401000000008111156101a757600080fd5b8201836020820111156101b957600080fd5b803590602001918460018302840111640100000000831117156101db57600080fd5b9091929391929390505050610819565b005b3480156101f957600080fd5b506102026108e2565b604051808060200180602001806020018967ffffffffffffffff1667ffffffffffffffff1681526020018867ffffffffffffffff1667ffffffffffffffff168152602001806020018060200187600381111561025a57fe5b60ff16815260200186810386528e818151815260200191508051906020019080838360005b8381101561029a57808201518184015260208101905061027f565b50505050905090810190601f1680156102c75780820380516001836020036101000a031916815260200191505b5086810385528d818151815260200191508051906020019080838360005b838110156103005780820151818401526020810190506102e5565b50505050905090810190601f16801561032d5780820380516001836020036101000a031916815260200191505b5086810384528c818151815260200191508051906020019080838360005b8381101561036657808201518184015260208101905061034b565b50505050905090810190601f1680156103935780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b838110156103cc5780820151818401526020810190506103b1565b50505050905090810190601f1680156103f95780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b83811015610432578082015181840152602081019050610417565b50505050905090810190601f16801561045f5780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b34801561048457600080fd5b5061048d610c4e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156104db57600080fd5b506106cf600480360360e08110156104f257600080fd5b810190808035906020019064010000000081111561050f57600080fd5b82018360208201111561052157600080fd5b8035906020019184600183028401116401000000008311171561054357600080fd5b90919293919293908035906020019064010000000081111561056457600080fd5b82018360208201111561057657600080fd5b8035906020019184600183028401116401000000008311171561059857600080fd5b9091929391929390803590602001906401000000008111156105b957600080fd5b8201836020820111156105cb57600080fd5b803590602001918460018302840111640100000000831117156105ed57600080fd5b90919293919293908035906020019064010000000081111561060e57600080fd5b82018360208201111561062057600080fd5b8035906020019184600183028401116401000000008311171561064257600080fd5b9091929391929390803567ffffffffffffffff169060200190929190803567ffffffffffffffff1690602001909291908035906020019064010000000081111561068b57600080fd5b82018360208201111561069d57600080fd5b803590602001918460018302840111640100000000831117156106bf57600080fd5b9091929391929390505050610c73565b005b3480156106dd57600080fd5b506106e6610e27565b005b3480156106f457600080fd5b506106fd610edc565b604051808215151515815260200191505060405180910390f35b34801561072357600080fd5b506107666004803603602081101561073a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610ee5565b005b60078054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107fe5780601f106107d3576101008083540402835291602001916107fe565b820191906000526020600020905b8154815290600101906020018083116107e157829003601f168201915b505050505081565b600860009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561087457600080fd5b6001600381111561088157fe5b600860009054906101000a900460ff16600381111561089c57fe5b1415156108a857600080fd5b8181600791906108b9929190610fba565b506002600860006101000a81548160ff021916908360038111156108d957fe5b02179055505050565b6060806060600080606080600060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109855780601f1061095a57610100808354040283529160200191610985565b820191906000526020600020905b81548152906001019060200180831161096857829003601f168201915b5050505050975060028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a225780601f106109f757610100808354040283529160200191610a22565b820191906000526020600020905b815481529060010190602001808311610a0557829003601f168201915b5050505050965060038054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610abf5780601f10610a9457610100808354040283529160200191610abf565b820191906000526020600020905b815481529060010190602001808311610aa257829003601f168201915b50505050509550600560009054906101000a900467ffffffffffffffff169450600560089054906101000a900467ffffffffffffffff16935060068054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b8e5780601f10610b6357610100808354040283529160200191610b8e565b820191906000526020600020905b815481529060010190602001808311610b7157829003601f168201915b5050505050925060048054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c2b5780601f10610c0057610100808354040283529160200191610c2b565b820191906000526020600020905b815481529060010190602001808311610c0e57829003601f168201915b50505050509150600860009054906101000a900460ff1690509091929394959697565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610cce57600080fd5b60006003811115610cdb57fe5b600860009054906101000a900460ff166003811115610cf657fe5b141515610d0257600080fd5b600067ffffffffffffffff168467ffffffffffffffff16111515610d2557600080fd5b600067ffffffffffffffff168367ffffffffffffffff1610151515610d4957600080fd5b8b8b60019190610d5a929190610fba565b50898960029190610d6c929190610fba565b50878760039190610d7e929190610fba565b50858560049190610d90929190610fba565b5083600560006101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555082600560086101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550818160069190610df4929190610fba565b506001600860006101000a81548160ff02191690836003811115610e1457fe5b0217905550505050505050505050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610e8257600080fd5b60026003811115610e8f57fe5b600860009054906101000a900460ff166003811115610eaa57fe5b141515610eb657600080fd5b6003600860006101000a81548160ff02191690836003811115610ed557fe5b0217905550565b60006001905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610f4057600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515610fb757806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ffb57803560ff1916838001178555611029565b82800160010185558215611029579182015b8281111561102857823582559160200191906001019061100d565b5b509050611036919061103a565b5090565b61105c91905b80821115611058576000816000905550600101611040565b5090565b9056fea165627a7a7230582091ecf36183b8fed320afdd46b99e607bb9d0f1e001dcc6244d8bcc157427e1940029";

    public static final String FUNC_SECURERANDOMTOKEN = "secureRandomToken";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_INITSECURERANDOMTOKEN = "initSecureRandomToken";

    public static final String FUNC_REVOKE = "revoke";

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_ISNONSTRUCTURED = "isNonStructured";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected NonStructuredDoc(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public NonStructuredDoc(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NonStructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public NonStructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteCall<TransactionReceipt> init(Utf8String _bankCode, Utf8String _documentName, Utf8String _recipientId, Utf8String _recipientName, Uint64 _issueTimestamp, Uint64 _expireTimestamp, Utf8String _dataHash) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(_bankCode, _documentName, _recipientId, _recipientName, _issueTimestamp, _expireTimestamp, _dataHash), 
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

    public RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Utf8String, Uint8>> getData() {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}));
        return new RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Utf8String, Uint8>>(
                new Callable<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Utf8String, Uint8>>() {
                    @Override
                    public Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Utf8String, Uint8> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Utf8String, Uint8>(
                                (Utf8String) results.get(0), 
                                (Utf8String) results.get(1), 
                                (Utf8String) results.get(2), 
                                (Uint64) results.get(3), 
                                (Uint64) results.get(4), 
                                (Utf8String) results.get(5), 
                                (Utf8String) results.get(6), 
                                (Uint8) results.get(7));
                    }
                });
    }

    public RemoteCall<Bool> isNonStructured() {
        final Function function = new Function(FUNC_ISNONSTRUCTURED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static NonStructuredDoc load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonStructuredDoc(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NonStructuredDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonStructuredDoc(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NonStructuredDoc load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NonStructuredDoc(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NonStructuredDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NonStructuredDoc(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NonStructuredDoc.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NonStructuredDoc.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NonStructuredDoc.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NonStructuredDoc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

    public static String getDataOnInit(String _bankCode, String _documentName, String _recipientId, String _recipientName, Long _issueTimestamp, Long _expireTimestamp, String _dataHash) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.asList(
                        new Utf8String(_bankCode),
                        new Utf8String(_documentName),
                        new Utf8String(_recipientId),
                        new Utf8String(_recipientName),
                        new Uint64(_issueTimestamp),
                        new Uint64(_expireTimestamp),
                        new Utf8String(_dataHash)
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

    public static String getDataOnRevoke() {
        final Function function = new Function(
                FUNC_REVOKE,
                Arrays.asList(),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }
}
