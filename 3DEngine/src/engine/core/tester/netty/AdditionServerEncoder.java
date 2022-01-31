/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class AdditionServerEncoder extends MessageToByteEncoder<AdditionInput> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AdditionInput additionInput, ByteBuf byteBuf) {
        byteBuf.writeInt(additionInput.a() + additionInput.b());
    }
}
