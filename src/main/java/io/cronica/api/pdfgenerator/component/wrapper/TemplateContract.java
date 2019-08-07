package io.cronica.api.pdfgenerator.component.wrapper;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
    private static final String BINARY = "0x608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600760006101000a81548160ff0219169083600181111561007057fe5b02179055506112d8806100846000396000f3fe6080604052600436106100e6576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100eb578063200d2ed21461017b57806341e461a0146101b45780635f29bd4b146102e457806371316de31461036a5780637d862fc2146103955780638da5cb5b146103c05780639ed240cd14610417578063a1540a7c1461049d578063b269eb9114610523578063bed15a2f146105d7578063c06fad061461068b578063c452d99b1461071b578063f2fde38b146107ab578063f6135f00146107fc578063feffdcf6146108b0575b600080fd5b3480156100f757600080fd5b506101006108db565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610140578082015181840152602081019050610125565b50505050905090810190601f16801561016d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018757600080fd5b50610190610979565b604051808260018111156101a057fe5b60ff16815260200191505060405180910390f35b3480156101c057600080fd5b506102e2600480360360608110156101d757600080fd5b81019080803590602001906401000000008111156101f457600080fd5b82018360208201111561020657600080fd5b8035906020019184600183028401116401000000008311171561022857600080fd5b90919293919293908035906020019064010000000081111561024957600080fd5b82018360208201111561025b57600080fd5b8035906020019184600183028401116401000000008311171561027d57600080fd5b90919293919293908035906020019064010000000081111561029e57600080fd5b8201836020820111156102b057600080fd5b803590602001918460018302840111640100000000831117156102d257600080fd5b909192939192939050505061098c565b005b3480156102f057600080fd5b506103686004803603602081101561030757600080fd5b810190808035906020019064010000000081111561032457600080fd5b82018360208201111561033657600080fd5b8035906020019184600183028401116401000000008311171561035857600080fd5b9091929391929390505050610afa565b005b34801561037657600080fd5b5061037f610bcf565b6040518082815260200191505060405180910390f35b3480156103a157600080fd5b506103aa610bdc565b6040518082815260200191505060405180910390f35b3480156103cc57600080fd5b506103d5610be9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561042357600080fd5b5061049b6004803603602081101561043a57600080fd5b810190808035906020019064010000000081111561045757600080fd5b82018360208201111561046957600080fd5b8035906020019184600183028401116401000000008311171561048b57600080fd5b9091929391929390505050610c0e565b005b3480156104a957600080fd5b50610521600480360360208110156104c057600080fd5b81019080803590602001906401000000008111156104dd57600080fd5b8201836020820111156104ef57600080fd5b8035906020019184600183028401116401000000008311171561051157600080fd5b9091929391929390505050610ce3565b005b34801561052f57600080fd5b5061055c6004803603602081101561054657600080fd5b8101908080359060200190929190505050610db8565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561059c578082015181840152602081019050610581565b50505050905090810190601f1680156105c95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156105e357600080fd5b50610610600480360360208110156105fa57600080fd5b8101908080359060200190929190505050610e73565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610650578082015181840152602081019050610635565b50505050905090810190601f16801561067d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561069757600080fd5b506106a0610f2e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156106e05780820151818401526020810190506106c5565b50505050905090810190601f16801561070d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561072757600080fd5b50610730610fcc565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610770578082015181840152602081019050610755565b50505050905090810190601f16801561079d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156107b757600080fd5b506107fa600480360360208110156107ce57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061106a565b005b34801561080857600080fd5b506108356004803603602081101561081f57600080fd5b810190808035906020019092919050505061113f565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561087557808201518184015260208101905061085a565b50505050905090810190601f1680156108a25780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156108bc57600080fd5b506108c56111fa565b6040518082815260200191505060405180910390f35b60018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109715780601f1061094657610100808354040283529160200191610971565b820191906000526020600020905b81548152906001019060200180831161095457829003601f168201915b505050505081565b600760009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156109e757600080fd5b600060018111156109f457fe5b600760009054906101000a900460ff166001811115610a0f57fe5b141515610a1b57600080fd5b6000600380549050111515610a98576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f54656d706c6174652066696c65206973206e6f742075706c6f6164656400000081525060200191505060405180910390fd5b858560019190610aa9929190611207565b50838360029190610abb929190611207565b50818160069190610acd929190611207565b506001600760006101000a81548160ff02191690836001811115610aed57fe5b0217905550505050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610b5557600080fd5b60006001811115610b6257fe5b600760009054906101000a900460ff166001811115610b7d57fe5b141515610b8957600080fd5b6003828290918060018154018082558091505090600182039060005260206000200160009091929390919293909192909192509190610bc9929190611207565b50505050565b6000600480549050905090565b6000600380549050905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c6957600080fd5b60006001811115610c7657fe5b600760009054906101000a900460ff166001811115610c9157fe5b141515610c9d57600080fd5b6004828290918060018154018082558091505090600182039060005260206000200160009091929390919293909192909192509190610cdd929190611207565b50505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610d3e57600080fd5b60006001811115610d4b57fe5b600760009054906101000a900460ff166001811115610d6657fe5b141515610d7257600080fd5b6005828290918060018154018082558091505090600182039060005260206000200160009091929390919293909192909192509190610db2929190611207565b50505050565b600381815481101515610dc757fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e6b5780601f10610e4057610100808354040283529160200191610e6b565b820191906000526020600020905b815481529060010190602001808311610e4e57829003601f168201915b505050505081565b600581815481101515610e8257fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f265780601f10610efb57610100808354040283529160200191610f26565b820191906000526020600020905b815481529060010190602001808311610f0957829003601f168201915b505050505081565b60028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610fc45780601f10610f9957610100808354040283529160200191610fc4565b820191906000526020600020905b815481529060010190602001808311610fa757829003601f168201915b505050505081565b60068054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110625780601f1061103757610100808354040283529160200191611062565b820191906000526020600020905b81548152906001019060200180831161104557829003601f168201915b505050505081565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156110c557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151561113c57806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50565b60048181548110151561114e57fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111f25780601f106111c7576101008083540402835291602001916111f2565b820191906000526020600020905b8154815290600101906020018083116111d557829003601f168201915b505050505081565b6000600580549050905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061124857803560ff1916838001178555611276565b82800160010185558215611276579182015b8281111561127557823582559160200191906001019061125a565b5b5090506112839190611287565b5090565b6112a991905b808211156112a557600081600090555060010161128d565b5090565b9056fea165627a7a723058205ab28f422b74c1f3cb3f6929641134606c5649ca2d118f8c5a47f7b266a1467a0029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_MAINCONTENT = "mainContent";

    public static final String FUNC_FOOTERCONTENT = "footerContent";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_FILETYPE = "fileType";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_HEADERCONTENT = "headerContent";

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

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> mainContent(Uint256 param0) {
        final Function function = new Function(FUNC_MAINCONTENT, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> footerContent(Uint256 param0) {
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

    public RemoteCall<Utf8String> headerContent(Uint256 param0) {
        final Function function = new Function(FUNC_HEADERCONTENT, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> pushMainContent(Utf8String _contentPiece) {
        final Function function = new Function(
                FUNC_PUSHMAINCONTENT, 
                Arrays.<Type>asList(_contentPiece), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pushHeaderContent(Utf8String _contentPiece) {
        final Function function = new Function(
                FUNC_PUSHHEADERCONTENT, 
                Arrays.<Type>asList(_contentPiece), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pushFooterContent(Utf8String _contentPiece) {
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
