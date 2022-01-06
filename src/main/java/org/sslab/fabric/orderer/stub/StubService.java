package org.sslab.fabric.orderer.stub;


import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.runtime.CorfuRuntime;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.impl.NetworkImpl;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.peer.Chaincode;
import org.hyperledger.fabric.protos.peer.ProposalPackage;
import org.sslab.fabric.OrdereClient.TransactionImpl;
import org.sslab.fabric.corfu.CorfuAccess;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author jeyoungHwang
 */

public class StubService  {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final Log logger = LogFactory.getLog(StubService.class);
    Map<UUID, CorfuRuntime> runtimes;;
    Map<String, Long> lastReadAddrs;
    int nrRuntime;
    static long targetAddr;
    static CorfuAccess corfu_access = new CorfuAccess();

    public StubService() throws IOException {
        runtimes = new HashMap<UUID, CorfuRuntime>();
        lastReadAddrs = new HashMap<String, Long>();
        nrRuntime = 0;
        System.out.println("Init StubService");
    }

    public static void main(String[] args) throws Exception {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "edgechain", "bsp_210817_base", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
        Path networkConfigPath = Paths.get("..", "hyperledger", "fabric-testnets", "fabric-samples", "test-network","organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "woochang2").networkConfig(networkConfigPath).discovery(true);
        targetAddr = 0L;

        try(Gateway gateway = builder.connect()){
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            NetworkImpl network1 = (NetworkImpl) network;
            Contract contract = network.getContract("fabcar");

            Thread.sleep(3000);

            while(true) {
//                LogData logData = corfu_access.getLogData(targetAddr);
                Thread.sleep(500);
                System.out.println("log Read Start from targetAddr " + targetAddr);
                //read test용도
                Map<Long, ILogData> entries = corfu_access.getLogDataBatch(targetAddr);
                byte[] testStringBytes = entries.get(targetAddr).getTransactionEnvelope();
                String test = new String(testStringBytes);
                System.out.println(test);
//                List<Common.Envelope> envs = corfu_access.getFabricEnvelopes(targetAddr);
                TransactionImpl transaction = new TransactionImpl(network1);
                System.out.println(network1.toString());
//                contract.createTransaction("test").sendEnvelopes(envs);

//                transaction.sendEnvelopes(envs);
                targetAddr += entries.size();
//                targetAddr += envs.size();
            }
        }
    }


//    public static <T extends ProposalResponse> Collection<T> getProposalResponse(long targetAddr, Class<T> clazz) throws InvalidArgumentException, InvalidProtocolBufferException, ProposalException {
//        Common.Envelope env = null;
//        List<byte[]> metadata = corfu_access.getLogData(targetAddr);
//        env = Common.Envelope.parseFrom(metadata.get(0));
//
//        TransactionContext result = (TransactionContext) genson.deserialize(env.getPayload().toStringUtf8(), Object.class);
//        System.out.println("받아온 txcontext에서 추출한 채널명:" + result.getChannel());
//
//        ProposalPackage.SignedProposal resignedProposal = ProposalPackage.SignedProposal.newBuilder()
//                .setProposalBytes(signedProposal.getProposalBytes())
//                .setSignature(signedProposal.getSignature())
//                .build();
//        fabricResponse =  ProposalResponsePackage.ProposalResponse.parseFrom(metadata.get(1));
//        UnpackedProposal up =  unpackProposal(resignedProposal);
//
//        Constructor<? extends ProposalResponse> declaredConstructor;
//        try {
//            declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class, int.class, String.class);
//        } catch (NoSuchMethodException e) {
//            throw new InvalidArgumentException(e);
//        }
//
//        final String txID = up.channelHeader.getTxId();
//        String channelID = up.channelHeader.getChannelId();
//
//        Collection<T> proposalResponses = new ArrayList<>();
//            String message;
//            int status = 500;
//            try {
////                fabricResponse =  ProposalResponsePackage.ProposalResponse.parseFrom(metadata.get(1));
//                message = fabricResponse.getResponse().getMessage();
//                status = fabricResponse.getResponse().getStatus();
//                logger.info(format("Channel %s, transaction: %s got back from sharedlog status: %d, message: %s",
//                        channelID, txID, status, message));
//            } catch (Exception e) {
//                Throwable cause = e.getCause();
//                message = format("Channel %s sending proposal with transaction: %s failed because of: %s",
//                        channelID, txID, cause.getMessage());
//                e.printStackTrace();
//            }
//
//            ProposalResponse proposalResponse = null;
//            try {
//                proposalResponse = declaredConstructor.newInstance(up.channelHeader.getTxId(), up.channelHeader.getChannelId(), status, message);
//            } catch (Exception e) {
//                throw new InvalidArgumentException(e); // very unlikely to happen.
//            }
//
//            proposalResponse.setProposalResponse(fabricResponse);
//            proposalResponse.setProposal(resignedProposal);
//
//        proposalResponses.add((T) proposalResponse);
//        return proposalResponses;
//    }


    //sharedlog로 부터 일
//    private static <T extends ProposalResponse> Collection<T> getProposalResponses(Class<T> clazz) throws InvalidArgumentException, ProposalException {
//        ProposalResponsePackage.ProposalResponse fabricResponse = null;
//        Map<Long, ILogData> entries = corfu_access.getLogDataBatch();
//        Iterator<Long> keys = entries.keySet().iterator();
//
//        while(keys.hasNext()) {
//            long key = keys.next();
//            LogData logData = (LogData) entries.get(key);
//            fabricResponse = corfu_access.getFabricPR(logData);
//            ProposalPackage.SignedProposal signedProposal = corfu_access.getFabricProposal(logData);
//            UnpackedProposal up =  unpackProposal(signedProposal);
//
//            Constructor<? extends ProposalResponse> declaredConstructor;
//
//            try {
//                declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class, int.class, String.class);
//            } catch (NoSuchMethodException e) {
//                throw new InvalidArgumentException(e);
//            }
//            final String txID = up.channelHeader.getTxId();
//            String channelID = up.channelHeader.getChannelId();
//            Collection<T> proposalResponses = new ArrayList<>();
//            String message;
//            int status = 500;
//            try {
//                fabricResponse = corfu_access.getFabricPR(logData);
//                message = fabricResponse.getResponse().getMessage();
//                status = fabricResponse.getResponse().getStatus();
//                logger.debug(format("Channel %s, transaction: %s got back from sharedlog status: %d, message: %s",
//                        channelID, txID, status, message));
//            } catch (Exception e) {
//                Throwable cause = e.getCause();
//                message = format("Channel %s sending proposal with transaction: %s failed because of: %s",
//                        channelID, txID, cause.getMessage());
//                e.printStackTrace();
//            }
//
//            ProposalResponse proposalResponse = null;
//            try {
//                proposalResponse = declaredConstructor.newInstance(up.channelHeader.getTxId(), up.channelHeader.getChannelId(), status, message);
////                proposalResponse = declaredConstructor.newInstance(up.channelHeader.getTxId(), status, message);
//            } catch (Exception e) {
//                throw new InvalidArgumentException(e); // very unlikely to happen.
//            }
//
////            proposalResponse = new ProposalResponse(up.channelHeader.getTxId(), up.channelHeader.getChannelId(), status, message);
//            proposalResponse.setProposalResponse(fabricResponse);
//            proposalResponse.setProposal(signedProposal);
////            proposalResponse.setPeer(peerFuturePair.peer);
//
//
//            proposalResponses.add((T) proposalResponse);
//            return proposalResponses;
//        }
//    }

    @SneakyThrows
    public static UnpackedProposal unpackEnvelope(ProposalPackage.SignedProposal signedProposal) {
        ProposalPackage.Proposal proposal;
        Common.Header header;
        Common.ChannelHeader channelHeader;
        Common.SignatureHeader signatureHeader;
        ProposalPackage.ChaincodeHeaderExtension chaincodeHdrExt;
        ProposalPackage.ChaincodeProposalPayload payload;
        Chaincode.ChaincodeInvocationSpec chaincodeInvocationSpec;

//        byte[] sp = signedProposal.getProposalBytes().toByteArray();
        proposal = ProposalPackage.Proposal.parseFrom(signedProposal.getProposalBytes());
        header = Common.Header.parseFrom(proposal.getHeader());
        channelHeader = Common.ChannelHeader.parseFrom(header.getChannelHeader());
        signatureHeader = Common.SignatureHeader.parseFrom(header.getSignatureHeader());
        chaincodeHdrExt = ProposalPackage.ChaincodeHeaderExtension.parseFrom(channelHeader.getExtension());
        payload = ProposalPackage.ChaincodeProposalPayload.parseFrom(proposal.getPayload());
        chaincodeInvocationSpec = Chaincode.ChaincodeInvocationSpec.parseFrom(payload.getInput());

        UnpackedProposal unpackedProposal = new UnpackedProposal(chaincodeHdrExt.getChaincodeId().getName().toString(), channelHeader, chaincodeInvocationSpec.getChaincodeSpec().getInput(),
                proposal, signatureHeader, signedProposal, signedProposal.getProposalBytes());
        return unpackedProposal;
    }
//    public void joinChain(Sharedlog.Req_JoinChain req, StreamObserver<Sharedlog.Res_JoinChain> responseObserver) {
//        /* 한 machine 에서 동작하는 여러 peer는 하나의 stub을 공유할 수 있음.
//         * 따라서, peer 마다 joinChain message 를 stub 으로 보내면, stub은
//         * 1. shared log 와 이미 연결된 channel 인지 확인하고, 연결이 되어 있지 않다면 연결하는 과정
//         * 2. joinChain message 를 보낸 peer를 위한 last read addr 를 -1로 설정하는 과정
//         * 으로 이루어짐
//         */
//
//        String channelId = "mychannel";
////        String channelId = req.getChainId();
//        String peerName = req.getPeerName();
//        String CombinationKey = channelId + peerName;
//        System.out.println("[peer-stub] {joinChain} Channel ID: " + channelId + ", Peer Name: " + peerName);
//
//        UUID streamID = CorfuRuntime.getStreamID(channelId);
//
//        // channelID 가 mychannel1 인 경우 runtimeAddr 중 1번 항목에 연결. 즉 runtimeAddr[1]
//        // channelID 가 mychannel2 인 경우 runtimeAddr 중 2번 항목에 연결. 즉 runtimeAddr[2]
//        int channelIndex = Integer.parseInt(channelId.substring(9));
//
//        if(runtimes.get(streamID) == null){
//            CorfuRuntime runtime = new CorfuRuntime(runtimeAddr[channelIndex]).connect();
//            runtimes.put(streamID, runtime);
//            streamViews.put(streamID, runtime.getStreamsView().get(streamID));
//            System.out.println("[peer-stub] {joinChain} Corfu runtime is connected");
//        }
//        lastReadAddrs.put(CombinationKey, 1L);
//
//        Sharedlog.Res_JoinChain res = Sharedlog.Res_JoinChain.newBuilder().setSuccess(true).build();
//        responseObserver.onNext(res);
//        responseObserver.onCompleted();
//    }
}

