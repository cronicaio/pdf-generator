package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class TemplateContract extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060008054600160a060020a0319163317815560078054604060020a60ff021916905561146a90819061004390396000f3fe6080604052600436106101065763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde03811461010b5780630aa9d8fb14610195578063200d2ed2146101ee57806324035582146102275780632ec9b6f81461023c5780632f2770db146102bb57806371316de3146102d05780637834f239146102f75780637d862fc21461030c5780638da5cb5b146103215780638dd9fe191461035f5780638f281bd6146103dc578063a3907d71146103f1578063c06fad0614610406578063c452d99b1461041b578063c5f5d80e14610430578063d89433b21461048c578063fc5dd056146105b7578063feffdcf614610634575b600080fd5b34801561011757600080fd5b50610120610649565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015a578181015183820152602001610142565b50505050905090810190601f1680156101875780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101a157600080fd5b506101aa6106d6565b6040805167ffffffffffffffff978816815295871660208701529386168585015291851660608501528416608084015290921660a082015290519081900360c00190f35b3480156101fa57600080fd5b5061020361073b565b6040518082600281111561021357fe5b60ff16815260200191505060405180910390f35b34801561023357600080fd5b50610120610750565b34801561024857600080fd5b506102b96004803603602081101561025f57600080fd5b81019060208101813564010000000081111561027a57600080fd5b82018360208201111561028c57600080fd5b803590602001918460018302840111640100000000831117156102ae57600080fd5b5090925090506107ab565b005b3480156102c757600080fd5b506102b9610967565b3480156102dc57600080fd5b506102e5610a63565b60408051918252519081900360200190f35b34801561030357600080fd5b50610120610a7d565b34801561031857600080fd5b506102e5610ad8565b34801561032d57600080fd5b50610336610af1565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b34801561036b57600080fd5b506102b96004803603602081101561038257600080fd5b81019060208101813564010000000081111561039d57600080fd5b8201836020820111156103af57600080fd5b803590602001918460018302840111640100000000831117156103d157600080fd5b509092509050610b0d565b3480156103e857600080fd5b50610120610c58565b3480156103fd57600080fd5b506102b9610cb3565b34801561041257600080fd5b50610120610dac565b34801561042757600080fd5b50610120610e04565b34801561043c57600080fd5b506102b9600480360360c081101561045357600080fd5b5067ffffffffffffffff8135811691602081013582169160408201358116916060810135821691608082013581169160a0013516610e5f565b34801561049857600080fd5b506102b9600480360360808110156104af57600080fd5b8101906020810181356401000000008111156104ca57600080fd5b8201836020820111156104dc57600080fd5b803590602001918460018302840111640100000000831117156104fe57600080fd5b91939092909160208101903564010000000081111561051c57600080fd5b82018360208201111561052e57600080fd5b8035906020019184600183028401116401000000008311171561055057600080fd5b91939092909160208101903564010000000081111561056e57600080fd5b82018360208201111561058057600080fd5b803590602001918460018302840111640100000000831117156105a257600080fd5b91935091503567ffffffffffffffff16610f93565b3480156105c357600080fd5b506102b9600480360360208110156105da57600080fd5b8101906020810181356401000000008111156105f557600080fd5b82018360208201111561060757600080fd5b8035906020019184600183028401116401000000008311171561062957600080fd5b50909250905061115d565b34801561064057600080fd5b506102e56112a8565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b820191906000526020600020905b8154815290600101906020018083116106b157829003601f168201915b505050505081565b60085460095467ffffffffffffffff808316926801000000000000000080820483169370010000000000000000000000000000000083048416937801000000000000000000000000000000000000000000000000909304831692818116929091041686565b60075468010000000000000000900460ff1681565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b60005473ffffffffffffffffffffffffffffffffffffffff1633146107cf57600080fd5b600060075468010000000000000000900460ff1660028111156107ee57fe5b1461088057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f5374617465206f6620636f6e7472616374206d7573742062652043524541544560448201527f4400000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60048054604080516020601f6002600019610100600188161502019095169490940493840181900481028201810190925282815261094e939092909183018282801561090d5780601f106108e25761010080835404028352916020019161090d565b820191906000526020600020905b8154815290600101906020018083116108f057829003601f168201915b505050505083838080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152506112c192505050565b805161096291600491602090910190611338565b505050565b60005473ffffffffffffffffffffffffffffffffffffffff16331461098b57600080fd5b600160075468010000000000000000900460ff1660028111156109aa57fe5b14610a3c57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602360248201527f5374617465206f6620636f6e7472616374206d7573742062652046494e414c4960448201527f5a45440000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600780546002919068ff0000000000000000191668010000000000000000835b0217905550565b600454600260001961010060018416150201909116045b90565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b6003546002600019610100600184161502019091160490565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff163314610b3157600080fd5b600060075468010000000000000000900460ff166002811115610b5057fe5b14610be257604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f5374617465206f6620636f6e7472616374206d7573742062652043524541544560448201527f4400000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60058054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152610c44939092909183018282801561090d5780601f106108e25761010080835404028352916020019161090d565b805161096291600591602090910190611338565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b60005473ffffffffffffffffffffffffffffffffffffffff163314610cd757600080fd5b600260075468010000000000000000900460ff166002811115610cf657fe5b14610d8857604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602260248201527f5374617465206f6620636f6e7472616374206d7573742062652044495341424c60448201527f4544000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600780546001919068ff000000000000000019166801000000000000000083610a5c565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106ce5780601f106106a3576101008083540402835291602001916106ce565b60005473ffffffffffffffffffffffffffffffffffffffff163314610e8357600080fd5b6040805160c08101825267ffffffffffffffff9788168082529688166020820181905295881691810182905293871660608501819052928716608085018190529190961660a090930183905260088054780100000000000000000000000000000000000000000000000090930277ffffffffffffffffffffffffffffffffffffffffffffffff7001000000000000000000000000000000009098027fffffffffffffffff0000000000000000ffffffffffffffffffffffffffffffff680100000000000000009788026fffffffffffffffff00000000000000001967ffffffffffffffff19978816909a178a16171617979097169690961790955560098054939092029216909317909116179055565b60005473ffffffffffffffffffffffffffffffffffffffff163314610fb757600080fd5b600060075468010000000000000000900460ff166002811115610fd657fe5b1461106857604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f5374617465206f6620636f6e7472616374206d7573742062652043524541544560448201527f4400000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600354600060026000196101006001851615020190921691909104116110ef57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601d60248201527f54656d706c6174652066696c65206973206e6f742075706c6f61646564000000604482015290519081900360640190fd5b6110fb600188886113b6565b50611108600286866113b6565b50611115600684846113b6565b506007805467ffffffffffffffff191667ffffffffffffffff8316178082556001919068ff000000000000000019166801000000000000000083021790555050505050505050565b60005473ffffffffffffffffffffffffffffffffffffffff16331461118157600080fd5b600060075468010000000000000000900460ff1660028111156111a057fe5b1461123257604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f5374617465206f6620636f6e7472616374206d7573742062652043524541544560448201527f4400000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152611294939092909183018282801561090d5780601f106108e25761010080835404028352916020019161090d565b805161096291600391602090910190611338565b6005546002600019610100600184161502019091160490565b815181516040518183018082526060939290916020601f8086018290049301049060005b83811015611301576001016020810289810151908301526112e5565b5060005b82811015611323576001016020810288810151908701830152611305565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061137957805160ff19168380011785556113a6565b828001600101855582156113a6579182015b828111156113a657825182559160200191906001019061138b565b506113b2929150611424565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106113f75782800160ff198235161785556113a6565b828001600101855582156113a6579182015b828111156113a6578235825591602001919060010190611409565b610a7a91905b808211156113b2576000815560010161142a56fea165627a7a72305820a881fbbd207ec21372f395f6824eeb742841f9f55ae2933c958d9df7ee6b8d900029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_WKHTMLTOPDFSETTINGS = "wkhtmltopdfSettings";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_HEADERCONTENT = "headerContent";

    public static final String FUNC_MAINCONTENT = "mainContent";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_EXPIRETIMESTAMP = "expireTimestamp";

    public static final String FUNC_FOOTERCONTENT = "footerContent";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_FILETYPE = "fileType";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_PUSHMAINCONTENT = "pushMainContent";

    public static final String FUNC_PUSHHEADERCONTENT = "pushHeaderContent";

    public static final String FUNC_PUSHFOOTERCONTENT = "pushFooterContent";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_INITWKHTMLTOPDF = "initWkhtmltopdf";

    public static final String FUNC_ENABLE = "enable";

    public static final String FUNC_DISABLE = "disable";

    public static final String FUNC_GETMAINCONTENTARRAYLENGTH = "getMainContentArrayLength";

    public static final String FUNC_GETHEADERCONTENTARRAYLENGTH = "getHeaderContentArrayLength";

    public static final String FUNC_GETFOOTERCONTENTARRAYLENGTH = "getFooterContentArrayLength";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected TemplateContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TemplateContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TemplateContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TemplateContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Utf8String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Tuple6<Uint64, Uint64, Uint64, Uint64, Uint64, Uint64>> wkhtmltopdfSettings() {
        final Function function = new Function(FUNC_WKHTMLTOPDFSETTINGS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}));
        return new RemoteCall<Tuple6<Uint64, Uint64, Uint64, Uint64, Uint64, Uint64>>(
                new Callable<Tuple6<Uint64, Uint64, Uint64, Uint64, Uint64, Uint64>>() {
                    @Override
                    public Tuple6<Uint64, Uint64, Uint64, Uint64, Uint64, Uint64> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<Uint64, Uint64, Uint64, Uint64, Uint64, Uint64>(
                                (Uint64) results.get(0),
                                (Uint64) results.get(1),
                                (Uint64) results.get(2),
                                (Uint64) results.get(3),
                                (Uint64) results.get(4),
                                (Uint64) results.get(5));
                    }
                });
    }

    public RemoteCall<Uint8> status() {
        final Function function = new Function(FUNC_STATUS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<DynamicBytes> headerContent() {
        final Function function = new Function(FUNC_HEADERCONTENT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> headerContentOld(Uint256 param0) {
        final Function function = new Function(FUNC_HEADERCONTENT,
                Arrays.<Type>asList(param0),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<DynamicBytes> mainContent() {
        final Function function = new Function(FUNC_MAINCONTENT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> mainContentOld(Uint256 param0) {
        final Function function = new Function(FUNC_MAINCONTENT,
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

    public RemoteCall<DynamicBytes> footerContent() {
        final Function function = new Function(FUNC_FOOTERCONTENT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> footerContentOld(Uint256 param0) {
        final Function function = new Function(FUNC_FOOTERCONTENT,
                Arrays.<Type>asList(param0),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> items() {
        final Function function = new Function(FUNC_ITEMS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> fileType() {
        final Function function = new Function(FUNC_FILETYPE,
                Arrays.<Type>asList(),
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

    public RemoteCall<TransactionReceipt> pushMainContent(DynamicBytes _contentPiece) {
        final Function function = new Function(
                FUNC_PUSHMAINCONTENT,
                Arrays.<Type>asList(_contentPiece),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pushHeaderContent(DynamicBytes _contentPiece) {
        final Function function = new Function(
                FUNC_PUSHHEADERCONTENT,
                Arrays.<Type>asList(_contentPiece),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pushFooterContent(DynamicBytes _contentPiece) {
        final Function function = new Function(
                FUNC_PUSHFOOTERCONTENT,
                Arrays.<Type>asList(_contentPiece),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint64> expireTimestamp() {
        final Function function = new Function(FUNC_EXPIRETIMESTAMP,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> init(Utf8String _name, Utf8String _items, Utf8String _fileType, Uint64 _expireTimestamp) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.<Type>asList(_name, _items, _fileType, _expireTimestamp),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initWkhtmltopdf(Uint64 _marginBottom, Uint64 _marginTop, Uint64 _marginLeft, Uint64 _marginRight, Uint64 _headerSpacing, Uint64 _footerSpacing) {
        final Function function = new Function(
                FUNC_INITWKHTMLTOPDF,
                Arrays.<Type>asList(_marginBottom, _marginTop, _marginLeft, _marginRight, _headerSpacing, _footerSpacing),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> enable() {
        final Function function = new Function(
                FUNC_ENABLE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> disable() {
        final Function function = new Function(
                FUNC_DISABLE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> getMainContentArrayLength() {
        final Function function = new Function(FUNC_GETMAINCONTENTARRAYLENGTH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getHeaderContentArrayLength() {
        final Function function = new Function(FUNC_GETHEADERCONTENTARRAYLENGTH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getFooterContentArrayLength() {
        final Function function = new Function(FUNC_GETFOOTERCONTENTARRAYLENGTH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static TemplateContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemplateContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TemplateContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemplateContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TemplateContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TemplateContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TemplateContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TemplateContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TemplateContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemplateContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<TemplateContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemplateContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemplateContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemplateContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemplateContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemplateContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}