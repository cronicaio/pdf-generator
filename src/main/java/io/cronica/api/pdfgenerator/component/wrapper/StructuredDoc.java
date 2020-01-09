package io.cronica.api.pdfgenerator.component.wrapper;

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
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060008054600160a060020a031916331781556008805460a060020a60ff021916905561105490819061004390396000f3fe6080604052600436106100ae5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631a40ac3781146100b3578063200d2ed2146100ea5780632105401d146101235780632f407a85146102c95780633bc5de30146103025780638a330a401461050f5780638da5cb5b14610538578063b6549f7514610576578063e3f6618d1461058b578063e648aa19146105b2578063eb87cdc11461063c575b600080fd5b3480156100bf57600080fd5b506100c86106b9565b604080516bffffffffffffffffffffffff199092168252519081900360200190f35b3480156100f657600080fd5b506100ff6106ce565b6040518082600381111561010f57fe5b60ff16815260200191505060405180910390f35b34801561012f57600080fd5b506102c7600480360360e081101561014657600080fd5b81019060208101813564010000000081111561016157600080fd5b82018360208201111561017357600080fd5b8035906020019184600183028401116401000000008311171561019557600080fd5b9193909290916020810190356401000000008111156101b357600080fd5b8201836020820111156101c557600080fd5b803590602001918460018302840111640100000000831117156101e757600080fd5b91939092909160208101903564010000000081111561020557600080fd5b82018360208201111561021757600080fd5b8035906020019184600183028401116401000000008311171561023957600080fd5b91939092909160208101903564010000000081111561025757600080fd5b82018360208201111561026957600080fd5b8035906020019184600183028401116401000000008311171561028b57600080fd5b9193509150803567ffffffffffffffff908116916020810135909116906040013573ffffffffffffffffffffffffffffffffffffffff166106ef565b005b3480156102d557600080fd5b506102c7600480360360208110156102ec57600080fd5b50356bffffffffffffffffffffffff1916610878565b34801561030e57600080fd5b5061031761093b565b6040805167ffffffffffffffff80881660608301528616608082015273ffffffffffffffffffffffffffffffffffffffff841660c08201529081906020820190820160a0830160e0840186600381111561036d57fe5b60ff16815260200185810385528d818151815260200191508051906020019080838360005b838110156103aa578181015183820152602001610392565b50505050905090810190601f1680156103d75780820380516001836020036101000a031916815260200191505b5085810384528c5181528c516020918201918e019080838360005b8381101561040a5781810151838201526020016103f2565b50505050905090810190601f1680156104375780820380516001836020036101000a031916815260200191505b5085810383528b5181528b516020918201918d019080838360005b8381101561046a578181015183820152602001610452565b50505050905090810190601f1680156104975780820380516001836020036101000a031916815260200191505b5085810382528851815288516020918201918a019080838360005b838110156104ca5781810151838201526020016104b2565b50505050905090810190601f1680156104f75780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b34801561051b57600080fd5b50610524610c12565b604080519115158252519081900360200190f35b34801561054457600080fd5b5061054d610c18565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b34801561058257600080fd5b506102c7610c34565b34801561059757600080fd5b506105a0610cc4565b60408051918252519081900360200190f35b3480156105be57600080fd5b506105c7610cdd565b6040805160208082528351818301528351919283929083019185019080838360005b838110156106015781810151838201526020016105e9565b50505050905090810190601f16801561062e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561064857600080fd5b506102c76004803603602081101561065f57600080fd5b81019060208101813564010000000081111561067a57600080fd5b82018360208201111561068c57600080fd5b803590602001918460018302840111640100000000831117156106ae57600080fd5b509092509050610d6b565b6008546c010000000000000000000000000281565b60085474010000000000000000000000000000000000000000900460ff1681565b60005473ffffffffffffffffffffffffffffffffffffffff16331461071357600080fd5b600060085474010000000000000000000000000000000000000000900460ff16600381111561073e57fe5b1461074857600080fd5b600067ffffffffffffffff84161161075f57600080fd5b600067ffffffffffffffff8316101561077757600080fd5b61078360018c8c610f22565b5061079060028a8a610f22565b5061079d60038888610f22565b506107aa60048686610f22565b50600580546006805467ffffffffffffffff191667ffffffffffffffff868116919091179091557fffffffff0000000000000000ffffffffffffffffffffffffffffffffffffffff9091167401000000000000000000000000000000000000000091861682021773ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff8416179091556008805460019274ff000000000000000000000000000000000000000019909116908302179055505050505050505050505050565b60005473ffffffffffffffffffffffffffffffffffffffff16331461089c57600080fd5b600160085474010000000000000000000000000000000000000000900460ff1660038111156108c757fe5b146108d157600080fd5b6008805473ffffffffffffffffffffffffffffffffffffffff19166c010000000000000000000000008304178082556002919074ff000000000000000000000000000000000000000019167401000000000000000000000000000000000000000083021790555050565b6060806060600080606060008060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109de5780601f106109b3576101008083540402835291602001916109de565b820191906000526020600020905b8154815290600101906020018083116109c157829003601f168201915b505060028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152969e5091945092508401905082828015610a6c5780601f10610a4157610100808354040283529160200191610a6c565b820191906000526020600020905b815481529060010190602001808311610a4f57829003601f168201915b505060038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152969d5091945092508401905082828015610afd5780601f10610ad257610100808354040283529160200191610afd565b820191906000526020600020905b815481529060010190602001808311610ae057829003601f168201915b50506005546006546004805460408051602060026101006001861615026000190190941693909304601f8101849004840282018401909252818152989e507401000000000000000000000000000000000000000090940467ffffffffffffffff9081169d509092169a5094509092508401905082828015610bbf5780601f10610b9457610100808354040283529160200191610bbf565b820191906000526020600020905b815481529060010190602001808311610ba257829003601f168201915b50506005546008549c9d9b9c9a9b999a9899949873ffffffffffffffffffffffffffffffffffffffff909116975074010000000000000000000000000000000000000000900460ff169550929350505050565b60015b90565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff163314610c5857600080fd5b600260085474010000000000000000000000000000000000000000900460ff166003811115610c8357fe5b14610c8d57600080fd5b6008805474ff0000000000000000000000000000000000000000191674030000000000000000000000000000000000000000179055565b6007546002600019610100600184161502019091160490565b6007805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610d635780601f10610d3857610100808354040283529160200191610d63565b820191906000526020600020905b815481529060010190602001808311610d4657829003601f168201915b505050505081565b60005473ffffffffffffffffffffffffffffffffffffffff163314610d8f57600080fd5b600060085474010000000000000000000000000000000000000000900460ff166003811115610dba57fe5b14610dc457600080fd5b60078054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152610e929390929091830182828015610e515780601f10610e2657610100808354040283529160200191610e51565b820191906000526020600020905b815481529060010190602001808311610e3457829003601f168201915b505050505083838080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250610eab92505050565b8051610ea691600791602090910190610fa0565b505050565b815181516040518183018082526060939290916020601f8086018290049301049060005b83811015610eeb57600101602081028981015190830152610ecf565b5060005b82811015610f0d576001016020810288810151908701830152610eef565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f635782800160ff19823516178555610f90565b82800160010185558215610f90579182015b82811115610f90578235825591602001919060010190610f75565b50610f9c92915061100e565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fe157805160ff1916838001178555610f90565b82800160010185558215610f90579182015b82811115610f90578251825591602001919060010190610ff3565b610c1591905b80821115610f9c576000815560010161101456fea165627a7a72305820dfb124a1a682217f078b3d58c9ee91aed89e1fcb4dff1273341a34d8927cf3ac0029";

    public static final String FUNC_SECURERANDOMTOKEN = "secureRandomToken";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_STRUCTUREDDATA = "structuredData";

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

    protected StructuredDoc(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StructuredDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteCall<DynamicBytes> structuredData() {
        final Function function = new Function(FUNC_STRUCTUREDDATA,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function);
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
}
