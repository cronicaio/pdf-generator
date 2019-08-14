package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes20;
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
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060008054600160a060020a031916331781556008805460a060020a60ff021916905561111890819061004390396000f3fe6080604052600436106100b95763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631a40ac3781146100be578063200d2ed2146100f55780632105401d1461012e5780632f407a85146102d45780633bc5de301461030d5780638a330a401461051a5780638da5cb5b14610543578063b6549f7514610581578063e3f6618d14610596578063e648aa19146105bd578063eb87cdc114610647578063f2fde38b146106c4575b600080fd5b3480156100ca57600080fd5b506100d3610704565b604080516bffffffffffffffffffffffff199092168252519081900360200190f35b34801561010157600080fd5b5061010a610719565b6040518082600381111561011a57fe5b60ff16815260200191505060405180910390f35b34801561013a57600080fd5b506102d2600480360360e081101561015157600080fd5b81019060208101813564010000000081111561016c57600080fd5b82018360208201111561017e57600080fd5b803590602001918460018302840111640100000000831117156101a057600080fd5b9193909290916020810190356401000000008111156101be57600080fd5b8201836020820111156101d057600080fd5b803590602001918460018302840111640100000000831117156101f257600080fd5b91939092909160208101903564010000000081111561021057600080fd5b82018360208201111561022257600080fd5b8035906020019184600183028401116401000000008311171561024457600080fd5b91939092909160208101903564010000000081111561026257600080fd5b82018360208201111561027457600080fd5b8035906020019184600183028401116401000000008311171561029657600080fd5b9193509150803567ffffffffffffffff908116916020810135909116906040013573ffffffffffffffffffffffffffffffffffffffff1661073a565b005b3480156102e057600080fd5b506102d2600480360360208110156102f757600080fd5b50356bffffffffffffffffffffffff19166108c3565b34801561031957600080fd5b50610322610986565b6040805167ffffffffffffffff80881660608301528616608082015273ffffffffffffffffffffffffffffffffffffffff841660c08201529081906020820190820160a0830160e0840186600381111561037857fe5b60ff16815260200185810385528d818151815260200191508051906020019080838360005b838110156103b557818101518382015260200161039d565b50505050905090810190601f1680156103e25780820380516001836020036101000a031916815260200191505b5085810384528c5181528c516020918201918e019080838360005b838110156104155781810151838201526020016103fd565b50505050905090810190601f1680156104425780820380516001836020036101000a031916815260200191505b5085810383528b5181528b516020918201918d019080838360005b8381101561047557818101518382015260200161045d565b50505050905090810190601f1680156104a25780820380516001836020036101000a031916815260200191505b5085810382528851815288516020918201918a019080838360005b838110156104d55781810151838201526020016104bd565b50505050905090810190601f1680156105025780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b34801561052657600080fd5b5061052f610c5d565b604080519115158252519081900360200190f35b34801561054f57600080fd5b50610558610c63565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b34801561058d57600080fd5b506102d2610c7f565b3480156105a257600080fd5b506105ab610d0f565b60408051918252519081900360200190f35b3480156105c957600080fd5b506105d2610d28565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561060c5781810151838201526020016105f4565b50505050905090810190601f1680156106395780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561065357600080fd5b506102d26004803603602081101561066a57600080fd5b81019060208101813564010000000081111561068557600080fd5b82018360208201111561069757600080fd5b803590602001918460018302840111640100000000831117156106b957600080fd5b509092509050610db6565b3480156106d057600080fd5b506102d2600480360360208110156106e757600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610ef6565b6008546c010000000000000000000000000281565b60085474010000000000000000000000000000000000000000900460ff1681565b60005473ffffffffffffffffffffffffffffffffffffffff16331461075e57600080fd5b600060085474010000000000000000000000000000000000000000900460ff16600381111561078957fe5b1461079357600080fd5b600067ffffffffffffffff8416116107aa57600080fd5b600067ffffffffffffffff831610156107c257600080fd5b6107ce60018c8c610fe6565b506107db60028a8a610fe6565b506107e860038888610fe6565b506107f560048686610fe6565b50600580546006805467ffffffffffffffff191667ffffffffffffffff868116919091179091557fffffffff0000000000000000ffffffffffffffffffffffffffffffffffffffff9091167401000000000000000000000000000000000000000091861682021773ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff8416179091556008805460019274ff000000000000000000000000000000000000000019909116908302179055505050505050505050505050565b60005473ffffffffffffffffffffffffffffffffffffffff1633146108e757600080fd5b600160085474010000000000000000000000000000000000000000900460ff16600381111561091257fe5b1461091c57600080fd5b6008805473ffffffffffffffffffffffffffffffffffffffff19166c010000000000000000000000008304178082556002919074ff000000000000000000000000000000000000000019167401000000000000000000000000000000000000000083021790555050565b6060806060600080606060008060018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a295780601f106109fe57610100808354040283529160200191610a29565b820191906000526020600020905b815481529060010190602001808311610a0c57829003601f168201915b505060028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152969e5091945092508401905082828015610ab75780601f10610a8c57610100808354040283529160200191610ab7565b820191906000526020600020905b815481529060010190602001808311610a9a57829003601f168201915b505060038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152969d5091945092508401905082828015610b485780601f10610b1d57610100808354040283529160200191610b48565b820191906000526020600020905b815481529060010190602001808311610b2b57829003601f168201915b50506005546006546004805460408051602060026101006001861615026000190190941693909304601f8101849004840282018401909252818152989e507401000000000000000000000000000000000000000090940467ffffffffffffffff9081169d509092169a5094509092508401905082828015610c0a5780601f10610bdf57610100808354040283529160200191610c0a565b820191906000526020600020905b815481529060010190602001808311610bed57829003601f168201915b50506005546008549c9d9b9c9a9b999a9899949873ffffffffffffffffffffffffffffffffffffffff909116975074010000000000000000000000000000000000000000900460ff169550929350505050565b60015b90565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff163314610ca357600080fd5b600260085474010000000000000000000000000000000000000000900460ff166003811115610cce57fe5b14610cd857600080fd5b6008805474ff0000000000000000000000000000000000000000191674030000000000000000000000000000000000000000179055565b6007546002600019610100600184161502019091160490565b6007805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610dae5780601f10610d8357610100808354040283529160200191610dae565b820191906000526020600020905b815481529060010190602001808311610d9157829003601f168201915b505050505081565b60005473ffffffffffffffffffffffffffffffffffffffff163314610dda57600080fd5b600060085474010000000000000000000000000000000000000000900460ff166003811115610e0557fe5b14610e0f57600080fd5b60078054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152610edd9390929091830182828015610e9c5780601f10610e7157610100808354040283529160200191610e9c565b820191906000526020600020905b815481529060010190602001808311610e7f57829003601f168201915b505050505083838080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250610f6f92505050565b8051610ef191600791602090910190611064565b505050565b60005473ffffffffffffffffffffffffffffffffffffffff163314610f1a57600080fd5b73ffffffffffffffffffffffffffffffffffffffff811615610f6c576000805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff83161790555b50565b815181516040518183018082526060939290916020601f8086018290049301049060005b83811015610faf57600101602081028981015190830152610f93565b5060005b82811015610fd1576001016020810288810151908701830152610fb3565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110275782800160ff19823516178555611054565b82800160010185558215611054579182015b82811115611054578235825591602001919060010190611039565b506110609291506110d2565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110a557805160ff1916838001178555611054565b82800160010185558215611054579182015b828111156110545782518255916020019190600101906110b7565b610c6091905b8082111561106057600081556001016110d856fea165627a7a7230582057f271486501f96d0c239f6118eed9b81a8a2b373b53a739c46a7836fa489c9e0029";

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

    public RemoteCall<DynamicBytes> structuredData() {
        final Function function = new Function(FUNC_STRUCTUREDDATA, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> structuredDataOld(Uint256 param0) {
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

    public RemoteCall<TransactionReceipt> pushStructuredData(DynamicBytes _structuredData) {
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

    public static String getDataOnInitSecureRandomToken(byte[] _secureRandomToken) {
        final Function function = new Function(
                FUNC_INITSECURERANDOMTOKEN,
                Arrays.asList(new Bytes20(_secureRandomToken)),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }

    public static String getDataOnPushStructuredData(byte[] _structuredData) {
        final Function function = new Function(
                FUNC_PUSHSTRUCTUREDDATA,
                Arrays.asList(new DynamicBytes(_structuredData)),
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
