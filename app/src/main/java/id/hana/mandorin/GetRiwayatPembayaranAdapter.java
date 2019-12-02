package id.hana.mandorin;

public class GetRiwayatPembayaranAdapter{

    public String id, nomor_kontrak, nama_pemesan, email, alamat, biaya_desain, biaya_konstruksi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getNomor_kontrak() { return nomor_kontrak; }

    public void setNomor_kontrak(String nomor_kontrak) {
        this.nomor_kontrak= nomor_kontrak;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama(String nama_pemesan) {
        this.nama_pemesan= nama_pemesan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat= alamat;
    }

    public String getBiaya_desain() {
        return biaya_desain;
    }

    public void setBiaya_desain(String biaya_desain) {
        this.biaya_desain= biaya_desain;
    }

    public String getBiaya_konstruksi() {
        return biaya_konstruksi;
    }

    public void setBiaya_konstruksi(String biaya_konstruksi) { this.biaya_konstruksi= biaya_konstruksi; }
}