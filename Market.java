import java.sql.*;

public class Market {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/market?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
							"student", "student");

			stmt = conn.createStatement();

			// String
			// strInsert="insert into market values (1006,'kava','Franck','21.33','40')";
			// System.out.println("Zahtjev za update je " + strInsert);
			// int countInserted=stmt.executeUpdate(strInsert);
			// System.out.println(countInserted + " proizvod ubačen.\n");
			// System.out.println();

			// String sqlDelete="delete from market where id>=1006";
			// System.out.println("Zahtjev prema SQLu je: " + sqlDelete);
			// int countDeleted = stmt.executeUpdate(sqlDelete);
			// System.out.println(countDeleted + " proizvod obrisan.\n");
			// System.out.println();

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
			String MaxProizvod;
			String MinProizvod;

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
					System.out.println("Trenutni max Row je:" + maxRow);
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

			rset.absolute(maxRow);
			MaxProizvod = rset.getString("artikal");
			System.out
					.println("proizvod sa najvecom cijenom je=" + MaxProizvod);

			rset.absolute(minRow);
			MinProizvod = rset.getString("artikal");
			System.out.println("proizvod sa najmanjom cijenom je="
					+ MinProizvod);

			System.out.println();
			System.out.println("Broj razlicitih sadržaja u marketu je: "
					+ rowCount);
			System.out.println("Ukupna količina svih proizvada u marketu je "
					+ sumaKol);
			System.out.println("Najveća cijena od svih proizvoda je "
					+ maxCijena + "a taj proizvod je" + MaxProizvod);
			System.out.println("Najmanja cijena od svih proizvoda je "
					+ minCijena + "a taj proizvod je:" + MinProizvod);
			System.out.println("Prosjecna cijena prozvoda je " + prosjCijena);

			System.out.println();

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
