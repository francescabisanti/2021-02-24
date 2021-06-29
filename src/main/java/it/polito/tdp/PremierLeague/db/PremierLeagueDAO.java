package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public void listAllPlayers(Map <Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
	
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				idMap.put(player.getPlayerID(), player);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID "
				+  "ORDER BY MatchID ASC ";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List <Player> getVertici(Map<Integer, Player> idMap, Match m){
		String sql="SELECT DISTINCT a.PlayerID AS id "
				+ "FROM actions a "
				+ "WHERE a.MatchID=? ";
		
		Connection conn = DBConnect.getConnection();
		List<Player> result= new ArrayList<>();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player p= idMap.get(res.getInt("id"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List <Adiacenza> getAdiacenza(Map<Integer, Player> idMap, Match m){
		String sql="SELECT a1.PlayerID AS p1, a2.PlayerID AS p2,((a1.TotalSuccessfulPassesAll+ a1.Assists)/ (a1.TimePlayed)) AS peso1,((a2.TotalSuccessfulPassesAll+ a2.Assists)/( a2.TimePlayed)) AS peso2 "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.TeamID<> a2.TeamID AND a1.MatchID=a2.MatchID AND a1.MatchID=? "
				+ "AND a1.PlayerID> a2.PlayerID "
				+ "GROUP BY a1.PlayerID, a2.PlayerID";
		
		Connection conn = DBConnect.getConnection();
		List<Adiacenza> result= new ArrayList<>();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player p1= idMap.get(res.getInt("p1"));
				Player p2= idMap.get(res.getInt("p2"));
				Double peso= res.getDouble("peso1")-res.getDouble("peso2");
				
				if(p1!=null && p2!=null) {
					if(peso>0) {
						Adiacenza a= new Adiacenza(p1,p2,peso);
						result.add(a);
					}
					else {
						Adiacenza a= new Adiacenza (p2,p1, Math.abs(peso));
						result.add(a);
					}
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Integer SquadraMigliore(Player p){
		String sql = "SELECT DISTINCT a.TeamID AS id "
				+ "FROM ACTIONs a "
				+ "WHERE a.PlayerID=? ";
		Integer t=null;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, p.getPlayerID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				t= res.getInt("id");

			}
			conn.close();
			return t;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
