package com.hedera.hashgraph.sdk;

import com.hedera.hashgraph.sdk.proto.ContractID;

import javax.annotation.Nonnegative;

public final class ContractId extends EntityId {
    public ContractId(@Nonnegative long num) {
        super(0, 0, num);
    }

    @SuppressWarnings("InconsistentOverloads")
    public ContractId(@Nonnegative long shard, @Nonnegative long realm, @Nonnegative long num) {
        super(shard, realm, num);
    }

    public static ContractId fromString(String id) {
        return EntityId.fromString(id, ContractId::new);
    }

    public static ContractId fromSolidityAddress(String address) {
        return EntityId.fromSolidityAddress(address, ContractId::new);
    }

    static ContractId fromProtobuf(ContractID contractId) {
        return new ContractId(
            contractId.getShardNum(), contractId.getRealmNum(), contractId.getContractNum());
    }

    @Override
    public String toSolidityAddress() {
        return super.toSolidityAddress();
    }

    ContractID toProtobuf() {
        return ContractID.newBuilder()
            .setShardNum(shard)
            .setRealmNum(realm)
            .setContractNum(num)
            .build();
    }
}
