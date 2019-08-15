package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060008054600160a060020a031916331781556007805460ff19169055610e0890819061003d90396000f3fe6080604052600436106100e55763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100ea578063200d2ed21461017457806324035582146101ad5780632ec9b6f8146101c257806341e461a01461024157806371316de3146103625780637834f239146103895780637d862fc21461039e5780638da5cb5b146103b35780638dd9fe19146103f15780638f281bd61461046e578063c06fad0614610483578063c452d99b14610498578063f2fde38b146104ad578063fc5dd056146104ed578063feffdcf61461056a575b600080fd5b3480156100f657600080fd5b506100ff61057f565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610139578181015183820152602001610121565b50505050905090810190601f1680156101665780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018057600080fd5b5061018961060c565b6040518082600181111561019957fe5b60ff16815260200191505060405180910390f35b3480156101b957600080fd5b506100ff610615565b3480156101ce57600080fd5b5061023f600480360360208110156101e557600080fd5b81019060208101813564010000000081111561020057600080fd5b82018360208201111561021257600080fd5b8035906020019184600183028401116401000000008311171561023457600080fd5b509092509050610670565b005b34801561024d57600080fd5b5061023f6004803603606081101561026457600080fd5b81019060208101813564010000000081111561027f57600080fd5b82018360208201111561029157600080fd5b803590602001918460018302840111640100000000831117156102b357600080fd5b9193909290916020810190356401000000008111156102d157600080fd5b8201836020820111156102e357600080fd5b8035906020019184600183028401116401000000008311171561030557600080fd5b91939092909160208101903564010000000081111561032357600080fd5b82018360208201111561033557600080fd5b8035906020019184600183028401116401000000008311171561035757600080fd5b509092509050610798565b34801561036e57600080fd5b5061037761089c565b60408051918252519081900360200190f35b34801561039557600080fd5b506100ff6108b6565b3480156103aa57600080fd5b50610377610911565b3480156103bf57600080fd5b506103c861092a565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156103fd57600080fd5b5061023f6004803603602081101561041457600080fd5b81019060208101813564010000000081111561042f57600080fd5b82018360208201111561044157600080fd5b8035906020019184600183028401116401000000008311171561046357600080fd5b509092509050610946565b34801561047a57600080fd5b506100ff6109fd565b34801561048f57600080fd5b506100ff610a58565b3480156104a457600080fd5b506100ff610ab0565b3480156104b957600080fd5b5061023f600480360360208110156104d057600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610b0b565b3480156104f957600080fd5b5061023f6004803603602081101561051057600080fd5b81019060208101813564010000000081111561052b57600080fd5b82018360208201111561053d57600080fd5b8035906020019184600183028401116401000000008311171561055f57600080fd5b509092509050610b8f565b34801561057657600080fd5b50610377610c46565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b820191906000526020600020905b8154815290600101906020018083116105e757829003601f168201915b505050505081565b60075460ff1681565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b60005473ffffffffffffffffffffffffffffffffffffffff16331461069457600080fd5b600060075460ff1660018111156106a757fe5b146106b157600080fd5b60048054604080516020601f6002600019610100600188161502019095169490940493840181900481028201810190925282815261077f939092909183018282801561073e5780601f106107135761010080835404028352916020019161073e565b820191906000526020600020905b81548152906001019060200180831161072157829003601f168201915b505050505083838080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250610c5f92505050565b805161079391600491602090910190610cd6565b505050565b60005473ffffffffffffffffffffffffffffffffffffffff1633146107bc57600080fd5b600060075460ff1660018111156107cf57fe5b146107d957600080fd5b6003546000600260001961010060018516150201909216919091041161086057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601d60248201527f54656d706c6174652066696c65206973206e6f742075706c6f61646564000000604482015290519081900360640190fd5b61086c60018787610d54565b5061087960028585610d54565b5061088660068383610d54565b50506007805460ff191660011790555050505050565b600454600260001961010060018416150201909116045b90565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b6003546002600019610100600184161502019091160490565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff16331461096a57600080fd5b600060075460ff16600181111561097d57fe5b1461098757600080fd5b60058054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526109e9939092909183018282801561073e5780601f106107135761010080835404028352916020019161073e565b805161079391600591602090910190610cd6565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106045780601f106105d957610100808354040283529160200191610604565b60005473ffffffffffffffffffffffffffffffffffffffff163314610b2f57600080fd5b73ffffffffffffffffffffffffffffffffffffffff811615610b8c57600080547fffffffffffffffffffffffff00000000000000000000000000000000000000001673ffffffffffffffffffffffffffffffffffffffff83161790555b50565b60005473ffffffffffffffffffffffffffffffffffffffff163314610bb357600080fd5b600060075460ff166001811115610bc657fe5b14610bd057600080fd5b60038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152610c32939092909183018282801561073e5780601f106107135761010080835404028352916020019161073e565b805161079391600391602090910190610cd6565b6005546002600019610100600184161502019091160490565b815181516040518183018082526060939290916020601f8086018290049301049060005b83811015610c9f57600101602081028981015190830152610c83565b5060005b82811015610cc1576001016020810288810151908701830152610ca3565b50928301602001604052509095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d1757805160ff1916838001178555610d44565b82800160010185558215610d44579182015b82811115610d44578251825591602001919060010190610d29565b50610d50929150610dc2565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d955782800160ff19823516178555610d44565b82800160010185558215610d44579182015b82811115610d44578235825591602001919060010190610da7565b6108b391905b80821115610d505760008155600101610dc856fea165627a7a723058202d057c4c6c6ce708c1c8b5814c6dd73fe49688082de7ed5afee73dece7529bb30029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_HEADERCONTENT = "headerContent";

    public static final String FUNC_MAINCONTENT = "mainContent";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_FOOTERCONTENT = "footerContent";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_FILETYPE = "fileType";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_PUSHMAINCONTENT = "pushMainContent";

    public static final String FUNC_PUSHHEADERCONTENT = "pushHeaderContent";

    public static final String FUNC_PUSHFOOTERCONTENT = "pushFooterContent";

    public static final String FUNC_INIT = "init";

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

    public RemoteCall<TransactionReceipt> init(Utf8String _name, Utf8String _items, Utf8String _fileType) {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.<Type>asList(_name, _items, _fileType),
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
