package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes20;
import org.web3j.abi.datatypes.generated.Bytes32;
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
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060008054600160a060020a031916331781556007805460a060020a60ff0219169055610c7090819061004390396000f3fe6080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631a40ac37811461009d578063200d2ed2146100d45780632f407a851461010d5780633bc5de30146101485780638da5cb5b14610359578063b5d7f81b14610397578063b6549f7514610524578063ccca36ed14610539578063f2fde38b14610562575b600080fd5b3480156100a957600080fd5b506100b26105a2565b604080516bffffffffffffffffffffffff199092168252519081900360200190f35b3480156100e057600080fd5b506100e96105b7565b604051808260038111156100f957fe5b60ff16815260200191505060405180910390f35b34801561011957600080fd5b506101466004803603602081101561013057600080fd5b50356bffffffffffffffffffffffff19166105d8565b005b34801561015457600080fd5b5061015d61069b565b604051808060200180602001806020018967ffffffffffffffff1667ffffffffffffffff1681526020018867ffffffffffffffff1667ffffffffffffffff168152602001878152602001806020018660038111156101b757fe5b60ff16815260200185810385528d818151815260200191508051906020019080838360005b838110156101f45781810151838201526020016101dc565b50505050905090810190601f1680156102215780820380516001836020036101000a031916815260200191505b5085810384528c5181528c516020918201918e019080838360005b8381101561025457818101518382015260200161023c565b50505050905090810190601f1680156102815780820380516001836020036101000a031916815260200191505b5085810383528b5181528b516020918201918d019080838360005b838110156102b457818101518382015260200161029c565b50505050905090810190601f1680156102e15780820380516001836020036101000a031916815260200191505b50858103825287518152875160209182019189019080838360005b838110156103145781810151838201526020016102fc565b50505050905090810190601f1680156103415780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b34801561036557600080fd5b5061036e61093a565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156103a357600080fd5b50610146600480360360e08110156103ba57600080fd5b8101906020810181356401000000008111156103d557600080fd5b8201836020820111156103e757600080fd5b8035906020019184600183028401116401000000008311171561040957600080fd5b91939092909160208101903564010000000081111561042757600080fd5b82018360208201111561043957600080fd5b8035906020019184600183028401116401000000008311171561045b57600080fd5b91939092909160208101903564010000000081111561047957600080fd5b82018360208201111561048b57600080fd5b803590602001918460018302840111640100000000831117156104ad57600080fd5b9193909290916020810190356401000000008111156104cb57600080fd5b8201836020820111156104dd57600080fd5b803590602001918460018302840111640100000000831117156104ff57600080fd5b919350915067ffffffffffffffff813581169160208101359091169060400135610956565b34801561053057600080fd5b50610146610a9d565b34801561054557600080fd5b5061054e610b2d565b604080519115158252519081900360200190f35b34801561056e57600080fd5b506101466004803603602081101561058557600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610b33565b6007546c010000000000000000000000000281565b60075474010000000000000000000000000000000000000000900460ff1681565b60005473ffffffffffffffffffffffffffffffffffffffff1633146105fc57600080fd5b600160075474010000000000000000000000000000000000000000900460ff16600381111561062757fe5b1461063157600080fd5b6007805473ffffffffffffffffffffffffffffffffffffffff19166c010000000000000000000000008304178082556002919074ff000000000000000000000000000000000000000019167401000000000000000000000000000000000000000083021790555050565b606080606060008060006060600060018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561073f5780601f106107145761010080835404028352916020019161073f565b820191906000526020600020905b81548152906001019060200180831161072257829003601f168201915b505060028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152969e50919450925084019050828280156107cd5780601f106107a2576101008083540402835291602001916107cd565b820191906000526020600020905b8154815290600101906020018083116107b057829003601f168201915b505060038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152969d509194509250840190508282801561085e5780601f106108335761010080835404028352916020019161085e565b820191906000526020600020905b81548152906001019060200180831161084157829003601f168201915b505060055460065460048054604080516020601f60026000196001871615610100020190951694909404938401819004810282018101909252828152989e5067ffffffffffffffff8086169e50680100000000000000009095049094169b509199509450925084019050828280156109175780601f106108ec57610100808354040283529160200191610917565b820191906000526020600020905b8154815290600101906020018083116108fa57829003601f168201915b50505050509150600760149054906101000a900460ff1690509091929394959697565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff16331461097a57600080fd5b600060075474010000000000000000000000000000000000000000900460ff1660038111156109a557fe5b146109af57600080fd5b600067ffffffffffffffff8416116109c657600080fd5b600067ffffffffffffffff831610156109de57600080fd5b6109ea60018c8c610bac565b506109f760028a8a610bac565b50610a0460038888610bac565b50610a1160048686610bac565b506005805467ffffffffffffffff191667ffffffffffffffff948516176fffffffffffffffff000000000000000019166801000000000000000093909416929092029290921790556006555050600780547401000000000000000000000000000000000000000074ff000000000000000000000000000000000000000019909116179055505050505050565b60005473ffffffffffffffffffffffffffffffffffffffff163314610ac157600080fd5b600260075474010000000000000000000000000000000000000000900460ff166003811115610aec57fe5b14610af657600080fd5b6007805474ff0000000000000000000000000000000000000000191674030000000000000000000000000000000000000000179055565b60015b90565b60005473ffffffffffffffffffffffffffffffffffffffff163314610b5757600080fd5b73ffffffffffffffffffffffffffffffffffffffff811615610ba9576000805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff83161790555b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610bed5782800160ff19823516178555610c1a565b82800160010185558215610c1a579182015b82811115610c1a578235825591602001919060010190610bff565b50610c26929150610c2a565b5090565b610b3091905b80821115610c265760008155600101610c3056fea165627a7a72305820b0bd2084220fbde6dd4e24e6155aa579eede00b597097c1532051010a68efbfb0029";

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

    public RemoteCall<TransactionReceipt> init(Utf8String _bankCode, Utf8String _documentName, Utf8String _recipientId, Utf8String _recipientName, Uint64 _issueTimestamp, Uint64 _expireTimestamp, Bytes32 _dataHash) {
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

    public RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8>> getData() {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}));
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

    public static String getDataOnInit(String _bankCode, String _documentName, String _recipientId, String _recipientName, Long _issueTimestamp, Long _expireTimestamp, byte[] _dataHash) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.asList(
                        new Utf8String(_bankCode),
                        new Utf8String(_documentName),
                        new Utf8String(_recipientId),
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
