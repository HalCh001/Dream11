package Dream11.IPL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import Resources.BaseClass;
import Resources.LoggerClass;

public class PlayerManager extends BaseClass 
{
	static final Logger log = LoggerClass.Configure(PlayerManager.class);
	public static String Team1Name;
	public static String Team2Name;

	// Fetching Team-wise complete details
	public static HashMap<String, ArrayList<Object>> getCompleteInformationOfaTeam(String TeamName) throws IOException{
		HashMap<String, ArrayList<Object>> m0 = new HashMap<String, ArrayList<Object>>();
		File F1 = new File(ExcelLocation);
		FileInputStream Fis = new FileInputStream(F1);
		HSSFWorkbook x1 = new HSSFWorkbook(Fis);
		HSSFSheet Y1 = x1.getSheet(TeamName);

		int TotalRow = Y1.getPhysicalNumberOfRows();

		for (int i = 1; i < TotalRow; i++) {
			String PlayerName = Y1.getRow(i).getCell((short) 0).getStringCellValue();
			String PlayerType = Y1.getRow(i).getCell((short) 1).getStringCellValue();
			double PlayerPoint = Double.parseDouble(Y1.getRow(i).getCell((short) 2).getStringCellValue());
			double PlayerCredit = Double.parseDouble(Y1.getRow(i).getCell((short) 3).getStringCellValue());
			// String PlayerTeamName= Y1.getRow(i).getCell((short)
			// 4).getStringCellValue();
			String PlayerTeamName = TeamName;

			ArrayList<Object> al = new ArrayList<Object>();
			al.add(PlayerType);
			al.add(PlayerPoint);
			al.add(PlayerCredit);
			al.add(PlayerTeamName);

			m0.put(PlayerName, al);

		}

		return m0;

	}

	// Specific set of players Selection
	public static Map<String, ArrayList<Object>> getAllPlayersOfSpecificType(String PlayerType) throws IOException{
		HashMap<String, ArrayList<Object>> m1 = getCompleteInformationOfaTeam(Team1Name);
		HashMap<String, ArrayList<Object>> m2 = getCompleteInformationOfaTeam(Team2Name);
		HashMap<String, ArrayList<Object>> newMap = new HashMap<String, ArrayList<Object>>();
		m1.putAll(m2);

		for (Entry<String, ArrayList<Object>> entry : m1.entrySet()) {
			if (PlayerType.contains("ALL")) {
				newMap.put(entry.getKey(), entry.getValue());
			}

			else if (entry.getValue().get(0).toString().contains(PlayerType)) {
				// log.info("Player Name: "+entry.getKey()+",[ Details: Team->
				// "+entry.getValue().get(3)+ ", Type->
				// "+entry.getValue().get(0)+ ", Points->
				// "+entry.getValue().get(1)+ ", Credits->
				// "+entry.getValue().get(2)+" ]");
				newMap.put(entry.getKey(), entry.getValue());
			}

		}

		return newMap;

	}

	public static Map<String, ArrayList<Object>> GetTopPlayersOfSpecificType(Map<String, ArrayList<Object>> Team1,
			String PlayerType, int NoOfPlayers) throws IOException{
		int iCnt = 0;
		Map<String, ArrayList<Object>> NewTeam = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> bat3 = new HashMap<String, ArrayList<Object>>();
		NewTeam = PlayerManager.sort(Team1);

		for (Entry<String, ArrayList<Object>> entry : NewTeam.entrySet()) {
			if (iCnt < NoOfPlayers && entry.getValue().get(0).toString().contains(PlayerType)) {
				bat3.put(entry.getKey(), entry.getValue());
				iCnt++;
			}
		}
		return bat3;
	}

	public static Map<String, ArrayList<Object>> GetTopPlayersOfSpecificType(String PlayerType, int NoOfPlayers)
			throws IOException{
		int iCnt = 0;
		Map<String, ArrayList<Object>> AllPlayers = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> selectedPlayers = new HashMap<String, ArrayList<Object>>();
		AllPlayers = PlayerManager.sort(PlayerManager.getAllPlayersOfSpecificType(PlayerType));
		for (Entry<String, ArrayList<Object>> entry : AllPlayers.entrySet()) {
			if (iCnt < NoOfPlayers) {
				selectedPlayers.put(entry.getKey(), entry.getValue());
				iCnt++;
			}
		}
		return selectedPlayers;
	}

