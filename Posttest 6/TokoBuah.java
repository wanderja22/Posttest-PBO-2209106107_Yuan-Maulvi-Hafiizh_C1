import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

interface TransaksiLogger {
    void logTransaksi(String namaBuah, int jumlah, Date tanggal);
    void clearLog();
}

final public class TokoBuah implements TransaksiLogger {
    private final ArrayList<Buah> daftarBuah = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static int totalTransaksi = 0;

    public static abstract class Buah {
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

        public abstract void metodeAbstrak();
    }

    public static class BuahSegar extends Buah {
        private final boolean organik;

        public BuahSegar(String nama, int stok, boolean organik) {
            super(nama, stok);
            this.organik = organik;
        }

        @Override
        public String toString() {
            String jenis = organik ? "Organik" : "Non-organik";
            return String.format("| %-20s | %-10s | %-11s |", nama, stok, jenis);
        }

        @Override
        public void metodeAbstrak() {
        }
    }

    public static class Pembelian extends Transaksi {
        public Pembelian(String namaBuah, int jumlah) {
            super(namaBuah, jumlah);
        }

        @Override
        public void metodeAbstrak() {
        }
    }

    public abstract static class Transaksi {
        protected String namaBuah;
        protected int jumlah;
        protected final Date tanggal;

        public Transaksi(String namaBuah, int jumlah) {
            this.namaBuah = namaBuah;
            this.jumlah = jumlah;
            this.tanggal = new Date();
        }

        @Override
        public String toString() {
            return String.format("| %-20s | %-10s | %-10s |", namaBuah, jumlah, tanggal);
        }

        public abstract void metodeAbstrak();
    }

    @Override
    public void logTransaksi(String namaBuah, int jumlah, Date tanggal) {
        System.out.println("Transaksi berhasil ditambahkan:");
        System.out.println("====================================================================");
        System.out.printf("| %-20s | %-10s | %-28s |\n", "Nama Buah", "Jumlah", "Tanggal");
        System.out.println("====================================================================");
        System.out.printf("| %-20s | %-10s | %-28s |\n", namaBuah, jumlah, tanggal);
        System.out.println("====================================================================");
        totalTransaksi += jumlah;
    }

    @Override
    public void clearLog() {
        totalTransaksi = 0;
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

        Transaksi transaksi = new Pembelian(namaBuah, jumlah);
        logTransaksi(namaBuah, jumlah, transaksi.tanggal);
        buahPilihan.stok -= jumlah;
        tungguInput();
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

    private final void tungguInput() {
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
        scanner.nextLine();
        clearScreen();
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

            try {
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
                    case 6:
                        System.out.println("======================================================");
                        System.out.println("| Terima kasih telah menggunakan aplikasi Toko Buah! |");
                        System.out.println("======================================================");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih menu 1-6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka untuk memilih menu.");
                tokoBuah.scanner.next();
                pilihan = 0;
            }

        } while (pilihan != 6);

        tokoBuah.scanner.close();
    }
}
