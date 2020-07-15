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
public class StructuredDoc extends Contract {
    private static final String BINARY = "0x60806040523480156200001157600080fd5b506040516200132738038062001327833981018060405260c08110156200003757600080fd5b8151602083015160408401516060850151608086018051949693959294919392830192916401000000008111156200006e57600080fd5b820160208101848111156200008257600080fd5b81516401000000008111828201871017156200009d57600080fd5b50509291906020018051640100000000811115620000ba57600080fd5b82016020810184811115620000ce57600080fd5b8151640100000000811182820187101715620000e957600080fd5b5050600080546001600160a01b0319163217905592505050856200011d5760088054600160a01b60ff0219169055620003da565b836001600160a01b03166306fdde036040518163ffffffff1660e01b815260040160006040518083038186803b1580156200015757600080fd5b505afa1580156200016c573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156200019657600080fd5b810190808051640100000000811115620001af57600080fd5b82016020810184811115620001c357600080fd5b8151640100000000811182820187101715620001de57600080fd5b50508051620001f9945060029350602090910191506200046f565b50600580546001600160a01b0319166001600160a01b038616179055604080516001600160a81b031988166020808301919091528251600b818403018152602b9092019092528051620002519260019201906200046f565b50600380546001600160a01b031916600888901c606090811b901c17905560058054600160a01b600160e01b03191674010000000000000000000000000000000000000000608088901b60c01c6001600160401b039081169190910291909117909155600680546001600160401b0319169187169190911790558151620002e09060049060208501906200046f565b50600880546001600160a01b031916606085901c1790556007805460408051602060026101006001861615026000190190941693909304601f81018490048402820184019092528181526200039b939092909190830182828015620003895780601f106200035d5761010080835404028352916020019162000389565b820191906000526020600020905b8154815290600101906020018083116200036b57829003601f168201915b505050505082620003e660201b60201c565b8051620003b1916007916020909101906200046f565b5060088054600160a01b60ff021916740200000000000000000000000000000000000000001790555b50505050505062000514565b8151815160609190810160006020601f8401049050600060208651601f01816200040c57fe5b04905060405183815260005b83811015620004365760010160208102898101519083015262000418565b5060005b828110156200045a5760010160208102888101519087018301526200043a565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620004b257805160ff1916838001178555620004e2565b82800160010185558215620004e2579182015b82811115620004e2578251825591602001919060010190620004c5565b50620004f0929150620004f4565b5090565b6200051191905b80821115620004f05760008155600101620004fb565b90565b610e0380620005246000396000f3fe608060405234801561001057600080fd5b50600436106100d45760003560e01c80638da5cb5b11610081578063e3f6618d1161005b578063e3f6618d146104be578063e648aa19146104d8578063eb87cdc114610555576100d4565b80638da5cb5b14610347578063b6549f751461036b578063db0b56eb14610373576100d4565b80633bc5de30116100b25780633bc5de301461015d57806354fd4d50146103015780638a330a401461032b576100d4565b80631a40ac37146100d9578063200d2ed2146101035780632f407a851461012f575b600080fd5b6100e16105c5565b604080516bffffffffffffffffffffffff199092168252519081900360200190f35b61010b6105ce565b6040518082600381111561011b57fe5b60ff16815260200191505060405180910390f35b61015b6004803603602081101561014557600080fd5b50356bffffffffffffffffffffffff19166105de565b005b610165610667565b604080516bffffffffffffffffffffffff1988169181019190915267ffffffffffffffff8087166060830152851660808201526001600160a01b03831660c0820152806020810160a0820160e083018560038111156101c057fe5b60ff16815260200184810384528c818151815260200191508051906020019080838360005b838110156101fd5781810151838201526020016101e5565b50505050905090810190601f16801561022a5780820380516001836020036101000a031916815260200191505b5084810383528b5181528b516020918201918d019080838360005b8381101561025d578181015183820152602001610245565b50505050905090810190601f16801561028a5780820380516001836020036101000a031916815260200191505b50848103825287518152875160209182019189019080838360005b838110156102bd5781810151838201526020016102a5565b50505050905090810190601f1680156102ea5780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b6103096108ae565b6040805160ff909316835263ffffffff90911660208301528051918290030190f35b6103336108b6565b604080519115158252519081900360200190f35b61034f6108bc565b604080516001600160a01b039092168252519081900360200190f35b61015b6108cb565b61015b600480360360e081101561038957600080fd5b8101906020810181356401000000008111156103a457600080fd5b8201836020820111156103b657600080fd5b803590602001918460018302840111640100000000831117156103d857600080fd5b9193909290916020810190356401000000008111156103f657600080fd5b82018360208201111561040857600080fd5b8035906020019184600183028401116401000000008311171561042a57600080fd5b919390926bffffffffffffffffffffffff198335169260408101906020013564010000000081111561045b57600080fd5b82018360208201111561046d57600080fd5b8035906020019184600183028401116401000000008311171561048f57600080fd5b9193509150803567ffffffffffffffff90811691602081013590911690604001356001600160a01b031661093d565b6104c6610a84565b60408051918252519081900360200190f35b6104e0610a9d565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561051a578181015183820152602001610502565b50505050905090810190601f1680156105475780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61015b6004803603602081101561056b57600080fd5b81019060208101813564010000000081111561058657600080fd5b82018360208201111561059857600080fd5b803590602001918460018302840111640100000000831117156105ba57600080fd5b509092509050610b2b565b60085460601b81565b600854600160a01b900460ff1681565b6000546001600160a01b031633146105f557600080fd5b6001600854600160a01b900460ff16600381111561060f57fe5b1461061957600080fd5b6008805473ffffffffffffffffffffffffffffffffffffffff1916606083901c178082556002919074ff00000000000000000000000000000000000000001916600160a01b83021790555050565b6060806000806000606060008060018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561070a5780601f106106df5761010080835404028352916020019161070a565b820191906000526020600020905b8154815290600101906020018083116106ed57829003601f168201915b505060028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152969e50919450925084019050828280156107985780601f1061076d57610100808354040283529160200191610798565b820191906000526020600020905b81548152906001019060200180831161077b57829003601f168201915b50505050509650600360009054906101000a900460601b9550600560149054906101000a900467ffffffffffffffff169450600660009054906101000a900467ffffffffffffffff16935060048054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108795780601f1061084e57610100808354040283529160200191610879565b820191906000526020600020905b81548152906001019060200180831161085c57829003601f168201915b50506005546008549c9d9b9c9a9b999a989994986001600160a01b039091169750600160a01b900460ff169550929350505050565b600260019091565b60015b90565b6000546001600160a01b031681565b6000546001600160a01b031633146108e257600080fd5b6002600854600160a01b900460ff1660038111156108fc57fe5b1461090657600080fd5b6008805474ff0000000000000000000000000000000000000000191674030000000000000000000000000000000000000000179055565b6000546001600160a01b0316331461095457600080fd5b6000600854600160a01b900460ff16600381111561096e57fe5b1461097857600080fd5b67ffffffffffffffff831661098c57600080fd5b61099860018b8b610cd1565b506109a560028989610cd1565b506003805473ffffffffffffffffffffffffffffffffffffffff1916606088901c1790556109d560048686610cd1565b50600580546006805467ffffffffffffffff191667ffffffffffffffff868116919091179091557fffffffff0000000000000000ffffffffffffffffffffffffffffffffffffffff909116600160a01b91861682021773ffffffffffffffffffffffffffffffffffffffff19166001600160a01b038416179091556008805460019274ff0000000000000000000000000000000000000000199091169083021790555050505050505050505050565b6007546002600019610100600184161502019091160490565b6007805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610b235780601f10610af857610100808354040283529160200191610b23565b820191906000526020600020905b815481529060010190602001808311610b0657829003601f168201915b505050505081565b6000546001600160a01b03163314610b4257600080fd5b6000600854600160a01b900460ff166003811115610b5c57fe5b14610b6657600080fd5b60078054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152610c349390929091830182828015610bf35780601f10610bc857610100808354040283529160200191610bf3565b820191906000526020600020905b815481529060010190602001808311610bd657829003601f168201915b505050505083838080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250610c4d92505050565b8051610c4891600791602090910190610d4f565b505050565b8151815160609190810160006020601f8401049050600060208651601f0181610c7257fe5b04905060405183815260005b83811015610c9a57600101602081028981015190830152610c7e565b5060005b82811015610cbc576001016020810288810151908701830152610c9e565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d125782800160ff19823516178555610d3f565b82800160010185558215610d3f579182015b82811115610d3f578235825591602001919060010190610d24565b50610d4b929150610dbd565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d9057805160ff1916838001178555610d3f565b82800160010185558215610d3f579182015b82811115610d3f578251825591602001919060010190610da2565b6108b991905b80821115610d4b5760008155600101610dc356fea165627a7a72305820d4843dca200b1e7f3c81a159459a50c4780fca848c5389f31a3d93f71c61b0f60029";

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

