/**
 * Created by batuh on 04/10/17.
 */

import java.io.IOException;
import java.util.ArrayList;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapStat;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;
import com.sun.jna.Platform;

@SuppressWarnings("javadoc")
public class Loop {

    private static final String COUNT_KEY
            = Loop.class.getName() + ".count";
    private static final int COUNT
            = Integer.getInteger(COUNT_KEY, 5);//determines how many packets will be read

    private static final String READ_TIMEOUT_KEY
            = Loop.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT
            = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY
            = Loop.class.getName() + ".snaplen";
    private static final int SNAPLEN
            = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    private Loop() {}

    public ArrayList<NetworkPacket> getNetworkPackets() throws PcapNativeException, NotOpenException {
        //String filter = args.length != 0 ? args[0] : "";

        System.out.println(COUNT_KEY + ": " + COUNT);
        System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
        System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
        System.out.println("\n");

        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (nif == null) {
            return null;
        }

        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

        final PcapHandle handle = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        /*if (filter.length() != 0) {
            handle.setFilter(
                    filter,
                    BpfCompileMode.OPTIMIZE
            );
        }*/
        final ArrayList<NetworkPacket> networkPackets = new ArrayList<NetworkPacket>();
        PacketListener listener = new PacketListener() {
            public void gotPacket(Packet packet) {
                //System.out.println(handle.getTimestamp());
                //System.out.println(packet);
                NetworkPacket np = new NetworkPacket();
                np.setPacket(packet);
                np.setTimeStamp(handle.getTimestamp());
                networkPackets.add(np);
            }
        };

        try {
            handle.loop(COUNT, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PcapStat ps = handle.getStats();
        System.out.println("ps_recv: " + ps.getNumPacketsReceived());
        System.out.println("ps_drop: " + ps.getNumPacketsDropped());
        System.out.println("ps_ifdrop: " + ps.getNumPacketsDroppedByIf());
        if (Platform.isWindows()) {
            System.out.println("bs_capt: " + ps.getNumPacketsCaptured());
        }

        handle.close();
        System.out.println(networkPackets);
        return networkPackets;
    }
}