package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

	private static String filepath = "datafiles/raktar2.txt";
	// kilogrammban
	private static final int zsaksuly = 20;
	private static final int szanteherbiras = 2500;

	public static ArrayList<Raktar> raktarak = new ArrayList<>();



	public static void main(String[] args) throws InterruptedException {

		System.out.println("ha futatás közben az \"Infoprog\" nem zöld akkor az helyi hiba, mert IntelliJ IDEA Ultimate-ben és Windows Terminal-ban nekem ment - Tanko A.");
        Scanner sc = new Scanner(System.in);
		beolv();
        while (true) {

            System.out.print("╒=======================╕\n" +
                             "| \u001B[1;32mInfoprog 2022\u001B[1;0m         |\n" +
                             "|                       |\n" +
                             "| Egy szám beírásával   |\n" +
                             "| lehet kiválasztani,   |\n" +
                             "| hogy melyik feladat   |\n" +
                             "| fusson le             |\n" +
                             "╘=======================╛\n" +
                             " 0 - kilépés, 1 - 1.feladat és 2.feladat, 3 - 3.feladat, 4 - 4.feladat, 5 - 5.feladat \n" +
                             "Ide: ");
            String input = sc.next();
            int x;
            try  {
				x = Integer.parseInt(input);
			} catch (NumberFormatException e) {
            	System.out.println("\"" + input+ "\"" + " nem jó bemenet");
				TimeUnit.SECONDS.sleep(2);
				for (int i = 0; i < 20; i++) {
					System.out.println();
				}
            	continue;
			}

			switch (x) {
			case 0:
				System.out.println("Kilépés...");
				sc.close();
				return;
			case 1:
				raktarak = new ArrayList<>();
				beolv();
				System.out.println("===1/2. Feladat===");
				feladat1_2();
				break;
			case 3:
				raktarak = new ArrayList<>();
				beolv();
				System.out.println("===3. Feladat===");
				feladat3();
				break;
			case 4:
				raktarak = new ArrayList<>();
				beolv();
				System.out.println("===4. Feladat===");
				feladat4();
				break;
			case 5:
				raktarak = new ArrayList<>();
				beolv();
				System.out.println("===5. Feladat===");
				feladat5(feladat4());
				break;

			}
            
            while (true) {
                try {
                    System.out.println("Nyomja meg az \"ENTER\"-t ha tovább akar menni");
                    if (System.in.read() == 10) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }


        }
       
    }

	public static void feladat1_2() {
		try {
			int zsakokSzamaASzanon = szanteherbiras / zsaksuly;
			int elsoNemNullaRaktarIndexe;
			int utakszama = 0;
			int megtettKilometerek = 0;

			outer: while (mennyiKellMeg(raktarak) != 0) {
				utakszama++;
				elsoNemNullaRaktarIndexe = legkozelebbiNemNullaKeszlet(raktarak);
				megtettKilometerek += mennyitav(raktarak.get(0).getLat(), raktarak.get(0).getLon(), raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon());
				zsakokSzamaASzanon = szanteherbiras / zsaksuly;
				while (zsakokSzamaASzanon != 0) {
					System.out.printf("Érintett raktár: %d, %d; eddig megtett Km-ek: %d \n", raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon(), megtettKilometerek);
					if (zsakokSzamaASzanon > raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet()) {
						zsakokSzamaASzanon -= raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet();
						raktarak.get(elsoNemNullaRaktarIndexe).setKeszlet(0);
						int aktraktar = elsoNemNullaRaktarIndexe;
						elsoNemNullaRaktarIndexe = legkozelebbiNemNullaKeszlet(raktarak);
						megtettKilometerek += mennyitav(raktarak.get(aktraktar).getLat(), raktarak.get(aktraktar).getLon(), raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon());
					} else {
						raktarak.get(elsoNemNullaRaktarIndexe).
                                setKeszlet(raktarak.get(elsoNemNullaRaktarIndexe).
                                        getKeszlet() - zsakokSzamaASzanon);
						zsakokSzamaASzanon = 0;
					}
					if (elsoNemNullaRaktarIndexe == raktarak.size()) {
						String s = "," + zsakokSzamaASzanon;
						Files.write(Paths.get(filepath), s.getBytes(), StandardOpenOption.APPEND);
						raktarak.get(elsoNemNullaRaktarIndexe - 1).setKeszlet(zsakokSzamaASzanon);
						System.out.println("Maradek zsakok szama hozza adva a raktar.txt-hez");
						break outer;
					}
				}
				megtettKilometerek += mennyitav(raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon(), raktarak.get(0).getLat(), raktarak.get(0).getLon());
				System.out.println("Vissza Lapföldre");
			}
			// theoretical distance 24254 for raktar2.txt
			System.out.printf("Utolsó raktárba vitt zsákok: %d, megtett kilométerek: %d, megtett utakszáma: %d \n", zsakokSzamaASzanon, megtettKilometerek, utakszama);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void feladat3() {
		int zsakokSzamaASzanon;
		int elsoNemNullaRaktarIndexe;
		int utakszama = 0;
		int megtettKilometerek = 0;

		outer:while (mennyiKellMeg(raktarak) != 0) {
			utakszama++;
			elsoNemNullaRaktarIndexe = legkozelebbiNemNullaKeszlet(raktarak);
			megtettKilometerek += mennyitav(raktarak.get(0).getLat(), raktarak.get(0).getLon(), raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon());
			zsakokSzamaASzanon = szanteherbiras / zsaksuly;
			while (zsakokSzamaASzanon != 0) {
				if(elsoNemNullaRaktarIndexe == 0) {
					break outer;
				}
				System.out.printf("Érintett raktár: %d, %d; eddig megtett Km-ek: %d \n", raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon(), megtettKilometerek);
				if (zsakokSzamaASzanon > raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet()) {
					zsakokSzamaASzanon -= raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet();
					raktarak.get(elsoNemNullaRaktarIndexe).setKeszlet(0);
					int aktraktar = elsoNemNullaRaktarIndexe;
					elsoNemNullaRaktarIndexe = legkozelebbiNemNullaKeszlet(raktarak);
					if (zsakokSzamaASzanon < raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet()) {
						break;
					}
					megtettKilometerek += mennyitav(raktarak.get(aktraktar).getLat(), raktarak.get(aktraktar).getLon(), raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon());
				} else {
					raktarak.get(elsoNemNullaRaktarIndexe).setKeszlet(raktarak.get(elsoNemNullaRaktarIndexe).getKeszlet() - zsakokSzamaASzanon);
					zsakokSzamaASzanon = 0;
				}
				
			}
			megtettKilometerek += mennyitav(raktarak.get(elsoNemNullaRaktarIndexe).getLat(), raktarak.get(elsoNemNullaRaktarIndexe).getLon(), raktarak.get(0).getLat(), raktarak.get(0).getLon());
			System.out.println("Vissza Lapföldre");
		}
		System.out.printf("Megtett kilometerek: %d, megtett utakszama: %d \n", megtettKilometerek, utakszama);
	}

	public static ArrayList<ArrayList<Integer>> feladat4() {

		int[][] tavok = raktarakkoztitav();
		ArrayList<ArrayList<Integer>> utvonalak = new ArrayList<>();
		utvonalak.add(new ArrayList<>());
		int aktualisUtvonal = 0;
		int futasszama = 0;
		utvonalak.get(0).add(0);

		while (aktualisUtvonal < utvonalak.size()) {
			for (int j = 0; j < tavok.length - 1; j++) {
				int aktsor = utvonalak.get(aktualisUtvonal).get(utvonalak.get(aktualisUtvonal).size() - 1);
				int mintav = Integer.MAX_VALUE;
				for (int i = 0; i < tavok.length; i++) {
					if ((tavok[aktsor][i] < mintav) && !(utvonalak.get(aktualisUtvonal).contains(i))
							&& (tavok[aktsor][i] != 0)) {
						mintav = tavok[aktsor][i];
					}
				}
				int elso = 0;
				boolean volteMarTalalva = false;
				for (int i = 0; i < tavok.length; i++) {
					if ((tavok[aktsor][i] == mintav) && !(utvonalak.get(aktualisUtvonal).contains(i))
							&& !volteMarTalalva) {
						elso = i;
						volteMarTalalva = true;
						continue;
					}
					if ((tavok[aktsor][i] == mintav) && !(utvonalak.get(aktualisUtvonal).contains(i))) {
						ArrayList<Integer> list = new ArrayList<>(utvonalak.get(aktualisUtvonal));
						list.add(i);
						utvonalak.add(list);
					}
				}
				if (volteMarTalalva) {
					utvonalak.get(aktualisUtvonal).add(elso);
				}
			}
			aktualisUtvonal++;
			futasszama++;
			System.out.printf("Ennyi útvonal van: %d , futások: %d \n", utvonalak.size(), futasszama);
		}
		for (ArrayList<Integer> integers : utvonalak) {
			System.out.println(integers.toString());
		}
		return utvonalak;
	}

	public static void feladat5(ArrayList<ArrayList<Integer>> utvonalak) {
        for (ArrayList<Integer> integers : utvonalak) {
            ArrayList<Raktar> tempraktarak = new ArrayList<>();
            for (Integer integer : integers) {
                tempraktarak.add(raktarak.get(integer));
            }

            int zsakokSzamaASzanon = szanteherbiras / zsaksuly;
            int elsoNemNullaRaktarIndexe;
            int utakszama = 0;
            int megtettKilometerek = 0;

            while (mennyiKellMeg(tempraktarak) != 0) {

                utakszama++;
                elsoNemNullaRaktarIndexe = legkozelebbiNemNullaKeszlet(tempraktarak);
                megtettKilometerek += 2 * mennyitav(70, 23, tempraktarak.get(elsoNemNullaRaktarIndexe).getLat(), tempraktarak.get(elsoNemNullaRaktarIndexe).getLon());
                zsakokSzamaASzanon = szanteherbiras / zsaksuly;

                while (zsakokSzamaASzanon != 0) {
                    if (elsoNemNullaRaktarIndexe >= tempraktarak.size()) {
                        return;
                    }
                    System.out.printf("Érintett raktár: %d, %d; eddig megtett Km-ek: %d \n", tempraktarak.get(elsoNemNullaRaktarIndexe).getLat(), tempraktarak.get(elsoNemNullaRaktarIndexe).getLon(), megtettKilometerek);
                    if (zsakokSzamaASzanon > tempraktarak.get(elsoNemNullaRaktarIndexe).getKeszlet()) {
                        zsakokSzamaASzanon -= tempraktarak.get(elsoNemNullaRaktarIndexe).getKeszlet();
                        tempraktarak.get(elsoNemNullaRaktarIndexe).setKeszlet(0);
                        elsoNemNullaRaktarIndexe++;
                    } else {
                        tempraktarak.get(elsoNemNullaRaktarIndexe).setKeszlet(tempraktarak.get(elsoNemNullaRaktarIndexe).getKeszlet() - zsakokSzamaASzanon);
                        zsakokSzamaASzanon = 0;
                    }

                }
                System.out.println("Vissza Lapföldre");
            }
            System.out.printf("Utolsó raktárba vitt zsákok: %d, megtett kilométerek: %d, megtett utakszáma: %d \n", zsakokSzamaASzanon, megtettKilometerek, utakszama);
        }
	}

	public static int mennyiKellMeg(ArrayList<Raktar> raktaryes) {
		int mennyiZsakKellMeg = 0;
		for (Raktar raktar : raktaryes) {
			mennyiZsakKellMeg += raktar.getKeszlet();
		}
		return mennyiZsakKellMeg;
	}

	public static int[][] raktarakkoztitav() {
		int[][] tavok = new int[raktarak.size()][raktarak.size()];
		for (int i = 0; i < raktarak.size(); i++) {
			for (int j = 0; j < raktarak.size(); j++) {
				int tav = mennyitav(raktarak.get(i).getLat(), raktarak.get(i).getLon(), raktarak.get(j).getLat(),
						raktarak.get(j).getLon());
				tavok[i][j] = tav;
			}
		}
		return tavok;
	}

	public static int legkozelebbiNemNullaKeszlet(ArrayList<Raktar> raktaryes) {
		int nemNullaRaktarIndexe = 0;
		for (int i = 0; i < raktaryes.size(); i++) {
			// 1. raktar aminek keszlete nem 0
			if (raktaryes.get(i).getKeszlet() != 0) {
				nemNullaRaktarIndexe = i;
				break;
			}
		}
		return nemNullaRaktarIndexe;
	}
	
	// forras: https://www.movable-type.co.uk/scripts/latlong.html
	// haversine egyenlet
	public static int mennyitav(int lat1, int lon1, int lat2, int lon2) {
		final double r = 6371e3;
		double Phi1 = lat1 * Math.PI / 180;
		double Phi2 = lat2 * Math.PI / 180;
		double dPhi = (lat2 - lat1) * Math.PI / 180;
		double dLambda = (lon2 - lon1) * Math.PI / 180;

		double a = Math.sin(dPhi / 2) * Math.sin(dPhi / 2)
				+ Math.cos(Phi1) * Math.cos(Phi2) * Math.sin(dLambda / 2) * Math.sin(dLambda / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (int) (r * c) / 1000; // kilometerben
	}

	

	public static void beolv() {
		try {
			System.out.println("Beolvasás");
			File f = new File(filepath);
			Scanner sc2 = new Scanner(f);
			boolean yes = true;
			while (sc2.hasNextLine()) {
				String scnextlinestring = sc2.nextLine();
				Scanner sc = new Scanner(scnextlinestring).useDelimiter(",");
				Raktar r = new Raktar();
				if (yes) {
					yes = false;
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
				} else if (!sc2.hasNextLine()) {
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
					if (sc.hasNextInt()) {
						int maxcapkeszlet = sc.nextInt();
						r.setMaxcap(maxcapkeszlet);
						r.setKeszlet(maxcapkeszlet);
					}
				} else {
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
					int maxcapkeszlet = Integer.parseInt(sc.next());
					r.setMaxcap(maxcapkeszlet);
					r.setKeszlet(maxcapkeszlet);
				}
				
				raktarak.add(r);
				sc.close();
			}
			System.out.println("Beolvasva");
			sc2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