    public static final String FUNC_VERSION = "version";

    public static final List<TypeReference<?>> R_VALUES_DATA_LEGACY = Arrays.asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {});
    public static final List<TypeReference<?>> R_VALUES_DATA_V1 = Arrays.asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes20>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {});

    public static final List<TypeReference<?>> R_VALUES_VERSION = Arrays.asList(new TypeReference<Uint8>() {}, new TypeReference<Uint32>() {});

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

    public RemoteCall<TransactionReceipt> init(Utf8String _bankCode, Utf8String _documentName, Bytes20 _recipientId, Utf8String _recipientName, Uint64 _issueTimestamp, Uint64 _expireTimestamp, Address _templateId) {
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

    public RemoteCall<Tuple8<Utf8String, Utf8String, Bytes20, Uint64, Uint64, Utf8String, Address, Uint8>> getData() {
        final Function function = new Function(FUNC_GETDATA, Arrays.<Type>asList(), R_VALUES_DATA_V1);
        return new RemoteCall<Tuple8<Utf8String, Utf8String, Bytes20, Uint64, Uint64, Utf8String, Address, Uint8>>(
                new Callable<Tuple8<Utf8String, Utf8String, Bytes20, Uint64, Uint64, Utf8String, Address, Uint8>>() {
                    @Override
                    public Tuple8<Utf8String, Utf8String, Bytes20, Uint64, Uint64, Utf8String, Address, Uint8> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<Utf8String, Utf8String, Bytes20, Uint64, Uint64, Utf8String, Address, Uint8>(
                                (Utf8String) results.get(0),
                                (Utf8String) results.get(1),
                                (Bytes20) results.get(2),
                                (Uint64) results.get(3),
                                (Uint64) results.get(4),
                                (Utf8String) results.get(5),
                                (Address) results.get(6),
                                (Uint8) results.get(7));
                    }
                });
    }

    public RemoteCall<Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8>> getDataLegacy() {
        final Function function = new Function(FUNC_GETDATA, Arrays.<Type>asList(), R_VALUES_DATA_LEGACY);
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

    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Bytes32 _recipientInfo, Uint128 _timestamps, Address _templateId, Bytes20 _secureRandomToken, Utf8String _recipientName, DynamicBytes _structuredData) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _templateId, _secureRandomToken, _recipientName, _structuredData));
        return deployRemoteCall(StructuredDoc.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Bytes32 _recipientInfo, Uint128 _timestamps, Address _templateId, Bytes20 _secureRandomToken, Utf8String _recipientName, DynamicBytes _structuredData) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _templateId, _secureRandomToken, _recipientName, _structuredData));
        return deployRemoteCall(StructuredDoc.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Bytes32 _recipientInfo, Uint128 _timestamps, Address _templateId, Bytes20 _secureRandomToken, Utf8String _recipientName, DynamicBytes _structuredData) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _templateId, _secureRandomToken, _recipientName, _structuredData));
        return deployRemoteCall(StructuredDoc.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<StructuredDoc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Bytes32 _recipientInfo, Uint128 _timestamps, Address _templateId, Bytes20 _secureRandomToken, Utf8String _recipientName, DynamicBytes _structuredData) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_recipientInfo, _timestamps, _templateId, _secureRandomToken, _recipientName, _structuredData));
        return deployRemoteCall(StructuredDoc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
