package org.sslab.fabric.orderer.stub;

import com.google.protobuf.ByteString;
import lombok.SneakyThrows;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.peer.Chaincode;
import org.hyperledger.fabric.protos.peer.ProposalPackage;

public class UnpackedEnvelope {
    public String chaincodeName;
    public Common.ChannelHeader channelHeader;
    public Chaincode.ChaincodeInput input;
    public ProposalPackage.Proposal proposal;
    public Common.SignatureHeader signatureHeader;
    public ProposalPackage.SignedProposal signedProp;
    public ByteString proposalHash;

    public UnpackedEnvelope(String chaincodeName, Common.ChannelHeader channelHeader, Chaincode.ChaincodeInput input, ProposalPackage.Proposal proposal, Common.SignatureHeader signatureHeader,
                            ProposalPackage.SignedProposal signedProp, ByteString proposalHash) {
        this.chaincodeName = chaincodeName;
        this.channelHeader = channelHeader;
        this.input = input;
        this.proposal = proposal;
        this.signatureHeader = signatureHeader;
        this.signedProp = signedProp;
        this.proposalHash = proposalHash;
    }
}
