import org.pcap4j.core.PcapHandle;
import org.pcap4j.packet.Packet;

/**
 * Created by batuh on 04/10/17.
 */
public class NetworkPacket {

    private Packet packet;
    private java.sql.Timestamp timeStamp;

    public Packet getPacket(){
        return this.packet;
    }
    public java.sql.Timestamp getTimeStamp(){
        return this.timeStamp;
    }
    public void setPacket(Packet packet){
        this.packet = packet;
    }
    public void setTimeStamp(java.sql.Timestamp timeStamp){
        this.timeStamp = timeStamp;
    }
}