	// Choosing last Seven Players:
	public static Map<String, ArrayList<Object>> GetPlayerDetailsOfACombination(Map<String, ArrayList<Object>> Team,
			int NoOfBatsman, int NoOfBowler, int NoOfAL) throws IOException{
		Map<String, ArrayList<Object>> Last3 = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> bat = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> al = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> bowl = new HashMap<String, ArrayList<Object>>();

		bat = PlayerManager.GetTopPlayersOfSpecificType(Team, "Bat", NoOfBatsman);
		bowl = PlayerManager.GetTopPlayersOfSpecificType(Team, "Bowl", NoOfBowler);
		al = PlayerManager.GetTopPlayersOfSpecificType(Team, "AL", NoOfAL);

		Last3.putAll(bat);
		Last3.putAll(bowl);
		Last3.putAll(al);

		return Last3;
	}

	public static Map<String, ArrayList<Object>> getBestPossibleSetOfLast10Players(
			HashMap<String, ArrayList<Object>> Ready1) throws IOException{
		Map<String, ArrayList<Object>> IntrimCombination = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> FinalCombination = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> AllPlayers = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> RemainingPlayers = new HashMap<String, ArrayList<Object>>();

		// Calculating remaining credits after Top1 players taken.

		double remainingCredits = getRemainingCredits(Ready1);
		System.out.println();
		log.info("Remaining Credits: " + remainingCredits);

		// Getting all Players from Both Team

		AllPlayers = getAllPlayersOfSpecificType("ALL");
		System.out.println();

		// removing already taken Top1 players from All player's List:

		for (Entry<String, ArrayList<Object>> entry : Ready1.entrySet()) {
			AllPlayers.remove(entry.getKey());
		}
		System.out.println("remainging map Size: "+AllPlayers.keySet().size());

		// Storing the remaining players in another hashmap:

		RemainingPlayers.putAll(AllPlayers);

		// Genrating Possible combinations for the leftover 7 players:

		ArrayList<ArrayList<Integer>> PossibleCombination = new ArrayList<ArrayList<Integer>>();
		PossibleCombination = GetPossibleCombination(10);
		
		FinalCombination = GetPlayerDetailsOfACombination(RemainingPlayers, PossibleCombination.get(0).get(1), PossibleCombination.get(0).get(2),
				PossibleCombination.get(0).get(0));

		// Considering all possible combinations and Checking out the best:

		for (int i = 1; i < PossibleCombination.size(); i++) {
			ArrayList<Integer> Combination = PossibleCombination.get(i);
			IntrimCombination = GetPlayerDetailsOfACombination(RemainingPlayers, Combination.get(1), Combination.get(2),
					Combination.get(0));

			double Cr1 = getTotalCreditsOrPoints(FinalCombination, "Credit");
			double Pr1 = getTotalCreditsOrPoints(FinalCombination, "Point");

			double Cr2 = getTotalCreditsOrPoints(IntrimCombination, "Credit");
			double Pr2 = getTotalCreditsOrPoints(IntrimCombination, "Point");

			boolean NoOfPlayersCrossedSevenFrom1stCombination = validatePlayerCap(Ready1, FinalCombination,
					"Existing Combination");
			boolean NoOfPlayersCrossedSevenFrom2ndCombination = validatePlayerCap(Ready1, IntrimCombination,
					"New Combination");

			// log.info("Combination "+i+" -> Credit: "+Cr1+" ,Point: "+Pr1);
			// log.info("Combination "+(i+1)+" -> Credit: "+Cr2+" ,Point:
			// "+Pr2);

			if (Cr1 <= remainingCredits && Cr2 <= remainingCredits && NoOfPlayersCrossedSevenFrom1stCombination) {
				if (Pr1 > Pr2) {
					log.info("Combination " + i + " retained. " + FinalCombination);
					System.out.println();
				}

				else if (NoOfPlayersCrossedSevenFrom2ndCombination) {
					log.info("Combination " + (i + 1) + " updated. " + IntrimCombination);
					System.out.println();
					FinalCombination.clear();
					FinalCombination.putAll(IntrimCombination);
				}
			}

			else if (Cr1 <= Cr2 && Cr1 <= remainingCredits && NoOfPlayersCrossedSevenFrom1stCombination) {
				log.info("Combination " + i + " retained. " + FinalCombination);
				System.out.println();
			}

			else if (Cr2 <= remainingCredits && NoOfPlayersCrossedSevenFrom2ndCombination) {
				FinalCombination.clear();
				FinalCombination.putAll(IntrimCombination);
				log.info("Combination " + (i + 1) + " updated. " + IntrimCombination);
				System.out.println();
			}

		}

		return FinalCombination;
	}

