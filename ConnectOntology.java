/**
 * Created by batuhan erdogdu on 03/22/17.
 */

import org.apache.jena.base.Sys;
import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.core.QuerySolutionBase;
import org.apache.jena.util.FileManager;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ConnectOntology {

    static final String inputFileName = "C:/Users/batuh/IdeaProjects/intrusiondetection/src/main/resources/ids.owl"; //ontology location
    public static String NS = "http://www.semanticweb.org/batuh/ontologies/2017/2/ids#"; //namespace
    String serviceURI = "http://localhost:3030/ds/data";
    //*******fuseki server conf********** for latter work

    //accessor = factory.createHTTP(serviceURI);

    public Model connectOnt() throws Exception {
        // TODO Auto-generated method stub
        ReadPacketFile rpf = new ReadPacketFile();
        SendCommands sc = new SendCommands();
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        InputStream in;
        FileOutputStream modelToWrite = null;

        FileManager.get().readModel(model, inputFileName);
        if (model == null) {
            throw new IllegalArgumentException(
			                                 "File: " + "C:/Users/batuh/IdeaProjects/intrusiondetection/src/main/resources/ids.owl" + " not found");
        }

        //**********ontology classes**************
        OntClass device = model.getOntClass(NS + "Device");
        OntClass nms = model.getOntClass(NS + "NetworkManagementSystem");
        OntClass managedDevice = model.getOntClass(NS + "ManagedDevice");
        OntClass databaseServer = model.getOntClass(NS + "DatabaseServer");
        OntClass software = model.getOntClass(NS + "Software");
        OntClass malware = model.getOntClass(NS + "Malware");
        OntClass service = model.getOntClass(NS + "Service");
        OntClass process = model.getOntClass(NS + "Process");
        OntClass packet = model.getOntClass(NS + "Packet");
        OntClass signaturedPacket = model.getOntClass(NS + "SignaturedPacket");

        //*******ontology object properties***********
        OntProperty manages = model.getOntProperty(NS + "manages");
        OntProperty runsOn = model.getOntProperty(NS + "runsOn");
        OntProperty receives = model.getOntProperty(NS + "receives");

        //*********ontology data properties*********
        OntProperty os = model.getOntProperty(NS + "os");
        OntProperty type = model.getOntProperty(NS + "type");
        OntProperty destination = model.getOntProperty(NS + "destination");
        OntProperty destinationMac = model.getOntProperty(NS + "destinationMAC");
        OntProperty destinationPort = model.getOntProperty(NS + "destinationPort");
        OntProperty source = model.getOntProperty(NS + "source");
        OntProperty sourceMac = model.getOntProperty(NS + "sourceMAC");
        OntProperty sourcePort = model.getOntProperty(NS + "sourcePort");
        OntProperty information = model.getOntProperty(NS + "information");
        OntProperty ack = model.getOntProperty(NS + "ack");
        OntProperty fin = model.getOntProperty(NS + "fin");
        OntProperty payload = model.getOntProperty(NS + "payload");
        OntProperty syn = model.getOntProperty(NS + "syn");
        OntProperty win = model.getOntProperty(NS + "win");
        OntProperty name = model.getOntProperty(NS + "name");
        OntProperty protocol = model.getOntProperty(NS + "protocol");


        ArrayList<String> services = sc.getServices();
        Individual instanceOfNms = model.createIndividual(NS + sc.getHostIP(), nms);//add host ip address to ontology
        /*try //has to run one time to add service individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< services.size(); i++) {
                Individual instance = model.createIndividual(NS + services.get(i), service);//add individuals
                instance.addProperty(runsOn, instanceOfNms);//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }*/

        //ArrayList<ArrayList<String>> dataList = ImportData.retrieveData("C:/Users/batuh/IdeaProjects/intrusiondetection/src/main/resources/data.csv");//to get malware list as an arraylist
        /*try //has to run one time to add malware individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< dataList.size(); i++) {
                Individual instance = model.createIndividual(NS + dataList.get(i).get(1), malware);//add individuals
                instance.addProperty(type, dataList.get(i).get(0));//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }*/

        ArrayList<String> processes = sc.getProcesses();
        //Individual instanceOfNms = model.createIndividual(NS + sc.getHostIP(), nms);//add host ip address to ontology
        /*try //has to run one time to add process individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< processes.size(); i++) {
                Individual instance = model.createIndividual(NS + processes.get(i), process);//add individuals
                instance.addProperty(runsOn, instanceOfNms);//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }*/
        //executeQuery(model);

        //model.write(System.out);
        //System.out.println("Device = "+device+" Database Server = "+ databaseServer);
        //for (ExtendedIterator<? extends OntResource> instances = managedDevice.listInstances(); instances.hasNext(); ) {
            //OntResource managedDeviceInstance = instances.next();
            //System.out.println( "Managed Device os instance: " + managedDeviceInstance.getProperty(os).getString() );
            //System.out.println("Managed Device :" + managedDeviceInstance.getNameSpace().substring(NS.length());
        //} //an example to see if ontology is accessed

        //System.out.println(dataList.get(19).get(0)+ " "+ dataList.get(19).get(1));//test
        //System.out.println(TcpPackets.get(0).get(TcpPacket.class).getHeader().getDstPort());
        //System.out.println(sc.getProcesses());
        //System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
        //System.out.println(sc.getServices());
        /*for (ExtendedIterator<? extends OntResource> instances = malware.listInstances(); instances.hasNext(); ) {
            OntResource malwareInstance = instances.next();
            System.out.println("Malware type : " + malwareInstance.getProperty(type).getString());
            System.out.println("Malware name : " + malwareInstance.getNameSpace().substring(NS.length()));

         }//this is a test to see if individuals were written*/
        return model;
    }

    public Boolean executeQuery(Model model){
        String queryString = ("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ids: <http://www.semanticweb.org/batuh/ontologies/2017/2/ids#>\n" +
                "ASK\n" +
                "WHERE{\n" +
                "?y rdf:type ids:Service.\n" +
                "?z rdf:type ids:Malware.\n" +
                "FILTER(?y = ?z)}");//gets the running services and checks whether a malware is running as a service
        QueryExecution qexec = QueryExecutionFactory.create(queryString, model);
        Boolean result = null;
        try{
            result = qexec.execAsk();
            //result = ResultSetFactory.copyResults(result);
        }catch(Exception e){}finally{qexec.close();}
        //System.out.println("asd");
        //if(rs != null){
          //  while(rs.hasNext()){
            //    QuerySolution r = rs.next();
              //  if(r != null){
                //    RDFNode l = r.get("x");
                  //  System.out.println(l);
                    //System.out.println(r.getResource("x").getLocalName());
                //}
                System.out.println(result);
            //}

        //}
        return result;
    }

    /*public void addTcpPackets() throws PcapNativeException, NotOpenException {

        ArrayList<Packet>  tcpPackets = new ArrayList<Packet>();

        for(int i = 0; i<rpf.ReturnTcpPackets().size() ; i++) tcpPackets.add(rpf.ReturnTcpPackets().get(i));

        try //has to run one time to add tcp packets
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< tcpPackets.size(); i++) {
                Individual instance = model.createIndividual(NS + tcpPackets.get(i), packet);//add individuals
                instance.addProperty(protocol,"TCP");//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/

    /*public void addServices() throws Exception {

        ArrayList<String> services = sc.getServices();
        Individual instanceOfNms = model.createIndividual(NS + sc.getHostIP(), nms);//add host ip address to ontology
        try //has to run one time to add service individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< services.size(); i++) {
                Individual instance = model.createIndividual(NS + services.get(i), service);//add individuals
                instance.addProperty(runsOn, instanceOfNms);//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/

    /*public void addProcesses() throws Exception {

        ArrayList<String> processes = sc.getProcesses();
        Individual instanceOfNms = model.createIndividual(NS + sc.getHostIP(), nms);//add host ip address to ontology
        try //has to run one time to add process individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< processes.size(); i++) {
                Individual instance = model.createIndividual(NS + processes.get(i), process);//add individuals
                instance.addProperty(runsOn, instanceOfNms);//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/
    /*public void addMalwares() throws Exception
    {
        ArrayList<ArrayList<String>> dataList = ImportData.retrieveData("C:/Users/batuh/Documents/Assertion/batuhan/data.csv");//to get malware list as an arraylist
        try //has to run one time to add malware individuals
        {
            modelToWrite = new FileOutputStream(inputFileName);
            for (int i =0; i< dataList.size(); i++) {
                Individual instance = model.createIndividual(NS + dataList.get(i).get(1), malware);//add individuals
                instance.addProperty(type, dataList.get(i).get(0));//Add type property for individual
                System.out.println("Done.");//copy owl files to test
            }
            model.write(modelToWrite, "RDF/XML");
            modelToWrite.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/
}

