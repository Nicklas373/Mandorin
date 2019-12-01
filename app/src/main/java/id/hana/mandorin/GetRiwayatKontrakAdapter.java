package id.hana.mandorin;

public class GetRiwayatKontrakAdapter {

    public String id, nomor_kontrak, nama_pemesan, email, alamat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getNomor_kontrak() {
        return nomor_kontrak;
    }

    public void setNomor_kontrak(String nomor_kontrak) {
        this.nomor_kontrak=nomor_kontrak;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama_pemesan(String nama_pemesan) {
        this.nama_pemesan= nama_pemesan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) { this.alamat= alamat; }
}