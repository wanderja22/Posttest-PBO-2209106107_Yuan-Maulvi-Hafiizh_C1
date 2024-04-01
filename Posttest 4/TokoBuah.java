import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TokoBuah {
    private ArrayList<Buah> daftarBuah = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static class Buah {
        protected String nama;
        protected int stok;

        public Buah(String nama, int stok) {
            this.nama = nama;
            this.stok = stok;
        }

        @Override
        public String toString() {
            return String.format("| %-20s | %-10s | %-10s |", nama, stok, "");
        }
    }

    public static class BuahSegar extends Buah {
        private boolean organik;

        public BuahSegar(String nama, int stok, boolean organik) {
            super(nama, stok);
            this.organik = organik;
        }

        @Override
        public String toString() {
            String jenis = organik ? "Organik" : "Non-organik";
            return String.format("| %-20s | %-10s | %-11s |", nama, stok, jenis);
        }
    }

    public class Transaksi {
        private String namaBuah;
        private int jumlah;
        private Date tanggal;
    
        public Transaksi(String namaBuah, int jumlah) {
            this.namaBuah = namaBuah;
            this.jumlah = jumlah;
            this.tanggal = new Date();
        }
    
        @Override
        public String toString() {
            return String.format("| %-20s | %-10s | %-10s | %-10s |", namaBuah, jumlah, tanggal, "");
        }
    }    

    public void tambahBuah(String namaBuah, int stokBuah, String inputOrganik) {
        boolean organik = inputOrganik.equalsIgnoreCase("Y");
        Buah buah;
        if (organik) {
            buah = new BuahSegar(namaBuah, stokBuah, true);
        } else {
            buah = new BuahSegar(namaBuah, stokBuah, false);
        }
        daftarBuah.add(buah);
        System.out.println("Buah berhasil ditambahkan!");
        tungguInput();
    }
    
    public void tambahBuah(String namaBuah, int stokBuah) {
        Buah buah = new BuahSegar(namaBuah, stokBuah, false);
        daftarBuah.add(buah);
        System.out.println("Buah berhasil ditambahkan!");
        tungguInput();
    }

    public void tampilkanSemuaBuah() {
        clearScreen();
        if (daftarBuah.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("|       Daftar Buah kosong          |");
            System.out.println("=====================================");
        } else {
            System.out.println("===================================================");
            System.out.printf("| %-20s | %-10s | %-10s  |\n", "Nama Buah", "Stok", "Jenis");
            System.out.println("===================================================");

            for (Buah buah : daftarBuah) {
                System.out.println(buah.toString());
            }

            System.out.println("===================================================");
        }
        tungguInput();
    }

    public void updateStokBuah() {
        System.out.print("Masukkan nama buah yang ingin diupdate: ");
        String namaBuah = scanner.next();

        for (Buah buah : daftarBuah) {
            if (buah.nama.equalsIgnoreCase(namaBuah)) {
                System.out.print("Masukkan stok baru: ");
                int stokBaru = scanner.nextInt();
                buah.stok = stokBaru;
                System.out.println("Stok buah berhasil diupdate!");
                tungguInput();
                return;
            }
        }

        System.out.println("Buah dengan nama " + namaBuah + " tidak ditemukan.");
        tungguInput();
    }

    public void hapusBuah() {
        System.out.print("Masukkan nama buah yang ingin dihapus: ");
        String namaBuah = scanner.next();

        for (Buah buah : daftarBuah) {
            if (buah.nama.equalsIgnoreCase(namaBuah)) {
                daftarBuah.remove(buah);
                System.out.println("Buah berhasil dihapus!");
                tungguInput();
                return;
            }
        }

        System.out.println("Buah dengan nama " + namaBuah + " tidak ditemukan.");
        tungguInput();
    }

    public void tambahTransaksi() {
        System.out.print("Masukkan nama buah untuk transaksi: ");
        String namaBuah = scanner.next();
    
        if (namaBuah.trim().isEmpty()) {
            System.out.println("Nama buah tidak boleh kosong!");
            tungguInput();
            return;
        }
    
        boolean buahDitemukan = false;
        Buah buahPilihan = null;
        for (Buah buah : daftarBuah) {
            if (buah.nama.equalsIgnoreCase(namaBuah)) {
                buahDitemukan = true;
                buahPilihan = buah;
                break;
            }
        }
    
        if (!buahDitemukan) {
            System.out.println("Buah dengan nama " + namaBuah + " tidak ditemukan.");
            tungguInput();
            return;
        }
    
        System.out.print("Masukkan jumlah buah untuk transaksi: ");
        int jumlah = scanner.nextInt();
    
        if (jumlah > buahPilihan.stok) {
            System.out.println("Jumlah transaksi melebihi stok yang tersedia!");
            tungguInput();
            return;
        }
    
        Transaksi transaksi = new Transaksi(namaBuah, jumlah);
        System.out.println("Transaksi berhasil ditambahkan:");
        System.out.println("====================================================================");
        System.out.printf("| %-20s | %-10s | %-10s                   | %-10s |\n", "Nama Buah", "Jumlah", "Tanggal", "Keterangan");
        System.out.println("====================================================================");
        System.out.println(transaksi.toString());
        System.out.println("====================================================================");
        buahPilihan.stok -= jumlah;
        tungguInput();
    }    


    private void tungguInput() {
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
        scanner.nextLine();
        clearScreen();
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        TokoBuah tokoBuah = new TokoBuah();
        int pilihan;

        do {
            clearScreen();
            System.out.println("=================================================");
            System.out.println("|                 Menu Toko Buah                |");
            System.out.println("================================================");
            System.out.println("| 1. Tambah Buah                                |");
            System.out.println("| 2. Tampilkan Semua Buah                       |");
            System.out.println("| 3. Update Stok Buah                           |");
            System.out.println("| 4. Hapus Buah                                 |");
            System.out.println("| 5. Tambah Transaksi                           |");
            System.out.println("| 6. Keluar                                     |");
            System.out.println("===============================================");
            System.out.print("Pilih menu (1-6): ");

            pilihan = tokoBuah.scanner.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan nama buah: ");
                    String namaBuah = tokoBuah.scanner.next();
                    System.out.print("Masukkan stok buah: ");
                    int stokBuah = tokoBuah.scanner.nextInt();
                    System.out.print("Apakah buah tersebut organik? (Y/N): ");
                    String organikInput = tokoBuah.scanner.next();
                    tokoBuah.tambahBuah(namaBuah, stokBuah, organikInput.equalsIgnoreCase("Y") ? "Y" : "N");
                    break;
                case 2:
                    tokoBuah.tampilkanSemuaBuah();
                    break;
                case 3:
                    tokoBuah.updateStokBuah();
                    break;
                case 4:
                    tokoBuah.hapusBuah();
                    break;
                case 5:
                    tokoBuah.tambahTransaksi();
                    break;
            }

        } while (pilihan != 6);

        tokoBuah.scanner.close();
    }
}
