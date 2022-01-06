package org.sslab.fabric.corfu;

import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.protocols.wireprotocol.LogData;
import org.corfudb.protocols.wireprotocol.Token;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.collections.CorfuTable;
import org.corfudb.runtime.object.transactions.OptimisticTransactionalContext;
import org.corfudb.runtime.object.transactions.TransactionalContext;
import org.corfudb.runtime.view.AddressSpaceView;
import org.corfudb.runtime.view.StreamsView;
import org.corfudb.runtime.view.stream.IStreamView;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.peer.ProposalPackage;
import org.hyperledger.fabric.protos.peer.ProposalResponsePackage;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.sslab.fabric.orderer.stub.UnpackedProposal;


import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * @author Jeyoung Hwang.capricorn116@postech.ac.kr
 *         created on 2021. 9. 28.
 */
public class CorfuAccess {
    Map<UUID, CorfuRuntime> runtimes;
    Map<UUID, IStreamView> streamViews;
    Map<String, Long> lastReadAddrs;
    //tokenMap key: fabric txID, value: access token
    Map<String, Token> tokenMap;
    static long targetAddr;

    public CorfuAccess() {
        streamViews = new HashMap<UUID, IStreamView>();
        runtimes = new HashMap<UUID, CorfuRuntime>();
//        runtime = new CorfuRuntime(runtimeAddr[0]).connect();
        lastReadAddrs = new HashMap<String, Long>();
//        System.out.println("Init AdapterModuleService");
        tokenMap = new HashMap<String, Token>();
        targetAddr = 0L;
    }

    static CorfuRuntime runtime =  getRuntimeAndConnect("141.223.181.51:12011");
    static AddressSpaceView addressSpaceView = runtime.getAddressSpaceView();
    private final Logger logger = Logger.getLogger(CorfuAccess.class.getName());

    private static CorfuRuntime getRuntimeAndConnect(String configurationString) {
        CorfuRuntime corfuRuntime = new CorfuRuntime(configurationString).connect();
        return corfuRuntime;
    }

    public LogData getLogData(long targetAddr) {
        LogData logData;
        logData = (LogData) addressSpaceView.read(targetAddr);
        if (logData == null) {
            logger.info("[CorfuAccess] {getLogData}: logData is null, FAILED");
            return null;
        }
        logger.info("[CorfuAccess] {getLogData}: logData is available, SUCCESS. target seek addr: " + targetAddr);

        return logData;
    }
    
    public Map<Long, ILogData> getLogDataBatch(long targetAddr) {
        long tailAddr = runtime.getAddressSpaceView().getLogTail();
//        if(targetAddr > tailAddr) return null;

        List<Long> addresses = new ArrayList<>();
        //query할 address list 생성
//        while (this.targetAddr <= tailAddr && addresses.size() < 1000) {
//            addresses.add(this.targetAddr);
//            this.targetAddr++;
//        }
        //query할 address list 생성
        while (targetAddr <= tailAddr && addresses.size() < 5000) {
            addresses.add(targetAddr);
            targetAddr++;
        }
        //map data read
        Map<Long, ILogData> entries;
        entries = addressSpaceView.read(addresses);

        for(long i : addresses) {
            long addr =  entries.get(i).getGlobalAddress();
            byte[] temp =  entries.get(i).getTransactionEnvelope();
            String txenv = new String(temp);
            System.out.println("[CorfuAccess] {getLogDataBatch} target seek addr: " + addr + txenv);
        }

        System.out.println("[CorfuAccess] {getLogDataBatch}: logData entries are available, SUCCESS");

        return entries;
    }

    @SneakyThrows
    public List<Common.Envelope> getFabricEnvelopes(long targetAddr) {
        Map<Long, ILogData> entries = getLogDataBatch(targetAddr);
        Iterator<Long> keys = entries.keySet().iterator();
        List<Common.Envelope> envs = new ArrayList<>();
        while(keys.hasNext()) {
            long key = keys.next();
            byte[] envelopeBytes = entries.get(key).getTransactionEnvelope();
            if(envelopeBytes == null) {
                logger.info("[CorfuAccess] {getFabricEnvelop}: envelopeBytes is null, FAILED");
                return null;
            }
            Common.Envelope env = Common.Envelope.parseFrom(envelopeBytes);
            envs.add(env);
        }
//        for (long i = targetAddr; i < targetAddr + entries.size(); i++) {
//
//            byte[] envelopeBytes = entries.get(i).getTransactionEnvelope();
//            if(envelopeBytes == null) {
//                logger.info("[CorfuAccess] {getFabricEnvelop}: envelopeBytes is null, FAILED");
//                return null;
//            }
//            Common.Envelope env = Common.Envelope.parseFrom(envelopeBytes);
//            envs.add(env);
//        }
        logger.info("[CorfuAccess] {getFabricEnvelop}: envelopeBytes is available, SUCCESS");
        return envs;
    }
}

