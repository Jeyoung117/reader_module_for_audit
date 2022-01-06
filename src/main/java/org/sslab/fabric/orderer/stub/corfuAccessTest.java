///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.sslab.fabric.orderer.stub;
//
//
//import com.google.common.reflect.TypeToken;
//import org.corfudb.protocols.logprotocol.LogEntry;
//import org.corfudb.protocols.logprotocol.MultiObjectSMREntry;
//import org.corfudb.protocols.logprotocol.MultiSMREntry;
//import org.corfudb.protocols.wireprotocol.ILogData;
//import org.corfudb.protocols.wireprotocol.IMetadata;
//import org.corfudb.protocols.wireprotocol.LogData;
//import org.corfudb.runtime.CorfuRuntime;
//import org.corfudb.runtime.collections.CorfuTable;
//import org.corfudb.runtime.object.transactions.OptimisticTransactionalContext;
//import org.corfudb.runtime.object.transactions.TransactionalContext;
//import org.corfudb.runtime.view.AddressSpaceView;
//import org.corfudb.runtime.view.StreamsView;
//import org.corfudb.runtime.view.stream.IStreamView;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// *
// * @author jeyoungHwang
// */
//
//public class corfuAccessTest {
//    //    CorfuRuntime runtime;
//    Map<UUID, CorfuRuntime> runtimes;
//    Map<UUID, IStreamView> streamViews;
//    Map<String, Long> lastReadAddrs;
//    String[] runtimeAddr = {
//            "141.223.121.251:12011"
//    };
//    int nrRuntime;
//
//    public corfuAccessTest() {
//        streamViews = new HashMap<UUID, IStreamView>();
//        runtimes = new HashMap<UUID, CorfuRuntime>();
////        runtime = new CorfuRuntime(runtimeAddr[0]).connect();
//        lastReadAddrs = new HashMap<String, Long>();
//        nrRuntime = 0;
//        System.out.println("Init StubService");
//    }
//
//    private static CorfuRuntime getRuntimeAndConnect(String configurationString) {
//        CorfuRuntime corfuRuntime = new CorfuRuntime(configurationString).connect();
//        return corfuRuntime;
//    }
//     static CorfuRuntime runtime = getRuntimeAndConnect("141.223.121.139:12011");
//
//
//    public static void main(String args[]) {
////         next();
//        long appendedAddr = 0L;
////    long appendedAddr = mapInputTest();
//        logSearch(appendedAddr);
//  }
//
//    static public void logSearch(long appendedAddr) {
////        CorfuRuntime runtime = getRuntimeAndConnect("141.223.121.251:12011");
//        long targetAddr = appendedAddr;
//        UUID streamID = runtime.getStreamID("mychannel + fabcar"); //추후 실제 channel ID로 변
//        //views initialization
//        AddressSpaceView addressSpaceView = runtime.getAddressSpaceView();
//        IStreamView iStreamView = runtime.getStreamsView().get(streamID);
//        StreamsView streamsView = new StreamsView(runtime);
//
//        ILogData ilogData = addressSpaceView.read(targetAddr);
//        LogData logData = (LogData) ilogData;
//
////        iStreamView.seek(3L);
////        streamsView.get(streamID).seek(4L);
//        LogEntry logEntryfromilogdata = ilogData.getLogEntry(runtime);
//        LogEntry logEntryfromlogdata = logData.getLogEntry(runtime);
//        LogEntry logEntryfromlogdata1 = (LogEntry) logData.getPayload(runtime);
//        IMetadata iMetadata = (IMetadata) ilogData;
//
//        MultiObjectSMREntry multiObjectSMREntry = (MultiObjectSMREntry) ilogData.getPayload(runtime);
//        MultiSMREntry  multiSMREntry = multiObjectSMREntry.getEntryMap().entrySet().iterator().next().getValue();
//        iMetadata.getTransactionMetadata();
//
//        System.out.println("////////////////////////////////////////////////////////////////////////");
//        System.out.println("////////////////////////////////////////////////////////////////////////");
//        System.out.println("////////////////////////////////////////////////////////////////////////");
//        System.out.println("////////////////////////////////////////////////////////////////////////");
//
//        System.out.println(targetAddr +  " log 조사 시작");
//        System.out.println("[corfu-access-interface] {commitTransaction} targetAddr: " + targetAddr);
//        System.out.println("ilogData: " + ((LogData) ilogData).getData());
//        System.out.println("logData: " + logData.getData());
////        streamsView.get(streamID).seek(3L);
////        System.out.println("logData: " + iStreamView.next());
////        LogData logData1 = (LogData) iStreamView.next();
//
//        System.out.println("ilogData size: " + ilogData.getSizeEstimate());
////        System.out.println("logData size: " + iStreamView.next().getSizeEstimate());
//
////        System.out.println("logEntry from ilogData: " + iStreamView.next().getSizeEstimate());
//        System.out.println("logEntry from logData: " + logEntryfromlogdata1);
//
////        System.out.println("txMetadata from logData: " + multiObjectSMREntry.getTxMetadata());
//        System.out.println("TxMetadata from logData: " + ilogData.getTransactionMetadata());
//        String pr = new String(ilogData.getTransactionMetadata());
//        System.out.println("TxMetadata from metadata: " + pr);
//        System.out.println("fabric proposal from logData: " + ilogData.getFabricProposal());
//        String pp = new String(ilogData.getFabricProposal());
//        System.out.println("fabric proposal from metadata: " + pp);
//
//
//
////        System.out.println("txMetadata: " + ilogData.getPayload(runtime));
////        System.out.println("ilogData.getSerializedForm: " + ilogData.getSerializedForm().getSerialized());
////        System.out.println("logData: " + multiObjectSMREntry.getSMRUpdates(streamID));
////        System.out.println("multiSMREntry.getSMRUpdates(streamID;: " + multiSMREntry.getSMRUpdates(streamID).get(0));
////        SMREntry smrEntry =  multiSMREntry.getSMRUpdates(streamID).get(0);
//
////        System.out.println("smrEntry.getSMRArguments(): " + smrEntry.getSMRArguments());
////        Object[] ob = Arrays.stream(smrEntry.getSMRArguments()).toArray();
//////        Car car =
////        System.out.println("smrEntry.getSMRArguments(): " + ob[0] + ob[1]);
////        String temp = new String((byte[]) ob[1]);
////        System.out.println("value: " + temp);
////        System.out.println("key: " + ob[0]);
//    }
//
//    static public long  mapInputTest() {
////        CorfuRuntime runtime = getRuntimeAndConnect("141.223.121.251:13011");
//        Map<String, byte[]> map1 = runtime.getObjectsView()
//                .build()
//                .setStreamName("mychannel + fabcar")
//                .setTypeToken(new TypeToken<CorfuTable<String, byte[]>>() {
//                })
//                .open();
//
//        Map<String, byte[]> map2 = runtime.getObjectsView()
//                .build()
//                .setStreamName("mychannel + smallbank")
//                .setTypeToken(new TypeToken<CorfuTable<String, byte[]>>() {
//                })
//                .open();
//
//        runtime.getObjectsView().TXBegin();
//
////        byte[] CAR69 = map.get("CAR69");
//        String CAR69 = "CAR69";
//        String syncTest = "1";
//        byte[] syncTestByte = syncTest.getBytes();
//
//        if (CAR69 == null) {
//            System.out.println("비어음");
//            map1.put("CAR69", syncTestByte);
//        }
//        else {
//            String car = new String(CAR69);
//            System.out.println("기존에 저장되어 있던 car value:" + car);
//            map1.put("CAR69", syncTestByte);
//            map1.put("CAR68", syncTestByte);
//        }
//        map2.put("capricorn116", syncTestByte);
//
//
//        OptimisticTransactionalContext transactionalContext = (OptimisticTransactionalContext) TransactionalContext.getCurrentContext();
//        transactionalContext.setTxMetadata(syncTestByte);
//        UUID streamID1 = runtime.getStreamID("mychannel + fabcar");
//        UUID streamID2 = runtime.getStreamID("mychannel + smallbank");
//
//        long appendedAddr = runtime.getObjectsView().TXEnd();
//
//        long targetAddr = appendedAddr;
//        AddressSpaceView addressSpaceView = runtime.getAddressSpaceView();
//        ILogData ilogData = addressSpaceView.read(targetAddr);
//        LogData logData = (LogData) addressSpaceView.read(targetAddr);
//        System.out.println("appendedAddr " + appendedAddr);
//        System.out.println("logdata payload: " + ilogData.getPayload(runtime));
//        System.out.println("logdata GlobalAddress: " + ilogData.getGlobalAddress());
//        System.out.println("logdata logentry: " + ilogData.getLogEntry(runtime).getType());
//        System.out.println("logdata logentry: " + ilogData.getBackpointer(streamID1));
//        System.out.println("logdata logentry: " + ilogData.getThreadId());
//        MultiObjectSMREntry multiObjectSMREntry =  (MultiObjectSMREntry) logData.getPayload(runtime);
//        System.out.println("logdata payload: " + (multiObjectSMREntry.getEntryMap()));
//
//
//        IStreamView iStreamView = runtime.getStreamsView().get(streamID2);
//        StreamsView streamsView = new StreamsView(runtime);
//        iStreamView.next();
//        System.out.println("logdata from istreamview: " + iStreamView.next().getPayload(runtime));
//
//
//        return appendedAddr;
//    }
//
//
//    public static void next() {
//        String channelID = "mychannel";
//        String chaincodeID = "fabcar";
//        String peerName = "org1.peer1";
//        long targetAddr = 34L;
//        System.out.println("[peer-stub] {next} Channel ID: " + channelID + ", Peer Name: " + peerName);
//
//        UUID streamID = runtime.getStreamID(channelID + chaincodeID); //추후 실제 channel ID로 변
//        IStreamView iStreamView = runtime.getStreamsView().get(streamID);
//        StreamsView streamsView = new StreamsView(runtime);
//        System.out.println("iStreamView.next() " + iStreamView.next());
////        iStreamView.seek(targetAddr);
//        AddressSpaceView addressSpaceView = runtime.getAddressSpaceView();
//        long totalUpdate = streamsView.get(streamID).getTotalUpdates();
//        System.out.println("totalUpdate " + totalUpdate);
//        Object payload = iStreamView.next().getPayload(runtime);
//        System.out.println("iStreamView.next().getPayload(runtime) " + iStreamView.next().getPayload(runtime));
//        System.out.println("streamsView.get(streamID).next(): " + streamsView.get(streamID).next());
////        MultiObjectSMREntry multiObjectSMREntry =  (MultiObjectSMREntry)payload.getPayload(runtime);
////        byte[] txMetaByte =  multiObjectSMREntry.getTxMetadata();
////        String txMetaString = new String(txMetaByte);
////        LogEntry logEntry = ilogData.getLogEntry(runtime);
////
////        System.out.println("ilogData.getLogEntry의 meta 복구: " + logEntry);
////        MultiObjectSMREntry multiObjectSMREntry = (MultiObjectSMREntry) ilogData.getPayload(runtime);
////
////        Gateway.Builder builder = Gateway.createBuilder();
////        Gateway gateway = builder.connect();
////        Network network = gateway.getNetwork("mychannel");
////        String peerName = req.getPeerName();
////        String CombinationKey = channelId + peerName;
//        System.out.println("[peer-stub] {next} Channel ID: " + channelID + ", Peer Name: ");
//    }
//
//
//}
//
//
