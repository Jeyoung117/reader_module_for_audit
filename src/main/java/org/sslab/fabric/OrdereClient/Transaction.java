package org.sslab.fabric.OrdereClient;

import java.util.List;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.protos.common.Common;


public interface Transaction {
    /**
     * Get the fully qualified name of the transaction function.
     * @return Transaction name.
     */
    String getName();

    String sendEnvelope(Common.Envelope env) throws ContractException;

    String sendEnvelopes(List<Common.Envelope> envs) throws ContractException;
}