	public static ArrayList<ArrayList<Integer>> GetPossibleCombination(int sum){
		int k;
		int counter = 0;
		ArrayList<ArrayList<Integer>> Combination = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i <= 3; i++) // = added
		{
			for (int j = 3; j <= 5; j++) // changed from 2 to 3 added & = added
			{
				k = sum - j - i;
				if (k <= 5 & k > 2) // = added , changed > 2 from >1
				{
					if (i >= 0 && j >= 0 && k >= 0) {
						ArrayList<Integer> p = new ArrayList<>();
						p.addAll(Arrays.asList(i, j, k));
						Combination.add(counter, p);
						counter++;
					}
				}
			}
		}
		log.info("Possible Combination: AL:BAT:BOWL "+Combination);
		System.out.println();
		return Combination;
	}

	// Sorting Data
	static Map<String, ArrayList<Object>> sort(Map<String, ArrayList<Object>> unsortMap){

		List<Map.Entry<String, ArrayList<Object>>> list = new LinkedList<Map.Entry<String, ArrayList<Object>>>(
				unsortMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<Object>>>() {

			public int compare(Map.Entry<String, ArrayList<Object>> o1, Map.Entry<String, ArrayList<Object>> o2) {
				double a = (double) o1.getValue().get(1);
				double b = (double) o2.getValue().get(1);

				int cmp = a > b ? -1 : a < b ? +1 : 0;
				return cmp;
			}

		});

		// log.info(list);

		Map<String, ArrayList<Object>> result = new LinkedHashMap<String, ArrayList<Object>>();
		for (Map.Entry<String, ArrayList<Object>> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	// Data Filtration
	public static ArrayList<Object> updatePoint(ArrayList<Object> ar, double d){
		double updatedPoint = (double) ar.get(1) * d;
		ar.set(1, updatedPoint);
		return ar;

	}

	public static Map<String, ArrayList<Object>> updateCaptainViceCaptainPoints(Map<String, ArrayList<Object>> Team){
		Map<String, ArrayList<Object>> Dream11 = new HashMap<String, ArrayList<Object>>();
		Dream11 = PlayerManager.sort(Team);
		int icount = 0;

		for (Entry<String, ArrayList<Object>> entry : Dream11.entrySet()) {
			if (icount == 0 && icount < 2) {
				entry.setValue(PlayerManager.updatePoint(entry.getValue(), 2));
				System.out.println();
				;
				log.info("Captain: " + entry.getKey() + ",[ Details: Team-> " + entry.getValue().get(3) + ", Type-> "
						+ entry.getValue().get(0) + ", Points-> " + entry.getValue().get(1) + ", Credit- "
						+ entry.getValue().get(2) + " ]");
			} else if (icount == 1 && icount < 2) {
				entry.setValue(PlayerManager.updatePoint(entry.getValue(), 1.5));
				System.out.println();
				;
				log.info("Vice-Captain: " + entry.getKey() + ",[ Details: Team-> " + entry.getValue().get(3)
						+ ", Type-> " + entry.getValue().get(0) + ", Points-> " + entry.getValue().get(1) + ", Credit- "
						+ entry.getValue().get(2) + " ]");
			}
			icount++;
		}

		return Dream11;
	}

	public static boolean validatePlayerCap(Map<String, ArrayList<Object>> TeamBest4,
			Map<String, ArrayList<Object>> TeamNew7, String CombinationName){
		int Team1Players = getPlayersCountFromATeam(TeamBest4, Team1Name);
		int Team2Players = getPlayersCountFromATeam(TeamBest4, Team2Name);

		for (Entry<String, ArrayList<Object>> entry : TeamNew7.entrySet()){
			if (entry.getValue().get(3).toString().contains(Team1Name)) {
				++Team1Players;
			} else if (entry.getValue().get(3).toString().contains(Team2Name)){
				++Team2Players;
			}

		}

		// log.info("Team1 Players vs Team2 Players in "+CombinationName+" :
		// "+Team1Players+","+Team2Players);

		if (Team1Players <= 7 && Team2Players <= 7) {
			return true;
		}

		else {
			return false;
		}
	}

	public static double getRemainingCredits(HashMap<String, ArrayList<Object>> Team){
		double totalCredits = 0;
		for (Entry<String, ArrayList<Object>> entry : Team.entrySet()) {
			totalCredits = totalCredits + (double) entry.getValue().get(2);

		}
		return (100.00 - totalCredits);

	}

	public static double getTotalCreditsOrPoints(Map<String, ArrayList<Object>> Team, String whatToget){
		double total = 0;

		for (Entry<String, ArrayList<Object>> entry : Team.entrySet()) {
			if (whatToget == "Credit") {
				total = total + (double) entry.getValue().get(2);
			} else if (whatToget == "Point") {
				total = total + (double) entry.getValue().get(1);
			}

		}
		return total;

	}

	public static int getPlayersCountFromATeam(Map<String, ArrayList<Object>> Team, String TeamName){
		int NoOfPlayers = 0;
		for (Entry<String, ArrayList<Object>> entry : Team.entrySet()) {
			if (entry.getValue().get(3).toString().contains(TeamName)) {
				NoOfPlayers++;
			}

		}
		return NoOfPlayers;

	}

	// Final methods to send mail:
	public static Map<String, ArrayList<Object>> getMyDream11(String Team1, String Team2) throws IOException{
		Team1Name = Team1;
		Team2Name = Team2;

		// log.info("Team1 Name: "+Team1Name);
		// log.info("Team2 Name: "+Team2Name);

		HashMap<String, ArrayList<Object>> Dream11 = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> UpdatedDream11 = new HashMap<String, ArrayList<Object>>();
		Map<String, ArrayList<Object>> BestPossible10 = new HashMap<String, ArrayList<Object>>();

		double totalCredit = 0.00;
		double totalPoints = 0.00;

		// -----------------------------------------------------------Top1 +
		// Last10
		// Combination--------------------------------------------------------------//

		// Getting TopWK Player

		Dream11.putAll(PlayerManager.GetTopPlayersOfSpecificType("WK", 1));
		log.info("@Top WK Pick: "); // Changed from 4 to 1

		for (Entry<String, ArrayList<Object>> entry : Dream11.entrySet()) {
			log.info("Player Name: " + entry.getKey() + ",[ Details: Team-> " + entry.getValue().get(3) + ", Type-> "
					+ entry.getValue().get(0) + ", Points-> " + entry.getValue().get(1) + ", Credit- "
					+ entry.getValue().get(2) + " ]");
		}

		// Getting Last10 Players

		BestPossible10 = PlayerManager.getBestPossibleSetOfLast10Players(Dream11);
		Dream11.putAll(BestPossible10);

		System.out.println();
		log.info("@Last 10 Pick: ");

		for (Entry<String, ArrayList<Object>> entry : BestPossible10.entrySet()) {
			log.info("Player Name: " + entry.getKey() + ",[ Details: Team-> " + entry.getValue().get(3) + ", Type-> "
					+ entry.getValue().get(0) + ", Points-> " + entry.getValue().get(1) + ", Credit- "
					+ entry.getValue().get(2) + " ]");
		}

		// Updating Captain & Vice Captain points:

		UpdatedDream11 = PlayerManager.updateCaptainViceCaptainPoints(Dream11);
		System.out.println();
		;

		// Finalised Dream11:

		log.info("# ---------My Dream11:------------ ");
		for (Entry<String, ArrayList<Object>> entry : UpdatedDream11.entrySet()) {
			log.info("Player Name: " + entry.getKey() + ",[ Details: Team-> " + entry.getValue().get(3) + ", Type-> "
					+ entry.getValue().get(0) + ", Points-> " + entry.getValue().get(1) + ", Credit- "
					+ entry.getValue().get(2) + " ]");
			totalCredit = totalCredit + (double) entry.getValue().get(2);
			totalPoints = totalPoints + (double) entry.getValue().get(1);
		}

		System.out.println();
		log.info("Total Credits Used out of 100: " + totalCredit);
		log.info("Total points Gain: " + totalPoints);

		return UpdatedDream11;
	}

	public static void MailMyDream11(String Team1, String Team2) throws MessagingException, IOException{

		double totalCredit = 0.00;
		double totalPoints = 0.00;
		Map<String, ArrayList<Object>> Dream11 = new HashMap<String, ArrayList<Object>>();
		Dream11 = PlayerManager.getMyDream11(Team1, Team2);
		for (Entry<String, ArrayList<Object>> entry : Dream11.entrySet()) {
			totalCredit = totalCredit + (double) entry.getValue().get(2);
			totalPoints = totalPoints + (double) entry.getValue().get(1);
		}
		Multipart mp = new MimeMultipart();
		BodyPart htmlPart1 = new MimeBodyPart();

		StringBuilder email = new StringBuilder();
		email.append("<html><body text-align: center>");
		email.append("<div");

		email.append("<p><em> Hey Folks, </em></p>");
		email.append("<p style=\"color:Tomato;\"><em><br/><b><i> Your Dream11 for " + Team1 + " vs " + Team2
				+ " is all set..</i></b><br/><em></p>");

		email.append("<p><b> Total Credits Used: </b>" + totalCredit + "<br/>" + "<b>Total Points Gathered: </b>"
				+ totalPoints + "</p>");

		email.append("<table border = '1' border-collapse: collapse >");
		email.append("<tr>");

		email.append("<tr bgcolor=\"#33CC99\">");
		email.append("<th>");
		email.append("Player Name");
		email.append(" </th>");
		email.append("<th>");
		email.append("Player Type");
		email.append("</th>");
		email.append("<th>");
		email.append("Points");
		email.append("</th>");
		email.append("<th>");
		email.append("Credits");
		email.append("</th>");
		email.append("<th>");
		email.append("Team Name");
		email.append("</th>");
		email.append("</tr>");

		int iDecide = 0;
		for (Entry<String, ArrayList<Object>> entry : Dream11.entrySet()) {
			email.append("<tr align=\"center\">");
			if (iDecide == 0) {
				email.append("<td>");
				email.append(entry.getKey() + " (*C)");
				email.append("</td>");
			}

			else if (iDecide == 1) {
				email.append("<td>");
				email.append(entry.getKey() + " (*VC)");
				email.append("</td>");
			}

			else {
				email.append("<td>");
				email.append(entry.getKey());
				email.append("</td>");
			}

			for (int jCnt = 0; jCnt < 4; jCnt++) {
				email.append("<td>");
				email.append(entry.getValue().get(jCnt));
				email.append("</td>");
			}
			email.append("</tr>");
			iDecide++;
		}
		email.append("</table>");

		email.append("</table></body></html>");

		email.append("<p><em> With regards," + "<br/>" + "Chiranjit" + "<br/>" + "<em></p>");

		htmlPart1.setContent(email.toString(), "text/html; charset=utf-8");
		mp.addBodyPart(htmlPart1);

		SendEmailJar.send("AutoCreditMail@gmail.com", "Test@12345#", "chiranjit.halder@gmail.com", "Dream11", mp);
	}

}
