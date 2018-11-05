import java.sql.*;

public class Market {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/market?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
							"student", "student");

			stmt = conn.createStatement();

			String strSelect = "select*from market";
			System.out.println("Zahtjev prema SQLu je: " + strSelect);
			System.out.println();

			rset = stmt.executeQuery(strSelect);

			System.out.println("Baza 'market' sadrži:");
			System.out.println();

			int rowCount = 0;
			int maxRow = 0;
			int minRow = 0;
			double sumaKol = 0;
			double maxCijena = 0;
			double minCijena = 0;
			double ukCijena = 0;
			double prosjCijena = 0;
			String maxArtikal;
			String minArtikal;

			String artikal;
			String proizvodjac;
			double cijena;
			int kolicina;

			while (rset.next()) {
				artikal = rset.getString("artikal");
				proizvodjac = rset.getString("proizvodjac");
				cijena = rset.getDouble("cijena");
				kolicina = rset.getInt("kolicina");

				System.out.println(artikal + ", " + proizvodjac + ", " + cijena
						+ ", " + kolicina);

				sumaKol = sumaKol + kolicina;
				++rowCount;

				if (maxCijena < cijena) {
					maxCijena = cijena;
					maxRow = rset.getRow();
				}

				if (rset.getRow() == 1) {
					minCijena = cijena;
					minRow = rset.getRow();
				} else if (minCijena > cijena) {
					minCijena = cijena;
					minRow = rset.getRow();
				}
				ukCijena = ukCijena + cijena;
			}
			
			prosjCijena = ukCijena / rowCount;

			rset.absolute(maxRow); //Moves the cursor to the given row number in this ResultSet object.
			maxArtikal = rset.getString("artikal");
			System.out.println("Artikal sa najvecom cijenom je: " + maxArtikal);

			rset.absolute(minRow);
			minArtikal = rset.getString("artikal");
			System.out.println("Artikal sa najmanjom cijenom je: " + minArtikal);
			System.out.println();
			
			System.out.println("Broj razlicitih sadržaja u marketu je: " + rowCount);
			System.out.println("Ukupna količina svih artikala u marketu je " + sumaKol);
			System.out.println("Najveća cijena od svih artikala je " + maxCijena + " a taj artikal je" + maxArtikal);
			System.out.println("Najmanja cijena od svih artikala je " + minCijena + " a taj artikal je:" + minArtikal);
			System.out.println("Prosjecna cijena artikla u marketu je " + prosjCijena);
			System.out.println();

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
