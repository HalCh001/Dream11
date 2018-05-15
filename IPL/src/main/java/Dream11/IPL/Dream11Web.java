package Dream11.IPL;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import Resources.BaseClass;

public class Dream11Web extends BaseClass
{
//encaptulation	
	@FindBy(how = How.ID, using = "login_desktop")
	protected WebElement Login;		
	@FindBy(how = How.ID, using = "login_jump")
	protected WebElement Signin;	
	@FindBy(how = How.ID, using = "LoginFormEmail")
	protected WebElement email;	
	@FindBy(how = How.ID, using = "LoginFormPassword")
	protected WebElement password;	
	@FindBy(how = How.ID, using = "LoginFormSubmit")
	protected WebElement Submit;	
	@FindBy(how = How.XPATH, using = "//*[text()='My Contests']")
	protected static WebElement MyContests;	
	@FindBy(how = How.XPATH, using = "//*[text()='Results']")
	protected WebElement ResultsTab;	
	@FindBy(how = How.XPATH, using = "//*[contains(@href,'/cricket/my-joined-leagues/Indian T20 League/811')]")
	protected WebElement SelectMatchContest;	
	@FindBy(how = How.XPATH, using = "//*[contains(@href,'/cricket/leaderboard/indian-t20-league/811')]")
	protected WebElement SelectLeaderBoardContest;		
	@FindBy(how = How.XPATH, using = "//*[text()='Score']")
	protected WebElement Score;	
	@FindBy(how = How.XPATH, using = "//table[@class='fantasy-scorecard__table']/tbody/tr")
	protected List<WebElement> PointsTableRow;			
	@FindBy(how = How.XPATH, using = "//*[text()='Create Team']")
	protected WebElement CreateTeam;	
	@FindBy(how = How.XPATH, using = "//*[@class='light']")
	protected WebElement TeamName;	
	@FindBy(how = How.XPATH, using = "//*[@class='create-team__team-selector__player-card__cell__col-player__header']")
	protected List<WebElement> Credit_Players;	
	@FindBy(how = How.XPATH, using = "//*[@class='create-team__team-selector__player-card__cell create-team__team-selector__player-card__cell__col-credit']")
	protected List<WebElement> Credits;	
	@FindBy(how = How.XPATH, using = "//*[text()='BAT']")	
	protected WebElement BatIcon;	
	@FindBy(how = How.XPATH, using = "//*[text()='AR']")
	protected WebElement ARIcon;	
	@FindBy(how = How.XPATH, using = "//*[text()='BOWL']")
	protected WebElement BowlIcon;	
	@FindBy(how = How.XPATH, using = "//*[@class='fantasy-scorecard__cell'][1]")
	protected List<WebElement> Points_Players;
	@FindBy(how = How.XPATH, using = "//*[@class='fantasy-scorecard__cell'][15]")
	protected List<WebElement> Points;
	@FindBy(how= How.XPATH,using = "//*[contains(@href,'/cricket/leagues/Indian T20 League/811')]")
	protected List<WebElement> Matches;
	@FindBy(how= How.XPATH,using = "//*[contains(text(),'My Teams')]")
	protected WebElement MyTeams;

	
//Getters & Setters	
	public WebElement getEmail() {
		return email;
	}
	public void setEmail(WebElement email) {
		this.email = email;
	}
	public WebElement getPassword() {
		return password;
	}
	public void setPassword(WebElement password) {
		this.password = password;
	}
	public WebElement getLogin() {
		return Login;
	}
	public WebElement getSignin() {
		return Signin;
	}
	public WebElement getSubmit() {
		return Submit;
	}
	public static By getMyContests() { 
		return By.xpath("//*[text()='My Contests']");
	}
	public WebElement getResultsTab() {
		return ResultsTab;
	}
	public WebElement getSelectMatchContest() {
		return SelectMatchContest;
	}
	public WebElement SelectLeaderBoardContest() {
		return SelectLeaderBoardContest;
	}
	public WebElement getScore() {
		return Score;
	}
	public List<WebElement> getPointsTableRow() {
		return PointsTableRow;
	}
	public static By getCreateTeam() {
		return By.xpath("//*[text()='Create Team']");
	}
	public static By getTeam() {
		return By.xpath("//*[@class='light']");
	}
	public static By getPlayersName() {
		return By.xpath("//*[@class='create-team__team-selector__player-card__cell__col-player__header']");
	}
	public static By getPlayerCredits() {
		return By.xpath("//*[@class='create-team__team-selector__player-card__cell create-team__team-selector__player-card__cell__col-credit']");
	}
	public WebElement getTeamName() {
		return TeamName;
	}
	public List<WebElement> getPlayers() {
		return Credit_Players;
	}
	public static By getContests() {
		return By.xpath("//*[contains(@href,'/cricket/my-joined-leagues/Indian T20 League/811')]");
	}
	public static By getMatch() {
		return By.xpath("//*[contains(@href,'/cricket/my-joined-leagues/Indian T20 League/811')]");
	}
	public static By ClickOnResultsTab() {
		return By.xpath("//*[text()='Results']");
	}
	public static By ClickOnLiveTab() {
		return By.xpath("//*[text()='Live']");
	}
	
	
	
	
	public List<WebElement> getCredits() {
		return Credits;
	}
	public List<WebElement> getPoints_Players() {
		return Points_Players;
	}
	public List<WebElement> getPoints() {
		return Points;
	}
	public WebElement getBatIcon() {
		return BatIcon;
	}
	public WebElement getARIcon() {
		return ARIcon;
	}
	public WebElement getBowlIcon() {
		return BowlIcon;
	}
	public List<WebElement> getMatches() {
		return Matches;
	}
	public WebElement getMyTeams() {
		return MyTeams;
	}

	
	

}
