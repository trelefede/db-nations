package org.lessons.java.db.java.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		String url = "jdbc:mysql://127.0.0.1:8889/dump_nations";
		String user = "root";
		String password = "root";

		Scanner sc = new Scanner(System.in);
		System.out.println("Filtra i risultati. Inserisci la parola da ricercare: ");
		String userInput = sc.nextLine();

		try (Connection con = DriverManager.getConnection(url, user, password)) {
			System.out.println("Connessione OK");

			// query
			String sql = "SELECT c.name  AS country_name,c.country_id AS country_id,r.name AS region_name, cn.name  AS continent_name  \n"
					+ "FROM dump_nations.countries c\n" + "INNER JOIN dump_nations.regions r\n"
					+ "ON c.region_id  = r.region_id \n" + "INNER JOIN dump_nations.continents cn \n"
					+ "ON r.continent_id  = cn.continent_id \n" + "WHERE c.name LIKE ? \n" + "ORDER BY c.name ";

			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, "%" + userInput + "%");

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String countryName = rs.getString(1);
						int countryId = rs.getInt(2);
						String regionName = rs.getString(3);
						String continentName = rs.getString(4);

						System.out.println("Country Name: " + countryName + " - Country id: " + countryId
								+ " - Region Name: " + regionName + " - Continent name: " + continentName + "\n");
					}
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
