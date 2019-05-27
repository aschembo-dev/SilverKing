package com.ms.silverking.cloud.dht.net;

import java.nio.ByteBuffer;

import com.ms.silverking.cloud.dht.common.MessageType;
import com.ms.silverking.collection.Pair;
import com.ms.silverking.id.UUIDBase;

public class ProtoProgressMessageGroup extends ProtoMessageGroup {
    private final ByteBuffer    buffer;
    
    private static final int    dataBufferIndex = 0;
    
    private static final int    bufferSize = Long.BYTES * 2;
    private static final int	progressIndex0 = 0;
    private static final int	progressIndex1 = progressIndex0 + Long.BYTES;
    
    public ProtoProgressMessageGroup(UUIDBase uuid, long context, Pair<Long,Long> progress, byte[] originator, int deadlineRelativeMillis) {
        super(MessageType.PROGRESS, uuid, context, originator, deadlineRelativeMillis, ForwardingMode.FORWARD);
        
        buffer = ByteBuffer.allocate(bufferSize);
        bufferList.add(buffer);
        buffer.putLong(progressIndex0, progress.getV1());
        buffer.putLong(progressIndex1, progress.getV2());
    }

    @Override
    public boolean isNonEmpty() {
        return true;
    }
    
    public static Pair<Long,Long> progress(MessageGroup mg) {
    	long	l0;
    	long	l1;
    	
        l0 = mg.getBuffers()[dataBufferIndex].getLong(progressIndex0);
        l1 = mg.getBuffers()[dataBufferIndex].getLong(progressIndex1);
    	return new Pair<>(l0, l1);
    }        
}
