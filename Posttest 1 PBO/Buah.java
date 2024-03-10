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
