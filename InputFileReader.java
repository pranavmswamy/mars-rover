import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFileReader {
	
	public String algorithm;
	public int totalRows, totalCols;
	public int landingRow, landingCol;
	public int elevationLimit;
	public int noOfTargetSites;
	public int[][] targetSites;
	public int[][] terrain;
	
	public void loadValues() throws Exception
	{
		
			Scanner input = null;
			try {
				input = new Scanner(new File("input.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> fileContents = new ArrayList<String>();
			while (input.hasNextLine()) {
			    fileContents.add(input.nextLine());
			}
	
			algorithm = fileContents.get(0);
			
			String[] terrainDimensions = fileContents.get(1).split(" ");
			totalRows = Integer.parseInt(terrainDimensions[1]);
			totalCols = Integer.parseInt(terrainDimensions[0]);
			
			String[] landingNode = fileContents.get(2).split(" ");
			landingRow = Integer.parseInt(landingNode[1]);
			landingCol = Integer.parseInt(landingNode[0]);
			
			elevationLimit = Integer.parseInt(fileContents.get(3));
			noOfTargetSites = Integer.parseInt(fileContents.get(4));
			
			targetSites = new int[noOfTargetSites][2];
			
			int targetCursor;
			for(targetCursor = 0; targetCursor<noOfTargetSites; targetCursor++)
			{
				
				targetSites[targetCursor][0] = Integer.parseInt(fileContents.get(5+targetCursor).split(" ")[1]);
				targetSites[targetCursor][1] = Integer.parseInt(fileContents.get(5+targetCursor).split(" ")[0]);
			}
			
			terrain = new int[totalRows][totalCols];
			
			int terrainCursor;
			for(terrainCursor = 0; terrainCursor<totalRows;terrainCursor++)
			{
				for(int j = 0; j < totalCols; j++)
				{
					terrain[terrainCursor][j] = Integer.parseInt(fileContents.get(5+targetCursor+terrainCursor).split(" ")[j]);
				}
			}
		}
}
