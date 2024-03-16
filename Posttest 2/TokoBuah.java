import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TokoBuah {
    private ArrayList<Buah> daftarBuah = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public class Buah {
        private String nama;
        private int stok;

        public Buah(String nama, int stok) {
            this.nama = nama;
            this.stok = stok;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public int getStok() {
            return stok;
        }

        public void setStok(int stok) {
            this.stok = stok;
        }

        @Override
        public String toString() {
            return "Buah{" +
                    "nama='" + nama + '\'' +
                    ", stok=" + stok +
                    '}';
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

        public String getNamaBuah() {
            return namaBuah;
        }

        public void setNamaBuah(String namaBuah) {
            this.namaBuah = namaBuah;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void setJumlah(int jumlah) {
            this.jumlah = jumlah;
        }

        public Date getTanggal() {
            return tanggal;
        }

        @Override
        public String toString() {
            return "Transaksi{" +
                    "namaBuah='" + namaBuah + '\'' +
                    ", jumlah=" + jumlah +
                    ", tanggal=" + tanggal +
                    '}';
        }
    }

    public void tambahBuah() {
        System.out.print("Masukkan nama buah: ");
        String namaBuah = scanner.next();
        System.out.print("Masukkan stok buah: ");
        int stokBuah = scanner.nextInt();

        Buah buah = new Buah(namaBuah, stokBuah);
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
            System.out.println("=====================================");
            System.out.printf("| %-20s | %-10s |\n", "Nama Buah", "Stok");
            System.out.println("=====================================");

            for (Buah buah : daftarBuah) {
                System.out.printf("| %-20s | %-10d |\n", buah.getNama(), buah.getStok());
            }

            System.out.println("=====================================");
        }
        tungguInput();
    }

    public void updateStokBuah() {
        System.out.print("Masukkan nama buah yang ingin diupdate: ");
        String namaBuah = scanner.next();

        for (Buah buah : daftarBuah) {
            if (buah.getNama().equalsIgnoreCase(namaBuah)) {
                System.out.print("Masukkan stok baru: ");
                int stokBaru = scanner.nextInt();
                buah.setStok(stokBaru);
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
            if (buah.getNama().equalsIgnoreCase(namaBuah)) {
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
            if (buah.getNama().equalsIgnoreCase(namaBuah)) {
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
    
        if (jumlah > buahPilihan.getStok()) {
            System.out.println("Jumlah transaksi melebihi stok yang tersedia!");
            tungguInput();
            return;
        }
    
        Transaksi transaksi = new Transaksi(namaBuah, jumlah);
        System.out.println("Transaksi berhasil ditambahkan:\n" + transaksi);
        buahPilihan.setStok(buahPilihan.getStok() - jumlah);
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
            System.out.println("================================================");
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
                    tokoBuah.tambahBuah();
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
