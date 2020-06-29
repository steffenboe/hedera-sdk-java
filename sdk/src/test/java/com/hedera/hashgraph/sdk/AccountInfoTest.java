package com.hedera.hashgraph.sdk;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.proto.CryptoGetInfoResponse;
import io.github.jsonSnapshot.SnapshotMatcher;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;


public class AccountInfoTest {
    private static final PrivateKey privateKey = PrivateKey.fromString(
        "302e020100300506032b657004220420db484b828e64b2d8f12ce3c0a0e93a0b8cce7af1bb8f39c97732394482538e10");

    private static final CryptoGetInfoResponse.AccountInfo info = CryptoGetInfoResponse.AccountInfo.newBuilder()
        .setAccountID(new AccountId(1).toProtobuf())
        .setDeleted(true)
        .setProxyReceived(2)
        .setKey(privateKey.getPublicKey().toKeyProtobuf())
        .setBalance(3)
        .setGenerateSendRecordThreshold(4)
        .setGenerateReceiveRecordThreshold(5)
        .setReceiverSigRequired(true)
        .setExpirationTime(InstantConverter.toProtobuf(Instant.ofEpochMilli(6)))
        .setAutoRenewPeriod(DurationConverter.toProtobuf(Duration.ofDays(7)))
        .build();

    @BeforeAll
    public static void beforeAll() {
        SnapshotMatcher.start();
    }

    @AfterClass
    public static void afterAll() {
        SnapshotMatcher.validateSnapshots();
    }

    @Test
    void fromProtobufWithOtherOptions() {
        SnapshotMatcher.expect(AccountInfo.fromProtobuf(info).toString())
            .toMatchSnapshot();
    }

    @Test
    void fromBytes() throws InvalidProtocolBufferException {
        SnapshotMatcher.expect(AccountInfo.fromBytes(info.toByteArray()).toString())
            .toMatchSnapshot();
    }

    @Test
    void toBytes() throws InvalidProtocolBufferException {
        SnapshotMatcher.expect(AccountInfo.fromBytes(info.toByteArray()).toBytes())
            .toMatchSnapshot();
    }

    @Test
    void toProtobuf() throws InvalidProtocolBufferException {
        SnapshotMatcher.expect(AccountInfo.fromProtobuf(info).toProtobuf())
            .toMatchSnapshot();
    }
}
