package net.spy.memcached.protocol.binary;

import java.util.UUID;

import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationState;
import net.spy.memcached.ops.TapOperation;
import net.spy.memcached.tapmessage.RequestMessage;
import net.spy.memcached.tapmessage.TapMagic;
import net.spy.memcached.tapmessage.TapOpcode;

public class TapDeregisterOperationImpl extends OperationImpl implements
        TapOperation {

    static final byte CMD = 0;
    private final String id;

    protected TapDeregisterOperationImpl(String id, OperationCallback cb) {
        super(CMD, generateOpaque(), cb);
        this.id = id;
    }

    @Override
    public void streamClosed(OperationState state) {
        transitionState(state);
    }

    @Override
    public void initialize() {
        RequestMessage message = new RequestMessage();
        message.setMagic(TapMagic.PROTOCOL_BINARY_REQ);
        message.setOpcode(TapOpcode.DEREGISTER_CLIENT);
        if (id != null) {
          message.setName(id);
        } else {
          message.setName(UUID.randomUUID().toString());
        }
        setBuffer(message.getBytes());
    }

    @Override
    public String toString() {
      return "Cmd: tap deregister client";
    }

}
