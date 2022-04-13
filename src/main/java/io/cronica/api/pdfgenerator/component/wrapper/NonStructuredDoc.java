package io.cronica.api.pdfgenerator.component.wrapper;

import org.apache.commons.lang.ArrayUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
    private static final String BINARY = "0x60806040523480156200001157600080fd5b5060405162000ca238038062000ca2833981018060405260c08110156200003757600080fd5b8151602083015160408401516060850151608086018051949693959294919392830192916401000000008111156200006e57600080fd5b820160208101848111156200008257600080fd5b81516401000000008111828201871017156200009d57600080fd5b50509291906020018051640100000000811115620000ba57600080fd5b82016020810184811115620000ce57600080fd5b8151640100000000811182820187101715620000e957600080fd5b5050600080546001600160a01b0319163217905592505050856200011d5760078054600160a01b60ff021916905562000226565b81516200013290600290602085019062000232565b50604080516001600160a81b031988166020808301919091528251808303600b018152602b90920190925280516200016f92600192019062000232565b50600380546001600160a01b031916600888901c606090811b901c179055600580546001600160401b031916608087901b60c01c6001600160401b0390811691909117600160401b600160801b03191668010000000000000000918816919091021790558051620001e890600490602084019062000232565b506007805460068590556001600160a01b031916606086901c17600160a01b60ff021916740200000000000000000000000000000000000000001790555b505050505050620002d7565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200027557805160ff1916838001178555620002a5565b82800160010185558215620002a5579182015b82811115620002a557825182559160200191906001019062000288565b50620002b3929150620002b7565b5090565b620002d491905b80821115620002b35760008155600101620002be565b90565b6109bb80620002e76000396000f3fe608060405234801561001057600080fd5b50600436106100a35760003560e01c80634b4df184116100765780638da5cb5b1161005b5780638da5cb5b14610433578063b6549f7514610457578063ccca36ed1461045f576100a3565b80634b4df184146102c857806354fd4d5014610409576100a3565b80631a40ac37146100a8578063200d2ed2146100d25780632f407a85146100fe5780633bc5de301461012c575b600080fd5b6100b061047b565b604080516bffffffffffffffffffffffff199092168252519081900360200190f35b6100da610484565b604051808260038111156100ea57fe5b60ff16815260200191505060405180910390f35b61012a6004803603602081101561011457600080fd5b50356bffffffffffffffffffffffff1916610494565b005b61013461051d565b604080516bffffffffffffffffffffffff1988169181019190915267ffffffffffffffff80871660608301528516608082015260a08101849052806020810160c0820160e0830185600381111561018757fe5b60ff16815260200184810384528c818151815260200191508051906020019080838360005b838110156101c45781810151838201526020016101ac565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b5084810383528b5181528b516020918201918d019080838360005b8381101561022457818101518382015260200161020c565b50505050905090810190601f1680156102515780820380516001836020036101000a031916815260200191505b50848103825286518152865160209182019188019080838360005b8381101561028457818101518382015260200161026c565b50505050905090810190601f1680156102b15780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b61012a600480360360e08110156102de57600080fd5b8101906020810181356401000000008111156102f957600080fd5b82018360208201111561030b57600080fd5b8035906020019184600183028401116401000000008311171561032d57600080fd5b91939092909160208101903564010000000081111561034b57600080fd5b82018360208201111561035d57600080fd5b8035906020019184600183028401116401000000008311171561037f57600080fd5b919390926bffffffffffffffffffffffff19833516926040810190602001356401000000008111156103b057600080fd5b8201836020820111156103c257600080fd5b803590602001918460018302840111640100000000831117156103e457600080fd5b919350915067ffffffffffffffff813581169160208101359091169060400135610757565b610411610869565b6040805160ff909316835263ffffffff90911660208301528051918290030190f35b61043b610870565b604080516001600160a01b039092168252519081900360200190f35b61012a61087f565b6104676108f1565b604080519115158252519081900360200190f35b60075460601b81565b600754600160a01b900460ff1681565b6000546001600160a01b031633146104ab57600080fd5b6001600754600160a01b900460ff1660038111156104c557fe5b146104cf57600080fd5b6007805473ffffffffffffffffffffffffffffffffffffffff1916606083901c178082556002919074ff00000000000000000000000000000000000000001916600160a01b83021790555050565b6060806000806000806060600060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105c05780601f10610595576101008083540402835291602001916105c0565b820191906000526020600020905b8154815290600101906020018083116105a357829003601f168201915b505060028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152969e509194509250840190508282801561064e5780601f106106235761010080835404028352916020019161064e565b820191906000526020600020905b81548152906001019060200180831161063157829003601f168201915b50505050509650600360009054906101000a900460601b9550600560009054906101000a900467ffffffffffffffff169450600560089054906101000a900467ffffffffffffffff169350600654925060048054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107345780601f1061070957610100808354040283529160200191610734565b820191906000526020600020905b81548152906001019060200180831161071757829003601f168201915b50505050509150600760149054906101000a900460ff1690509091929394959697565b6000546001600160a01b0316331461076e57600080fd5b6000600754600160a01b900460ff16600381111561078857fe5b1461079257600080fd5b67ffffffffffffffff83166107a657600080fd5b6107b260018b8b6108f7565b506107bf600289896108f7565b506003805473ffffffffffffffffffffffffffffffffffffffff1916606088901c1790556107ef600486866108f7565b506005805467ffffffffffffffff191667ffffffffffffffff948516176fffffffffffffffff00000000000000001916680100000000000000009390941692909202929092179055600655505060078054600160a01b74ff0000000000000000000000000000000000000000199091161790555050505050565b6001809091565b6000546001600160a01b031681565b6000546001600160a01b0316331461089657600080fd5b6002600754600160a01b900460ff1660038111156108b057fe5b146108ba57600080fd5b6007805474ff0000000000000000000000000000000000000000191674030000000000000000000000000000000000000000179055565b60015b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109385782800160ff19823516178555610965565b82800160010185558215610965579182015b8281111561096557823582559160200191906001019061094a565b50610971929150610975565b5090565b6108f491905b80821115610971576000815560010161097b56fea165627a7a7230582042b6e23cac1c1ddaa47097d27b0328cb9684f2215ed6b2ff5406440bcbe0b7830029";

    public static final String FUNC_SECURERANDOMTOKEN = "secureRandomToken";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_INITSECURERANDOMTOKEN = "initSecureRandomToken";

    public static final String FUNC_REVOKE = "revoke";

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_ISNONSTRUCTURED = "isNonStructured";

    public static final String FUNC_VERSION = "version";

    public static final List<TypeReference<?>> R_VALUES_DATA_LEGACY = Arrays.asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {});
    public static final List<TypeReference<?>> R_VALUES_DATA_V1 = Arrays.asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes20>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {});

    public static final List<TypeReference<?>> R_VALUES_VERSION = Arrays.asList(new TypeReference<Uint8>() {}, new TypeReference<Uint32>() {});

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

    public RemoteCall<Bytes20> secureRandomToken() {
        final Function function = new Function(FUNC_SECURERANDOMTOKEN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes20>() {}));
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

    public RemoteCall<TransactionReceipt> init(Utf8String _bankCode, Utf8String _documentName, Bytes20 _recipientId, Utf8String _recipientName, Uint64 _issueTimestamp, Uint64 _expireTimestamp, Bytes32 _dataHash) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.<Type>asList(_bankCode, _documentName, _recipientId, _recipientName, _issueTimestamp, _expireTimestamp, _dataHash),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initSecureRandomToken(Bytes20 _secureRandomToken) {
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

    public RemoteCall<Tuple8<Utf8String, Utf8String, Bytes32, Uint64, Uint64, Bytes32, Utf8String, Uint8>> getData() {
        final Function function = new Function(FUNC_GETDATA, Arrays.<Type>asList(), R_VALUES_DATA_V1);
        return new RemoteCall<Tuple8<Utf8String, Utf8String, Bytes32, Uint64, Uint64, Bytes32, Utf8String, Uint8>>(
                new Callable<Tuple8<Utf8String, Utf8String, Bytes32, Uint64, Uint64, Bytes32, Utf8String, Uint8>>() {
                    @Override
                    public Tuple8<Utf8String, Utf8String, Bytes32, Uint64, Uint64, Bytes32, Utf8String, Uint8> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<Utf8String, Utf8String, Bytes32, Uint64, Uint64, Bytes32, Utf8String, Uint8>(
                                (Utf8String) results.get(0),
                                (Utf8String) results.get(1),
                                (Bytes32) results.get(2),
                                (Uint64) results.get(3),
                                (Uint64) results.get(4),
                                (Bytes32) results.get(5),
                                (Utf8String) results.get(6),
                                (Uint8) results.get(7));
                    }
                });
    }

    public RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8>> getDataLegacy() {
        final Function function = new Function(FUNC_GETDATA, Arrays.<Type>asList(), R_VALUES_DATA_LEGACY);
        return new RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8>>(
                new Callable<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8>>() {
                    @Override
                    public Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8>(
                                (Utf8String) results.get(0),
                                (Utf8String) results.get(1),
                                (Utf8String) results.get(2),
                                (Uint64) results.get(3),
                                (Uint64) results.get(4),
                                (Bytes32) results.get(5),
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

    public RemoteCall<Tuple2<Uint8, Uint32>> version() {
        final Function function = new Function(FUNC_VERSION, Arrays.<Type>asList(), R_VALUES_VERSION);
        return new RemoteCall<Tuple2<Uint8, Uint32>>(
                new Callable<Tuple2<Uint8, Uint32>>() {
                    @Override
                    public Tuple2<Uint8, Uint32> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Uint8, Uint32>(
                                (Uint8) results.get(0),
                                (Uint32) results.get(1));
                    }
                });
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

    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Bytes32 _recipientInfo, Uint128 _timestamps, Bytes20 _secureRandomToken, Bytes32 _dataHash, Utf8String _documentName, Utf8String _recipientName) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _secureRandomToken, _dataHash, _documentName, _recipientName));
        return deployRemoteCall(NonStructuredDoc.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Bytes32 _recipientInfo, Uint128 _timestamps, Bytes20 _secureRandomToken, Bytes32 _dataHash, Utf8String _documentName, Utf8String _recipientName) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _secureRandomToken, _dataHash, _documentName, _recipientName));
        return deployRemoteCall(NonStructuredDoc.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Bytes32 _recipientInfo, Uint128 _timestamps, Bytes20 _secureRandomToken, Bytes32 _dataHash, Utf8String _documentName, Utf8String _recipientName) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _secureRandomToken, _dataHash, _documentName, _recipientName));
        return deployRemoteCall(NonStructuredDoc.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonStructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Bytes32 _recipientInfo, Uint128 _timestamps, Bytes20 _secureRandomToken, Bytes32 _dataHash, Utf8String _documentName, Utf8String _recipientName) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _secureRandomToken, _dataHash, _documentName, _recipientName));
        return deployRemoteCall(NonStructuredDoc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getDataOnDeploy() {
        final String constructor = FunctionEncoder.encodeConstructor(
                Arrays.asList(
                        Bytes32.DEFAULT,
                        Uint128.DEFAULT,
                        Bytes20.DEFAULT,
                        Bytes32.DEFAULT,
                        Utf8String.DEFAULT,
                        Utf8String.DEFAULT
                )
        );
        return BINARY + constructor;
    }

    public static String getDataOnDeploy(String _bankCode, String _documentName, String _recipientId, String _recipientName, Long _issueTimestamp, Long _expireTimestamp, byte[] _secureRandomToken, byte[] _dataHash) {
        byte[] bankCode = Arrays.copyOf(_bankCode.getBytes(StandardCharsets.UTF_8), 11);
        byte[] recipientInfo = Arrays.copyOf(ArrayUtils.addAll(bankCode, Numeric.hexStringToByteArray(_recipientId)), 32);
        BigInteger issueTimestamp = BigInteger.valueOf(_issueTimestamp);
        BigInteger expireTimestamp = BigInteger.valueOf(_expireTimestamp);
        BigInteger timestamps = issueTimestamp.shiftLeft(Long.SIZE).add(expireTimestamp);
        final String constructor = FunctionEncoder.encodeConstructor(
                Arrays.asList(
                        new Bytes32(recipientInfo),
                        new Uint128(timestamps),
                        new Bytes20(_secureRandomToken),
                        new Bytes32(_dataHash),
                        new Utf8String(_documentName),
                        new Utf8String(_recipientName)
                )
        );
        return BINARY + constructor;
    }

    public static String getDataOnInit(String _bankCode, String _documentName, String _recipientId, String _recipientName, Long _issueTimestamp, Long _expireTimestamp, byte[] _dataHash) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.asList(
                        new Utf8String(_bankCode),
                        new Utf8String(_documentName),
                        new Bytes20(Numeric.hexStringToByteArray(_recipientId)),
                        new Utf8String(_recipientName),
                        new Uint64(_issueTimestamp),
                        new Uint64(_expireTimestamp),
                        new Bytes32(_dataHash)
                ),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }

    public static String getDataOnInitSecureRandomToken(byte[] _secureRandomToken) {
        final Function function = new Function(
                FUNC_INITSECURERANDOMTOKEN,
                Arrays.asList(new Bytes20(_secureRandomToken)),
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
