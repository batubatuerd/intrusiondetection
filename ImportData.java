/**
 * Created by batuhan erdogdu on 03/22/17.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportData {

    public static ArrayList<ArrayList<String>> retrieveData(String fileLocation){
        ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
        BufferedReader dataBuffer = null;
        boolean flag= false;
        try{
            String dataLine;
            dataBuffer = new BufferedReader(new FileReader(fileLocation));

            while((dataLine = dataBuffer.readLine()) != null){
                //System.out.println("Raw CSV data: "+ dataLine);
                //System.out.println("Converted ArrayList data" + dataCsvToArrayList(dataLine) + "\n");
                if(flag==false){//remove bom character at beginning
                    dataLine= dataLine.substring(1);
                    finalList.add(dataCsvToArrayList(dataLine));
                    flag = true;
                }
                else{
                    finalList.add(dataCsvToArrayList(dataLine));
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(dataBuffer != null) dataBuffer.close();
            }catch(IOException dataException){
                dataException.printStackTrace();
            }
        }
        //System.out.println("Converted ArrayList data: " + finalList + "\n");
        //System.out.println(finalList.get(0).get(0).length());
        return finalList;
    }
	/*public static void main (String[] args){

		BufferedReader dataBuffer = null;

		try{
			String dataLine;
			dataBuffer = new BufferedReader(new FileReader("C:/Users/batuh/Documents/Dissertation/batuhan/data.csv"));

			while((dataLine = dataBuffer.readLine()) != null){
				System.out.println("Raw CSV data: "+ dataLine);
				System.out.println("Converted ArrayList data" + dataCsvToArrayList(dataLine) + "\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(dataBuffer != null) dataBuffer.close();
			}catch(IOException dataException){
				dataException.printStackTrace();
			}
		}
	}*/

    public static ArrayList<String> dataCsvToArrayList(String dataCSV){
        ArrayList<String> dataResult= new ArrayList<String>();

        if(dataCSV != null){
            String[] splitData = dataCSV.split("\\s*,\\s*");
            for(int i=0; i<splitData.length;i++){
                if(!(splitData[i]==null) || !(splitData[i].length() == 0)){
                    dataResult.add(splitData[i].trim());

                }
            }
        }
        return dataResult;
    }

}

