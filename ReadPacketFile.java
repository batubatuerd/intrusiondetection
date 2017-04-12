/**
 * Created by batuhan erdogdu on 03/26/17.
 */

import org.pcap4j.core.*;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.namednumber.TcpPort;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ReadPacketFile {

    private static final int COUNT = 45;//how many packets will be read

    private static final String PCAP_FILE_KEY
            = ReadPacketFile.class.getName() + ".pcapFile";
    private static final String PCAP_FILE
            = System.getProperty(PCAP_FILE_KEY, "src/main/resources/packetdataset.pcap");
    //private ReadPacketFile() {}

    public ArrayList<Packet> ReturnTcpPackets() throws PcapNativeException, NotOpenException {
        PcapHandle handle;
        try {
            handle = Pcaps.openOffline(PCAP_FILE, TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            handle = Pcaps.openOffline(PCAP_FILE);
        }
        ArrayList<Packet> TcpPackets= new ArrayList<Packet>();
        for (int i = 0; i < COUNT; i++) {
            try {
                Packet packet = handle.getNextPacketEx();
                //System.out.println(handle.getTimestamp());


                //System.out.println(packet);
                //System.out.println(packet.toString());
                if(packet.get(TcpPacket.class) != null){//to get tcp packets only
                    //System.out.println(packet.get(TcpPacket.class).getHeader().getSrcPort());
                    //System.out.println(packet.get(TcpPacket.class).getHeader().getDstPort());
                    //TcpPackets.add(handle.getTimestamp());

                    TcpPackets.add(packet);
                }
                //System.out.println(packet.get(TcpPacket.class));
            } catch (TimeoutException e) {
            } catch (EOFException e) {
                System.out.println("EOF");
                break;
            }
        }
        handle.close();
        return TcpPackets;
    }
}
