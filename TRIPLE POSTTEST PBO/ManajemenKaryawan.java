import java.util.Scanner;

class Karyawan {
    protected int jamKerja;
    protected double gaji;
    protected double bonus;

    public Karyawan(int jamKerja) {
        this.jamKerja = jamKerja;
        hitungGaji();
        hitungBonus();
    }

    public void hitungGaji() {
        if (jamKerja >= 1 && jamKerja <= 8) {
            gaji = 500000;
        } else if (jamKerja >= 9 && jamKerja <= 16) {
            gaji = 1500000;
        } else if (jamKerja >= 17 && jamKerja <= 24) {
            gaji = 3000000;
        } else {
            gaji = 0;
        }
    }

    public void hitungBonus() {
        if (jamKerja >= 1 && jamKerja <= 8) {
            bonus = 100000;
        } else if (jamKerja >= 9 && jamKerja <= 16) {
            bonus = 300000;
        } else if (jamKerja >= 17 && jamKerja <= 24) {
            bonus = 500000;
        } else {
            bonus = 0;
        }
    }

    public void displayInfo() {
        System.out.println("Jam kerja: " + jamKerja);
        System.out.println("Gaji: Rp " + gaji);
        System.out.println("Bonus: Rp " + bonus);
    }
}

class Manager extends Karyawan {
    public Manager(int jamKerja) {
        super(jamKerja);
    }
}

class Programmer extends Karyawan {
    public Programmer(int jamKerja) {
        super(jamKerja);
    }
}

class Sales extends Karyawan {
    public Sales(int jamKerja) {
        super(jamKerja);
    }
}

public class ManajemenKaryawan {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan jam kerja Manager:");
        int jamKerjaManager = scanner.nextInt();
        clearConsole();
        Manager manager = new Manager(jamKerjaManager);

        System.out.println("Masukkan jam kerja Programmer:");
        int jamKerjaProgrammer = scanner.nextInt();
        clearConsole();
        Programmer programmer = new Programmer(jamKerjaProgrammer);

        System.out.println("Masukkan jam kerja Sales:");
        int jamKerjaSales = scanner.nextInt();
        clearConsole();
        Sales sales = new Sales(jamKerjaSales);

        System.out.println("Apakah Anda ingin menampilkan informasi karyawan? (yes/no)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("yes")) {
            clearConsole();
            System.out.println("Manager:");
            manager.displayInfo();
            System.out.println();

            System.out.println("Programmer:");
            programmer.displayInfo();
            System.out.println();

            System.out.println("Sales:");
            sales.displayInfo();
        } else {
            System.out.println("Terima kasih!");
        }

        scanner.close();
    }

    public static void clearConsole() throws Exception {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
        }
    }
}
