package control;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OutPutControl {
	
	public void generateTxtFile(String result, String FileName) {
		try {
			FileWriter fw = new FileWriter("ResultLogs/" + FileName + ".txt");
			fw.write(result);
			fw.close();
		}catch(Exception e) { 
			System.out.println("[ERROR] PROBLEM WRITING THE .TXT OUTPUT\n");
			e.printStackTrace();
		}
	}
	
	public void generateGpxFile(String result, String FileName, TreeNode tn, Problem pr) {
		String[] lines = result.split("\\r?\\n");
		String gpxInit = "<gpx version=\"1.1\" creator=\"ENRIQUE, BEKA, RAQUEL\">\n";
		String desc = "";
		try {
			desc = "\n<desc>\n" + lines[3] + "\n" + lines[4] + "\n" + lines[5] + "\n" + lines[6] + "\n</desc>\n";
		}catch(Exception e) {
			System.out.println("\n[ERROR] NO SOLUTION TO WRITE IN THE GPX FILE");
		}
		if (!desc.equals("")) {
			String wpt = "";
			Graph G = pr.getStateSpace().getG();
			State InSt = pr.getInSt();
			ArrayList<String> listNodes = InSt.getListNodes();
			wpt = "\n<wpt lat=\"" + G.positionNode(InSt.getNode())[1] + "\" lon=\"" + G.positionNode(InSt.getNode())[0] + "\" >\n\t<name>[I]" + InSt.getNode() + "</name>\n</wpt>\n";
			for(int i = 0; i<listNodes.size(); i++) {
				wpt = wpt + "<wpt lat=\"" + G.positionNode(listNodes.get(i))[1] + "\" lon=\"" + G.positionNode(listNodes.get(i))[0] + "\" >\n\t<name>[V]" + listNodes.get(i) + "</name>\n</wpt>\n";
			}

			String trk = "\n<trk>\n";
			trk += generateTrk(tn, G);
			trk += "\n</trk>\n</gpx>";

			String gpxString = gpxInit + desc + wpt + trk;

			try {
				FileWriter fw = new FileWriter("GpxFiles/" + FileName + ".gpx");
				fw.write(gpxString);
				fw.close();
			}catch(Exception e) { 
				System.out.println("[ERROR] PROBLEM WRITING THE .GPX OUTPUT\n");
				e.printStackTrace();
			}
		}
	}
	
	private String generateTrk(TreeNode tn, Graph G) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date date = new Date();
		String result = "";
		if(tn.getParent() == null) {
			result += "\n\t<trkpt lat=\"" + G.positionNode(tn.getCurrentState().getNode())[1] + "\" lon=\"" + G.positionNode(tn.getCurrentState().getNode())[0] + "\" >\n\t\t<ele>0</ele>\n\t\t<time>" + dateFormat.format(date) + "</time>\n\t\t<name>" + tn.getCurrentState().getNode() + "</name>\n\t</trkpt>";
			return result;
		}else {
			result += generateTrk(tn.getParent(), G) + "\n\t<trkpt lat=\"" + G.positionNode(tn.getCurrentState().getNode())[1] + "\" lon=\"" + G.positionNode(tn.getCurrentState().getNode())[0] + "\" >\n\t\t<ele>0</ele>\n\t\t<time>" + dateFormat.format(date) + "</time>\n\t\t<name>" + tn.getCurrentState().getNode() + "</name>\n\t</trkpt>";
		}
		return result;
	}
}
