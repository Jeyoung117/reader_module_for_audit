package org.sslab.fabric.OrdereClient;


import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.impl.ContractImpl;
import org.hyperledger.fabric.gateway.impl.GatewayImpl;
import org.hyperledger.fabric.gateway.impl.NetworkImpl;
import org.hyperledger.fabric.gateway.impl.TimePeriod;
import org.hyperledger.fabric.gateway.spi.CommitHandlerFactory;
import org.hyperledger.fabric.gateway.spi.QueryHandler;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.sdk.*;


public final class TransactionImpl implements Transaction {
    private static final Log LOG = LogFactory.getLog(org.hyperledger.fabric.gateway.impl.TransactionImpl.class);

    private static final long DEFAULT_ORDERER_TIMEOUT = 60;
    private static final TimeUnit DEFAULT_ORDERER_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private final ContractImpl contract;
    private final String name;
    private final NetworkImpl network;
    private final Channel channel;
    private final GatewayImpl gateway;
    private final CommitHandlerFactory commitHandlerFactory;
    private TimePeriod commitTimeout;
    private final QueryHandler queryHandler;
    private Map<String, byte[]> transientData = null;
    private Collection<Peer> endorsingPeers = null;

    TransactionImpl(final ContractImpl contract, final String name) {
        this.contract = contract;
        this.name = name;
        network = contract.getNetwork();
        channel = network.getChannel();
        gateway = network.getGateway();
        commitHandlerFactory = gateway.getCommitHandlerFactory();
        commitTimeout = gateway.getCommitTimeout();
        queryHandler = network.getQueryHandler();
    }

    public TransactionImpl(final NetworkImpl network) {
        this.contract = null;
        this.name = null;
        this.network = network;
        channel = network.getChannel();
        gateway = network.getGateway();
        commitHandlerFactory = gateway.getCommitHandlerFactory();
        commitTimeout = gateway.getCommitTimeout();
        queryHandler = network.getQueryHandler();
    }

    @Override
    public String getName() {
        return name;
    }

    public String sendEnvelope(Common.Envelope env)
            throws ContractException {

        try {
            Channel.TransactionOptions transactionOptions = Channel.TransactionOptions.createTransactionOptions()
                    .nOfEvents(Channel.NOfEvents.createNoEvents()); // Disable default commit wait behaviour
            channel.sendEnvelope(env, transactionOptions);
        } catch (Exception e) {
            throw new ContractException("Failed to send transaction to the orderer", e);
        }

        return "success sending env";
    }

    public String sendEnvelopes(List<Common.Envelope> envs)
            throws ContractException {

        try {
            Channel.TransactionOptions transactionOptions = Channel.TransactionOptions.createTransactionOptions()
                    .nOfEvents(Channel.NOfEvents.createNoEvents()); // Disable default commit wait behaviour
            channel.sendEnvelopes(envs, transactionOptions);
        } catch (Exception e) {
            throw new ContractException("Failed to send transaction to the orderer", e);
        }

        return "success sending env";
    }

}

