import org.apache.jena.rdf.model.Model;

/**
 * Created by batuh on 04/11/17.
 */
public class Run {
    public static void main (String args[]){
        final ConnectOntology co = new ConnectOntology();
        Model model;
        boolean result;
        try {
            model = co.connectOnt();
            //co.addMalwares();
            //co.addProcesses();
            //co.addServices();
            result = co.executeQuery(model);
            if (result == false)
                System.out.println("No intrusion has been detected.");
            else System.out.println("Intrusion Detected! Please see your administrator.");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
