
public class MarsRover {

	public static void main(String args[])
	{
		InputFileReader readFile = new InputFileReader();
		OutputFileWriter writeFile;
		try
		{
			readFile.loadValues();
			StringBuilder finalPaths = new StringBuilder();
			
			switch(readFile.algorithm)
			{
			case "BFS":	for(int i=0; i<readFile.noOfTargetSites; i++)
						{
							int targetRow = readFile.targetSites[i][0];
							int targetCol = readFile.targetSites[i][1];
				
							finalPaths.append(new BFS(readFile.landingRow, readFile.landingCol,targetRow,targetCol, readFile.terrain, readFile.elevationLimit, readFile.totalRows, readFile.totalCols).startBFS());
						}
						finalPaths.deleteCharAt(finalPaths.length() - 1);
						System.out.print(finalPaths);
						writeFile = new OutputFileWriter();
						writeFile.writeStringToFile(finalPaths.toString());
						writeFile.closeFile();
						break;
			
			case "UCS":	for(int i=0; i<readFile.noOfTargetSites; i++)
						{
							int targetRow = readFile.targetSites[i][0];
							int targetCol = readFile.targetSites[i][1];
				
							finalPaths.append(new UCS(readFile.landingRow, readFile.landingCol,targetRow,targetCol, readFile.terrain, readFile.elevationLimit, readFile.totalRows, readFile.totalCols).startUCS());
						}
						finalPaths.deleteCharAt(finalPaths.length() - 1);
						System.out.print(finalPaths);
						writeFile = new OutputFileWriter();
						writeFile.writeStringToFile(finalPaths.toString());
						writeFile.closeFile();
						break;
			
			case "A*": for(int i=0; i<readFile.noOfTargetSites; i++)
						{
							int targetRow = readFile.targetSites[i][0];
							int targetCol = readFile.targetSites[i][1];
				
							finalPaths.append(new AStar(readFile.landingRow, readFile.landingCol,targetRow,targetCol, readFile.terrain, readFile.elevationLimit, readFile.totalRows, readFile.totalCols).startAStar());
						}
						finalPaths.deleteCharAt(finalPaths.length() - 1);
						System.out.print(finalPaths);
						writeFile = new OutputFileWriter();
						writeFile.writeStringToFile(finalPaths.toString());
						writeFile.closeFile();
						break;
			default:	writeFile = new OutputFileWriter();
						writeFile.writeStringToFile("FAIL");
						writeFile.closeFile();
		
			}
		}
		catch(Exception e)
		{
			writeFile = new OutputFileWriter();
			writeFile.writeStringToFile("FAIL");
			writeFile.closeFile();
		}	
	}
}
