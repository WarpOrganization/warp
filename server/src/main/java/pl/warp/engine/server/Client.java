package pl.warp.engine.server;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Client {
    private Channel channel;
    private InetSocketAddress address;
    private long lastKeepAlive;

    public Client(Channel channel, InetSocketAddress address) {
        this.channel = channel;
        this.address = address;
        lastKeepAlive = System.currentTimeMillis();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getLastKeepAlive() {
        return lastKeepAlive;
    }

    public void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }
}
