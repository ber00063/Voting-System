
/**
 * main.java is the main file for CSCI 5801 Project 3: Waterfall Implementation & Testing.
 * This is where the user is prompted for the election file, the type of election is determined,
 * and from there the election is processed based on its type.
 *
 * @author tracy255, Caleb Tracy.
 */


import java.io.*;

/** main class where user will be prompted for election information file
 *  and election system will be run.
 */
public class main {

    /**
     * The main method. Calls getInput() and checkArgs() to check user input and run election.
     * Checks if user gives too many arguments or for checkArgs() error.
     * @param args  the command line arguments given by the user.
     * @throws IOException  when getInput() or checkArgs() throws an exception.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("------ELECTION PROCESSING PROGRAM RUNNING------");
        Boolean checkReturn;  //Used to determine if the current input it valid
        if(args.length == 0) {
            System.out.println("------Input filename from working directory. Format: 'FileName'.csv------");
            String[] fileNames = getInput();
            checkReturn = checkArgs(fileNames);
            if (!checkReturn) {
                for(int i = 0; i < 10; i++) {
                    System.out.println("------INVALID FILENAME: Input filename from working directory. Format: 'FileName'.csv------");
                    String[] files = getInput();
                    checkReturn = checkArgs(files);
                    if(checkReturn == true) {
                        break;
                    }
                }
            }
        }
        else {
            // In the case arguments are provided
            checkReturn = checkArgs(args);
            if (!checkReturn) {  //Check if the name is valid
                System.out.println("------PROVIDED FILENAME IS INCORRECT------");
            }
        }
    }

    /**
     * Checks the command line argument given by the user to get the election information file.
     * Checks if input file can't be found.
     * Calls CPLProcessing.processElection() if election is CPL.
     * Calls IRProcessing.processElection() if election is IR.
     * @param fileNames  name of election information files given by user.
     * @return  a boolean, true if election was run, false if not.
     * @throws FileNotFoundException throws exception if file couldn't be found.
     */
    private static boolean checkArgs(String[] fileNames) throws IOException {
        //String path = "Project2/" + fileName;  //Not needed if in Project2 dir
        for (int i = 0; i < fileNames.length; i++) {
            File f = new File(fileNames[i]);
            if(f.exists()) {
                System.out.println("file " + f + " exists!");
            } else {
                System.out.println("file " + f + " does not exist!");
                return false;
            }
        }

        //Debugging for finding file name
        //System.out.println("Working Directory: " + System.getProperty("user.dir"));
        //System.out.println("File path: " + f.getPath());
        //List the directory's files, should print files/directories under /Project2
        //File file = new File(".");
        //for(String fileNames : file.list()) System.out.println(fileNames);

        FileReader csvFile = new FileReader(fileNames[0]);
        //If file cannot be found, throw an exception.
        //Extract the election type from the CSV file (first line)
        String electionType;
        BufferedReader myReader = new BufferedReader(csvFile);
        try {
            electionType = myReader.readLine();
            //for(char c : electionType.toCharArray()) System.out.print((int)c + "\n");  //debugging
        } catch (Exception e) {
            System.out.println("File cannot be found.\nFile-name provided: " + fileNames[0]);
            throw new RuntimeException(e);
        }

        BufferedReader[] electionFiles = new BufferedReader[fileNames.length];
        for (int i = 0; i < electionFiles.length; i++) {
            csvFile = new FileReader(fileNames[i]);
            myReader = new BufferedReader(csvFile);
            //Skips over election type
            myReader.readLine();
            electionFiles[i] = myReader;
        }

        //Create a processing class object accordingly and pass on the BufferedReader object
        if (electionType.equals("CPL")) {
            //runs processElection() in the constructor
            System.out.println("------Running Closed Party List Election------");
            CPLProcessing CPLElection = new CPLProcessing(electionFiles);
            return true;
        }
        else if (electionType.equals("IR")) {
            //runs processElection() in the constructor
            System.out.println("------Running Instant Runoff Vote Election------");
            IRProcessing IRElection = new IRProcessing(electionFiles);
            return true;
        }
        else if (electionType.equals("PO")) {
            //runs processElection() in the constructor
            System.out.println("------Running Popularity Only Vote Election------");
            POProcessing OPElection = new POProcessing(electionFiles[0]);
            return true;
        }
        return false;
    }

    /**
     * Reads user input from command line to get filename.
     * @return  name of file input by user as a String.
     * @throws IOException  when IO error occurs.
     */
    private static String[] getInput() throws IOException {
        //Read the user input from terminal
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String files = inputReader.readLine();
        String[] filesArray = files.split("\\s+");
        return filesArray;
    }


}
