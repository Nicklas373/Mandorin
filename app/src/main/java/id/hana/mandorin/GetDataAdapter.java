package id.hana.mandorin;

/**
 * Created by JUNED on 6/16/2016.
 */
public class GetDataAdapter {

    public String nik, nama_mandor, umur_mandor, alamat_mandor, foto_mandor;

    public String getFoto_mandor() {
        return foto_mandor;
    }

    public void setFoto_mandor(String foto_mandor) {
        this.foto_mandor = foto_mandor;
    }

    public String getNama_mandor() {
        return nama_mandor;
    }

    public void setNama_mandor(String nama_mandor) {
        this.nama_mandor = nama_mandor;
    }

    public String getNik_mandor() {
        return nik;
    }

    public void setNik_mandor(String nik) {
        this.nik = nik;
    }

    public String getUmur_mandor() {
        return umur_mandor;
    }

    public void setUmur_mandor(String umur_mandor) {
        this.umur_mandor= umur_mandor;
    }

    public String getAlamat_mandor() {
        return alamat_mandor;
    }

    public void setAlamat_mandor(String alamat_mandor) {
        this.alamat_mandor = alamat_mandor;
    }
}