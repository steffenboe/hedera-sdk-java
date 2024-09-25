/*-
 *
 * Hedera Java SDK
 *
 * Copyright (C) 2020 - 2024 Hedera Hashgraph, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.hedera.hashgraph.sdk;

import com.google.common.base.MoreObjects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A unique, composite, identifier for a pending airdrop.
 *
 * Each pending airdrop SHALL be uniquely identified by a PendingAirdropId.
 * A PendingAirdropId SHALL be recorded when created and MUST be provided in any transaction
 * that would modify that pending airdrop (such as a `claimAirdrop` or `cancelAirdrop`).
 */
public class PendingAirdropId {
    private AccountId sender;
    private AccountId receiver;
    @Nullable
    private TokenId tokenId;
    @Nullable
    private NftId nftId;

    public PendingAirdropId() {}

    PendingAirdropId(AccountId sender, AccountId receiver, TokenId tokenId) {
        this.sender = sender;
        this.receiver = receiver;
        this.tokenId = tokenId;
        this.nftId = null;
    }

    PendingAirdropId(AccountId sender, AccountId receiver, NftId nftId) {
        this.sender = sender;
        this.receiver = receiver;
        this.nftId = nftId;
        this.tokenId = null;
    }

    public AccountId getSender() {
        return sender;
    }

    public PendingAirdropId setSender(@Nonnull AccountId sender) {
        this.sender = sender;
        return this;
    }

    public AccountId getReceiver() {
        return receiver;
    }

    public PendingAirdropId setReceiver(@Nonnull AccountId receiver) {
        this.receiver = receiver;
        return this;
    }

    public TokenId getTokenId() {
        return tokenId;
    }

    public PendingAirdropId setTokenId(@Nullable TokenId tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public NftId getNftId() {
        return nftId;
    }

    public PendingAirdropId setNftId(@Nullable NftId nftId) {
        this.nftId = nftId;
        return this;
    }

    static PendingAirdropId fromProtobuf(com.hedera.hashgraph.sdk.proto.PendingAirdropId pendingAirdropId) {
        if (pendingAirdropId.hasFungibleTokenType()) {
            return new PendingAirdropId(AccountId.fromProtobuf(pendingAirdropId.getSenderId()),
                AccountId.fromProtobuf(pendingAirdropId.getReceiverId()),
                TokenId.fromProtobuf(pendingAirdropId.getFungibleTokenType()));
        } else {
            return new PendingAirdropId(AccountId.fromProtobuf(pendingAirdropId.getSenderId()),
                AccountId.fromProtobuf(pendingAirdropId.getReceiverId()),
                NftId.fromProtobuf(pendingAirdropId.getNonFungibleToken()));
        }
    }

    com.hedera.hashgraph.sdk.proto.PendingAirdropId toProtobuf() {
        var builder = com.hedera.hashgraph.sdk.proto.PendingAirdropId.newBuilder()
            .setSenderId(sender.toProtobuf())
            .setReceiverId(receiver.toProtobuf());

        if (tokenId != null) {
            builder.setFungibleTokenType(tokenId.toProtobuf());
        } else if (nftId != null) {
            builder.setNonFungibleToken(nftId.toProtobuf());
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("sender", sender)
            .add("receiver", receiver)
            .add("tokenId", tokenId)
            .add("nftId", nftId)
            .toString();
    }
}